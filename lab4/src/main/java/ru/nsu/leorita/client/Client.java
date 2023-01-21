package ru.nsu.leorita.client;

import ru.nsu.leorita.model.GameConfig;
import ru.nsu.leorita.model.GameState;
import ru.nsu.leorita.model.NodeRole;
import ru.nsu.leorita.model.PlayerType;
import ru.nsu.leorita.serializer.SnakesProto;

import java.net.InetAddress;
import java.util.List;

public interface Client {
    void handleAnnouncement(List<SnakesProto.GameAnnouncement> announcement, InetAddress senderIp, int senderPort, int senderId);

    void handleAck(InetAddress masterIp, int masterPort, int localId, int masterId);

    void handleState(SnakesProto.GameState state);

    void chooseGame(String gameName, PlayerType playerType, String playerName, NodeRole requestedRole);

    String getRequestedGame();

    void handleErrorMessage(String error);

    Boolean getJoinAwaiting();

    InetAddress getMasterIp();

    void setLocalId(int id);

    public void run();

    int getMasterPort();

    void changeRoleToDeputy();

    void changeMaster(InetAddress masterIp, int masterPort, int masterId);

    void killSnake();

    Boolean getIsMaster();

    void setIsMaster(Boolean isMaster);

    GameConfig getGameConfig();

    void setGameConfig(GameConfig config);

    void setGameState(GameState gameState);

    void goNorth();

    void goWest();

    void goEast();

    void goSouth();

    void shutdown();
}
