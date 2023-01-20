package ru.nsu.leorita.rdt;

import ru.nsu.leorita.serializer.SnakesProto;

import java.net.InetAddress;

public class ReceivedMessage {
    private SnakesProto.GameMessage gameMessage;
    private InetAddress senderAddress;
    private int senderPort;


    public ReceivedMessage(SnakesProto.GameMessage gameMessage, InetAddress senderAddress, int senderPort) {
        this.gameMessage = gameMessage;
        this.senderAddress = senderAddress;
        this.senderPort = senderPort;
    }

    public SnakesProto.GameMessage getGameMessage() {
        return gameMessage;
    }

    public void setGameMessage(SnakesProto.GameMessage gameMessage) {
        this.gameMessage = gameMessage;
    }

    public InetAddress getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(InetAddress senderAddress) {
        this.senderAddress = senderAddress;
    }

    public int getSenderPort() {
        return senderPort;
    }

    public void setSenderPort(int senderPort) {
        this.senderPort = senderPort;
    }


    public SnakesProto.GameMessage.TypeCase getMessageType() {
        return gameMessage.getTypeCase();
    }
}
