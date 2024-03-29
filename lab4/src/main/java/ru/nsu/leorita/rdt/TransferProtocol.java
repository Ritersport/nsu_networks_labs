package ru.nsu.leorita.rdt;

import org.apache.log4j.Logger;
import ru.nsu.leorita.network.DatagramSocketWrapper;
import ru.nsu.leorita.network.DeliveryChannel;
import ru.nsu.leorita.network.RawMessage;
import ru.nsu.leorita.rdt.exceptions.WrongMessageException;
import ru.nsu.leorita.serializer.SnakesProto;
import ru.nsu.leorita.utils.OneShootTimer;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TransferProtocol implements Runnable, TransferPublisher {
    private static volatile TransferProtocol transferProtocolInstance;
    private static Thread thread;
    private final DeliveryChannel channel;
    private final int rcvTimeout = 20;
    private final Logger logger = Logger.getLogger(TransferProtocol.class);
    private long ackTimeout;
    private ConcurrentHashMap<Long, SentMessage> toSend;
    private ConcurrentHashMap<InetAddress, HashMap<Long, ReceivedMessage>> receivedMessages;
    private ConcurrentHashMap<Long, OneShootTimer> timerMap;
    private ArrayList<Subscriber> subscribers = new ArrayList<>();
    private long nextSeqNum;
    private long sendingNum;
    private int localId;


    private TransferProtocol() throws IOException {
        toSend = new ConcurrentHashMap<>();
        receivedMessages = new ConcurrentHashMap<>();
        timerMap = new ConcurrentHashMap<>();
        channel = new DatagramSocketWrapper(rcvTimeout);
        sendingNum = 1;
        nextSeqNum = 0;
    }


    public static TransferProtocol getTransferProtocolInstance() throws IOException {
        if (transferProtocolInstance == null) {
            synchronized (TransferProtocol.class) {
                if (transferProtocolInstance == null) {
                    transferProtocolInstance = new TransferProtocol();
                    thread = new Thread(transferProtocolInstance);
                    thread.start();
                    thread.interrupt();
                }
            }
        }
        return transferProtocolInstance;
    }

    public void provideStateDelay(int stateDelayMs) {
        ackTimeout = stateDelayMs / 10;
    }

    public long getNextSeqNum() {
        nextSeqNum++;
        return nextSeqNum;
    }


    public void setLocalId(int localId) {
        this.localId = localId;
    }

    @Override
    public void run() {
        while (true) {
            if (!toSend.isEmpty()) {
                SentMessage sentMessage = toSend.get(sendingNum);
                if (sentMessage != null) {
                    try {
                        sendMessage(sentMessage);
                        sendingNum++;
                    } catch (IOException e) {
                        logger.error("TransferProtocol.run(): " + e);
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                receiveMessage();
            } catch (InterruptedIOException e) {
                logger.error(e);
            } catch (IOException ignored) {

            }
        }
    }

    public void send(SnakesProto.GameMessage message, InetAddress rcvAddress, int rcvPort) {
        if (message.hasAck() && (message.getReceiverId() == 0)) {
            logger.error("TransferProtocol.send():" + new WrongMessageException("Ack message without receiver"));
            return;
        }
        SentMessage newMessage;
        if (message.hasAck() || message.hasAnnouncement() || message.hasDiscover()) {
            try {
                channel.send(message.toByteArray(), rcvAddress, rcvPort);
            } catch (IOException e) {
                logger.error("TransferProtocol: " + e);
            }
        } else {
            newMessage = new SentMessage(message, rcvAddress, rcvPort);
            long seq = message.getMsgSeq();
            toSend.put(seq, newMessage);
        }
    }

    public void sendMyself(SnakesProto.GameMessage message) {
        notifySubscribers(new ReceivedMessage(message, null, 0));
    }

    private void sendMessage(SentMessage message) throws IOException {
        try {
            timerMap.remove(message.getSeq());
            OneShootTimer timer = new OneShootTimer(ackTimeout, () -> {
                try {
                    channel.send(message.getGameMessage().toByteArray(), message.getReceiverAddress(), message.getReceiverPort());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            channel.send(message.getGameMessage().toByteArray(), message.getReceiverAddress(), message.getReceiverPort());
            timer.start();
            timerMap.put(message.getSeq(), timer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void receiveMessage() throws IOException {
        RawMessage rcvData = channel.receive();
        if (rcvData != null) {
            SnakesProto.GameMessage gameMessage = SnakesProto.GameMessage.parseFrom(rcvData.getMessage());

            if ((gameMessage.hasAck() && gameMessage.getReceiverId() != 0) || gameMessage.hasAnnouncement() || gameMessage.hasDiscover()) {

                notifySubscribers(new ReceivedMessage(gameMessage, rcvData.getSenderAddress(), rcvData.getSenderPort()));
            } else {
                long seq = gameMessage.getMsgSeq();
                if (gameMessage.hasAck()) {
                    ackSentMessage(seq);
                } else {
                    sendAck(seq, rcvData.getSenderAddress(), rcvData.getSenderPort());
                    ReceivedMessage rcvMessage = new ReceivedMessage(gameMessage, rcvData.getSenderAddress(), rcvData.getSenderPort());
                    if (!receivedMessages.containsKey(rcvData.getSenderAddress())) {
                        receivedMessages.put(rcvMessage.getSenderAddress(), new HashMap<>());
                    }
                    if (!receivedMessages.get(rcvMessage.getSenderAddress()).containsKey(seq)) {
                        receivedMessages.get(rcvMessage.getSenderAddress()).put(seq, rcvMessage);
                        transfer(seq, rcvMessage.getSenderAddress());
                    }
                }
            }
        }
    }


    private void transfer(long seq, InetAddress ip) {
        notifySubscribers(receivedMessages.get(ip).get(seq));
    }

    private void ackSentMessage(long seq) {
        if (timerMap.get(seq) != null) {
            timerMap.get(seq).cancel();
        }
        timerMap.remove(seq);
        if (toSend.get(seq) != null) {
            toSend.get(seq).ack();
            toSend.remove(seq);
        }
    }

    private void sendAck(long seq, InetAddress receiverAddress, int receiverPort) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildAckMessage(seq, 0, 0);
        channel.send(message.toByteArray(), receiverAddress, receiverPort);
    }

    @Override
    public void notifySubscribers(ReceivedMessage message) {
        subscribers.forEach(subscriber -> subscriber.update(message));
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void shutdown() {
        this.channel.close();
        timerMap.forEach((i, t) -> t.cancel());
        thread.interrupt();
    }
}
