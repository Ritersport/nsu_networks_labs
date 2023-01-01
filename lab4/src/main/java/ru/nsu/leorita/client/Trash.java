package ru.nsu.leorita.client;

import ru.nsu.leorita.network.DeliveryChannel;
import ru.nsu.leorita.network.MulticastDeliveryChannel;
import ru.nsu.leorita.rdt.MessageBuilder;
import ru.nsu.leorita.serializer.SnakesProto;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class Trash {
    private DeliveryChannel channel;
    private MulticastDeliveryChannel multicastDeliveryChannel;
    private void sendPing(InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildPingMessage();
        channel.send(message.toByteArray(), address, port);
    }

    private void sendSteer(SnakesProto.Direction dir, InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildSteerMessage(dir);
        channel.send(message.toByteArray(), address, port);
    }

    private void sendStateMsg(SnakesProto.GameState state, InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildStateMessage(state);
        channel.send(message.toByteArray(), address, port);
    }

    //без подтверждений
    private void sendDiscoverMessage(InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildDiscoverMessage();
        channel.send(message.toByteArray(), address, port);
    }

    private void sendJoinMessage(SnakesProto.PlayerType playerType, String playerName, String gameName, SnakesProto.NodeRole requestedRole, InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildJoinMessage(playerType, playerName, gameName, requestedRole);
        channel.send(message.toByteArray(), address, port);
    }

    private void sendErrorMessage(String errorMessage, InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildErrorMessage(errorMessage);
        channel.send(message.toByteArray(), address, port);
    }

    private void sendRoleChangeMessage(SnakesProto.NodeRole senderRole, SnakesProto.NodeRole receiverRole, InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildRoleChangeMessage(senderRole, receiverRole);
        channel.send(message.toByteArray(), address, port);
    }
    private void sendAnnouncementMsg(List<SnakesProto.GameAnnouncement> announcement, InetAddress address, int port) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildAnnouncementMessage(announcement);
        channel.send(message.toByteArray(), address, port);
    }
    private void sendAnnouncementMsg(List<SnakesProto.GameAnnouncement> announcement) throws IOException {
        SnakesProto.GameMessage message = MessageBuilder.buildAnnouncementMessage(announcement);
        multicastDeliveryChannel.broadcast(message.toByteArray());
    }
}
