package ru.nsu.leorita.client;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import org.apache.log4j.Logger;
import ru.nsu.leorita.client.ui.View;
import ru.nsu.leorita.client.ui.fxext.FxSchedulers;
import ru.nsu.leorita.model.*;
import ru.nsu.leorita.model.mappers.*;
import ru.nsu.leorita.network.MulticastReceiver;
import ru.nsu.leorita.rdt.MessageBuilder;
import ru.nsu.leorita.rdt.TransferProtocol;
import ru.nsu.leorita.serializer.SnakesProto;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClientImpl implements Client, Runnable {
    private View view;
    private TransferProtocol channel;
    Logger logger = Logger.getLogger(ClientImpl.class);
    private GameConfig gameConfig;
    private GameState gameState;
    private int masterPort;
    private int localId;
    private int masterId;
    private InetAddress masterIp;
    private Boolean joinAwaiting;
    private String requestedGame;
    private Boolean isMaster;
    private ConcurrentHashMap<Long, GameAnnouncement> serversCollection;
    private final long IS_ALIVE_TIMEOUT = 1100;


    public ClientImpl(View view) {
        try {
            this.view = view;
            this.channel = TransferProtocol.getTransferProtocolInstance();
            joinAwaiting = false;
            this.serversCollection = new ConcurrentHashMap<>();
            isMaster = false;
        } catch (IOException e) {
            logger.error("ClientImpl constructor: unable to create TransferProtocol: " + e + "\nShutdown...");
            shutdown();
        }

    }

    @Override
    public void setLocalId(int id) {
        this.localId = id;
    }

    @Override
    public void run() {

    }

    private void handleAnnouncement(SnakesProto.GameMessage message, InetAddress senderIp, int senderPort) {
        if (!message.hasAnnouncement()) {
            logger.error("Client: Received multicast message of another type then Announcement");
        } else {
            ArrayList<Long> keysToDelete = new ArrayList<>();
            for (SnakesProto.GameAnnouncement game : message.getAnnouncement().getGamesList()) {
                if (game.getCanJoin()) {
                    serversCollection.forEach((time, server) -> {
                        if (server.getGameName().equals(game.getGameName()) && (server.getMasterId() == message.getSenderId())) {
                            keysToDelete.add(time);
                        }
                    });
                    checkServerRelevance(keysToDelete);
                    serversCollection.put(System.currentTimeMillis(), AnnouncementMapper.toDomen(game, senderIp, senderPort, message.getSenderId()));
                }
            }
            view.updateGameList(serversCollection);
        }
    }

    @Override
    public void handleAnnouncement(List<SnakesProto.GameAnnouncement> announcements, InetAddress senderIp, int senderPort, int senderId) {
        ArrayList<Long> keysToDelete = new ArrayList<>();
        for (SnakesProto.GameAnnouncement game : announcements) {
            if (game.getCanJoin()) {
                serversCollection.forEach((time, server) -> {
                    if (server.getGameName().equals(game.getGameName()) && (server.getMasterId() == senderId)) {
                        keysToDelete.add(time);
                    }
                });
                serversCollection.put(System.currentTimeMillis(), AnnouncementMapper.toDomen(game, senderIp, senderPort, senderId));
            }
        }
      //TODO  checkServerRelevance(keysToDelete);
        view.updateGameList(serversCollection);
    }

    @Override
    public void handleErrorMessage(String error) {
        view.showError(error);
    }

    private void checkServerRelevance(ArrayList<Long> keysToDelete) {
        Long curTime = System.currentTimeMillis();
        serversCollection.forEach((time, server) -> {
            if (curTime - time > IS_ALIVE_TIMEOUT) {
                keysToDelete.add(time);
            }
        });
        keysToDelete.forEach(key -> serversCollection.remove(key));
    }


    @Override
    public void changeMaster(InetAddress masterIp, int masterPort, int masterId) {
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        this.masterId = masterId;
    }

    @Override
    public void handleAck(InetAddress masterIp, int masterPort, int localId, int masterId) {
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        this.joinAwaiting = false;
        this.localId = localId;
        this.masterId = masterId;
    }

    @Override
    public void chooseGame(String gameName, PlayerType playerType, String playerName, NodeRole requestedRole) {
        GameAnnouncement chosenGame = null;
        for (GameAnnouncement gameAnnouncement : serversCollection.values()) {
            if (gameName.equals(gameAnnouncement.getGameName())) {
                chosenGame = gameAnnouncement;
            }
        }
        if (chosenGame != null) {
            SnakesProto.GameMessage joinMessage = MessageBuilder.buildJoinMessage(
                    TypeMapper.toProtobuf(playerType),
                    playerName,
                    gameName,
                    RoleMapper.toProtobuf(requestedRole),
                    channel.getNextSeqNum());
            channel.send(joinMessage, chosenGame.getMasterIp(), chosenGame.getMasterPort());
            joinAwaiting = true;
            gameConfig = chosenGame.getConfig();
        }
    }

    @Override
    public GameConfig getGameConfig() {
        return this.gameConfig;
    }

    @Override
    public void setIsMaster(Boolean isMaster) {
        this.isMaster = isMaster;
    }

    @Override
    public Boolean getIsMaster() {
        return isMaster;
    }

    @Override
    public InetAddress getMasterIp() {
        return masterIp;
    }

    @Override
    public void changeRoleToDeputy() {
        ackNewRole();
    }

    @Override
    public void killSnake() {

    }

    @Override
    public void handleState(SnakesProto.GameState state) {
        this.gameState = StateMapper.toDomen(state, localId, gameConfig);
        Platform.runLater(() ->
        view.repaintField(gameState, gameConfig));
    }

    @Override
    public void goNorth() {
        sendSteer(Direction.UP);
    }

    @Override
    public void goWest() {
        sendSteer(Direction.LEFT);
    }

    @Override
    public void goEast() {
        sendSteer(Direction.RIGHT);
    }

    @Override
    public void goSouth() {
        sendSteer(Direction.DOWN);
    }

    private void sendSteer(Direction dir) {

        if (isMaster) {
            SnakesProto.GameMessage message = MessageBuilder.buildSteerMessage(DirectionMapper.toProtobuf(dir), -1, localId);
            channel.sendMyself(message);
        } else {
            SnakesProto.GameMessage message = MessageBuilder.buildSteerMessage(DirectionMapper.toProtobuf(dir), channel.getNextSeqNum(), localId);
            channel.send(message, masterIp, masterPort);
        }
    }

    private void ackNewRole() {
        SnakesProto.GameMessage message = MessageBuilder.buildAckMessage(0, localId, masterId);
        channel.send(message, masterIp, masterPort);
    }

    @Override
    public int getMasterPort() {
        return masterPort;
    }

    @Override
    public Boolean getJoinAwaiting() {
        return joinAwaiting;
    }

    public void setJoinAwaiting(Boolean joinAwaiting) {
        this.joinAwaiting = joinAwaiting;
    }

    @Override
    public String getRequestedGame() {
        return requestedGame;
    }

    public void setRequestedGame(String requestedGame) {
        this.requestedGame = requestedGame;
    }

    @Override
    public void setGameConfig(GameConfig config) {
        this.gameConfig = config;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void shutdown() {


    }
}
