package ru.nsu.leorita.rdt;

import ru.nsu.leorita.network.*;
import ru.nsu.leorita.rdt.exceptions.WrongMessageException;
import ru.nsu.leorita.serializer.SnakesProto;
import ru.nsu.leorita.utils.OneShootTimer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Rdt {

    private ConcurrentHashMap<Long, SendMessage> toSend;
    private ConcurrentHashMap<Long, OneShootTimer> timerMap;
    private ConcurrentHashMap<Long, ReceiveMessage> receivedMessages;
    private final DeliveryChannel channel;
    private long sendBase;
    private long receiveBase;
    private int playerId;
    private final long ackTimeout;
    private final int WINDOW_SIZE = 100;
    private Rdt rdtInstance;
    private Rdt(int playerId, long stateDelayMs) throws IOException {
        channel = new SocketWrapper();
        sendBase = 0;
        receiveBase = 0;
        ackTimeout = stateDelayMs / 10;
        this.playerId = playerId;
    }

    public Rdt getRdtInstance(int playerId, long stateDelayMs) throws IOException {
        if (rdtInstance == null) {
            rdtInstance = new Rdt(playerId, stateDelayMs);
        }
        return rdtInstance;
    }
    private void receive() throws IOException {
        RawMessage rcvData = channel.receive();
        SnakesProto.GameMessage gameMessage = SnakesProto.GameMessage.parseFrom(rcvData.getMessage());
        long seq = gameMessage.getMsgSeq();
        if (gameMessage.hasAck()) {
            timerMap.get(seq).cancel();
            timerMap.remove(seq);
            if ((seq >= receiveBase) && (seq < receiveBase + WINDOW_SIZE)) {
                toSend.get(seq).ack();
                if (seq == receiveBase) {
                    do {
                        toSend.remove(sendBase);
                        sendBase++;
                    } while (toSend.get(sendBase).isAcked());
                }
            }
        }
        else {
            if ((seq >= receiveBase) && (seq > receiveBase + WINDOW_SIZE)) {
                sendACK(rcvData.getSenderAddress(), rcvData.getSenderPort(), gameMessage.getReceiverId(), seq);
                ReceiveMessage rcvMessage = new ReceiveMessage(gameMessage, rcvData.getSenderAddress(), rcvData.getSenderPort());
                if (!receivedMessages.containsKey(seq)) {
                    receivedMessages.put(seq, rcvMessage);
                }
                if (seq == receiveBase) {
                    do {
                        transfer(receiveBase);
                        receivedMessages.remove(receiveBase);
                        receiveBase++;
                    } while (receivedMessages.containsKey(receiveBase));
                }
            }
            if ((seq >= receiveBase - WINDOW_SIZE) && (seq < receiveBase)) {
                sendACK(rcvData.getSenderAddress(), rcvData.getSenderPort(), gameMessage.getReceiverId(), seq);
            }
        }
    }

    void transfer(long seq) {

    }

    public void send(SnakesProto.GameMessage gameMessage, InetAddress address, int port) throws IOException, WrongMessageException {
        if (gameMessage.hasAck()) {
            throw new WrongMessageException("Wrong type of message");
        }
        SendMessage sendMessage = new SendMessage(gameMessage, address, port);
        toSend.put(sendMessage.getSeq(), sendMessage);
        if (sendMessage.getSeq() < sendBase + WINDOW_SIZE) {
            this.sendMessage(sendMessage, address, port);
        }
    }

    private void sendMessage(SendMessage message, InetAddress address, int port) throws IOException {
        timerMap.remove(message.getSeq());
        OneShootTimer timer = new OneShootTimer(ackTimeout, () -> {
            try {
                Rdt.this.sendMessage(message, address, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel.send(message.getGameMessage().toByteArray(), address, port);
        timer.start();
        timerMap.put(message.getSeq(), timer);
    }

    private void sendACK(InetAddress address, int port, int receiverID, long seq) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildAckMessage(seq, playerId, receiverID);
        channel.send(message.toByteArray(), address, port);
    }
}
