package ru.nsu.leorita.rdt;

import ru.nsu.leorita.serializer.SnakesProto;

import java.net.DatagramPacket;
import java.util.List;

public class MessageBuilder {
    public static SnakesProto.GameMessage buildPingMessage() {
        SnakesProto.GameMessage.PingMsg pingMsg = SnakesProto.GameMessage.newBuilder().getPing();
        return SnakesProto.GameMessage.newBuilder()
                .setPing(pingMsg)
                .build();
    }

    public static SnakesProto.GameMessage buildSteerMessage(SnakesProto.Direction direction) {
        SnakesProto.GameMessage.SteerMsg steerMsg = SnakesProto.GameMessage.newBuilder()
                .getSteerBuilder()
                .setDirection(direction)
                .build();
        return SnakesProto.GameMessage.newBuilder()
                .setSteer(steerMsg)
                .build();
    }

    public static SnakesProto.GameMessage buildAckMessage(long seq, int id, int receiverID) {
        SnakesProto.GameMessage.AckMsg ackMessage = SnakesProto.GameMessage.newBuilder().getAck();
        return SnakesProto.GameMessage.newBuilder()
                .setAck(ackMessage)
                .setMsgSeq(seq)
                .setSenderId(id)
                .setReceiverId(receiverID)
                .build();
    }

    public static SnakesProto.GameMessage buildStateMessage(SnakesProto.GameState gameState) {
        SnakesProto.GameMessage.StateMsg stateMsg = SnakesProto.GameMessage.newBuilder()
                .getStateBuilder()
                .setState(gameState)
                .build();
        return SnakesProto.GameMessage.newBuilder()
                .setState(stateMsg)
                .build();
    }

    public static SnakesProto.GameMessage buildAnnouncementMessage(List<SnakesProto.GameAnnouncement> announcements) {
        SnakesProto.GameMessage.AnnouncementMsg announcementMsg = SnakesProto.GameMessage.newBuilder()
                .getAnnouncementBuilder()
                .addAllGames(announcements)
                .build();
        return SnakesProto.GameMessage.newBuilder()
                .setAnnouncement(announcementMsg)
                .build();
    }

    public static SnakesProto.GameMessage buildJoinMessage(SnakesProto.PlayerType playerType, String playerName, String gameName, SnakesProto.NodeRole requestedRole) {
        SnakesProto.GameMessage.JoinMsg joinMsg = SnakesProto.GameMessage.newBuilder()
                .getJoinBuilder()
                .setPlayerType(playerType)
                .setPlayerName(playerName)
                .setGameName(gameName)
                .setRequestedRole(requestedRole)
                .build();
        return SnakesProto.GameMessage.newBuilder()
                .setJoin(joinMsg)
                .build();
    }

    public static SnakesProto.GameMessage buildErrorMessage(String errorMessage) {
        SnakesProto.GameMessage.ErrorMsg errorMsg = SnakesProto.GameMessage.newBuilder()
                .getErrorBuilder()
                .setErrorMessage(errorMessage)
                .build();
        return SnakesProto.GameMessage.newBuilder()
                .setError(errorMsg)
                .build();
    }

    public static SnakesProto.GameMessage buildRoleChangeMessage(SnakesProto.NodeRole senderRole, SnakesProto.NodeRole receiverRole) {
        SnakesProto.GameMessage.RoleChangeMsg roleChangeMsg = SnakesProto.GameMessage.newBuilder()
                .getRoleChangeBuilder()
                .setReceiverRole(receiverRole)
                .setSenderRole(senderRole)
                .build();
        return SnakesProto.GameMessage.newBuilder()
                .setRoleChange(roleChangeMsg)
                .build();
    }

    public static SnakesProto.GameMessage buildDiscoverMessage() {
        SnakesProto.GameMessage.DiscoverMsg discoverMsg = SnakesProto.GameMessage.newBuilder()
                .getDiscover();
        return SnakesProto.GameMessage.newBuilder()
                .setDiscover(discoverMsg)
                .build();
    }
}
