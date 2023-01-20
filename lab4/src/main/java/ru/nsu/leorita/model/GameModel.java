package ru.nsu.leorita.model;

import ru.nsu.leorita.client.Client;
import ru.nsu.leorita.client.ui.View;
import ru.nsu.leorita.rdt.ReceivedMessage;
import ru.nsu.leorita.serializer.SnakesProto;

import java.net.InetAddress;

public interface GameModel {
    void send(SnakesProto.GameMessage message, InetAddress receiverAddress, int receiverPort);
    void handleMessage(ReceivedMessage message);
    void startServer(GameConfig config);
    void startClient(GamePlayer player, Boolean isMaster);
    void createClient(View view);
    void setLocalPlayerName(String name);
    void setLocalPlayerRole(NodeRole role);
    GamePlayer getLocalPlayer();
    Client getClient();
    void shutdown();
}
