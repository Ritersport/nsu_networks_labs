package ru.nsu.leorita.rdt;

import ru.nsu.leorita.serializer.SnakesProto;

import java.net.InetAddress;

public class SendMessage {
    private SnakesProto.GameMessage gameMessage;
    private boolean isAcked;
    private int receiverPort;
    private InetAddress receiverAddress;
    private final long seq;

    public SendMessage(SnakesProto.GameMessage gameMessage, InetAddress receiverAddress, int receiverPort) {
        this.gameMessage = gameMessage;
        this.receiverAddress = receiverAddress;
        this.receiverPort = receiverPort;
        this.seq = gameMessage.getMsgSeq();
        this.isAcked = false;
    }

    public SnakesProto.GameMessage getGameMessage() {
        return gameMessage;
    }

    public void setGameMessage(SnakesProto.GameMessage gameMessage) {
        this.gameMessage = gameMessage;
    }

    public void setReceiverAddress(InetAddress receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setReceiverPort(int receiverPort) {
        this.receiverPort = receiverPort;
    }

    public void ack() {
        this.isAcked = true;
    }

    public boolean isAcked() {
        return isAcked;
    }

    public SnakesProto.GameMessage.TypeCase getMessageType() {
        return gameMessage.getTypeCase();
    }
    public long getSeq() {
        return seq;
    }
}
