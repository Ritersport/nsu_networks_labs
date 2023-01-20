package ru.nsu.leorita.server;

import ru.nsu.leorita.serializer.SnakesProto;

import java.net.InetAddress;

public interface Server {
    void run();

    void handleJoin(long msgSeq, String gameName, String playerName, SnakesProto.PlayerType playerType, SnakesProto.NodeRole requestedRole, InetAddress newPlayerIp, int newPlayerPort);

    void handleRoleChangeToViewer(InetAddress requesterIp, int requesterPort, int senderId);

    void handleSteer(SnakesProto.Direction headDirection, int senderId);

    int getNextId();

    void ackNewDeputy(InetAddress deputyAddress, int deputyPort);

    Boolean getDeputyAckAwaiting();

    void sendAnnouncement(InetAddress receiverIp, int receiverPort);

    void shutdown();
}
