package ru.nsu.leorita.server;

import org.apache.log4j.Logger;
import ru.nsu.leorita.client.Client;
import ru.nsu.leorita.model.*;
import ru.nsu.leorita.model.mappers.*;
import ru.nsu.leorita.network.MulticastDeliveryChannel;
import ru.nsu.leorita.network.MulticastSocketWrapper;
import ru.nsu.leorita.rdt.MessageBuilder;
import ru.nsu.leorita.rdt.TransferProtocol;
import ru.nsu.leorita.serializer.SnakesProto;
import ru.nsu.leorita.utils.InfiniteShootsTimer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl implements Server, Runnable {
    private final Logger logger = Logger.getLogger(ServerImpl.class);
    private final GameState gameState;
    private final InfiniteShootsTimer announcementSender;
    private final InfiniteShootsTimer stateSender;
    private final int localId;
    private final int announcementTimeoutMs = 1000;
    private ArrayList<Integer> diedIds;
    private TransferProtocol transferProtocol;
    private Client client;
    private MulticastDeliveryChannel mcastChannel;
    private InetAddress mcastAddress;
    private int mcastPort = 9192;
    private int nextId;
    private Boolean deputyAckAwaiting;
    private InetAddress deputyIp;
    private int deputyPort;

    public ServerImpl(int localId, GameConfig config, String playerName, PlayerType type, Client client) {
        this.client = client;
        this.diedIds = new ArrayList<>();
        try {
            this.mcastAddress = InetAddress.getByName("239.192.0.4");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        ConcurrentHashMap<Integer, GamePlayer> players = new ConcurrentHashMap<>();
        GamePlayer localPlayer = new GamePlayer(playerName, localId, NodeRole.MASTER, type, 0);
        players.put(localPlayer.getId(), localPlayer);
        Coord localSnakeHead = new Coord((int) (Math.random() * 100), (int) (Math.random() * 100));
        localSnakeHead.normalize(config.getWidth(), config.getHeight());
        Snake localSnake = new Snake(localId, localSnakeHead, generateOffset(), config);
        HashMap<Integer, Snake> snakes = new HashMap<>();
        snakes.put(localId, localSnake);
        this.gameState = new GameState(config, players, snakes, localId);
        try {
            this.transferProtocol = TransferProtocol.getTransferProtocolInstance();
        } catch (IOException e) {
            logger.error("ServerImpl constructor: error while creating TransferProtocol instance: " + e);
            logger.info("Shutdown...");
            shutdown();
        }
        this.localId = localId;
        try {
            mcastChannel = new MulticastSocketWrapper(InetAddress.getByName("239.192.0.4"));
        } catch (IOException e) {
            logger.error("ServerImpl constructor: error while creating MulticastSocketWrapper: " + e);
            logger.info("Shutdown...");
            shutdown();
        }
        this.nextId = 1;
        this.deputyAckAwaiting = false;
        announcementSender = new InfiniteShootsTimer(announcementTimeoutMs, () -> {
            try {
                sendAnnouncement();
            } catch (IOException e) {
                logger.error("ServerImpl.sendAnnouncement() exception: " + e);
                throw new RuntimeException(e);
            }
        });
        stateSender = new InfiniteShootsTimer(config.getStateDelayMs(), () -> {
            moveTheWorld();
            sendState();
        });
        setFoods();

    }

    @Override
    public void run() {
        announcementSender.start();
        stateSender.start();
    }

    private void setFoods() {
        for (int i = 0; i < gameState.getConfig().getFoodStatic(); i++) {
            gameState.addFood(generateFood());
        }
    }

    private Coord generateOffset() {
        int direction = (int) (Math.random() * 3);
        switch (direction) {
            case 0:
                return new Coord(0, 1);
            case 1:
                return new Coord(0, -1);
            case 2:
                return new Coord(1, 0);
        }
        return new Coord(-1, 0);
    }

    private Coord generateFood() {
        while (true) {
            int x = (int) (Math.random() * 100);
            int y = (int) (Math.random() * 100);
            Coord food = new Coord(x, y);
            food.normalize(gameState.getConfig().getWidth(), gameState.getConfig().getHeight());
            if (!checkFoodCollisions(food)) {
                return food;
            }
        }
    }

    private Boolean checkFoodCollisions(Coord food) {
        return gameState.getFoods().contains(food);
    }

    private void sendState() {
        gameState.getPlayers().forEach((id, player) -> {
            if (id == localId) {
                SnakesProto.GameMessage stateMsg = MessageBuilder.buildStateMessage(StateMapper.toProtobuf(gameState, localId), -1);
                transferProtocol.sendMyself(stateMsg);
            } else {
                SnakesProto.GameMessage stateMsg = MessageBuilder.buildStateMessage(StateMapper.toProtobuf(gameState, localId), transferProtocol.getNextSeqNum());
                transferProtocol.send(stateMsg, player.getIpAddress(), player.getPort());
            }
        });
    }

    @Override
    public void handleSteer(SnakesProto.Direction headDirection, int senderId) {
        //    System.out.println(headDirection + String.valueOf(senderId));
        Snake snake = gameState.getSnakes().get(senderId);
        if (snake != null) {
            Direction newDir = DirectionMapper.toDomen(headDirection);
            assert newDir != null;
            if (snake.canTurn(newDir)) {
                snake.setHeadDirection(newDir);
            }
        } else {
            logger.error("Steer msg for snake that isn't alive");
        }
    }

    @Override
    public int getNextId() {
        nextId++;
        return nextId;
    }

    @Override
    public void ackNewDeputy(InetAddress deputyAddress, int deputyPort) {
        if (deputyAckAwaiting) {
            this.deputyIp = deputyAddress;
            this.deputyPort = deputyPort;
            deputyAckAwaiting = false;
        } else {
            logger.error("ServerImpl.ackNewDeputy(): ack hasn't been awaited, but received");
        }
    }

    @Override
    public Boolean getDeputyAckAwaiting() {
        return deputyAckAwaiting;
    }

    private void sendAnnouncement() throws IOException {
        GameAnnouncement gameAnnouncement = new GameAnnouncement(gameState.getPlayers(), gameState.getConfig(), checkCanJoin(), gameState.getConfig().getGameName(), localId);
        List<SnakesProto.GameAnnouncement> announcementList = new ArrayList<>();
        announcementList.add(AnnouncementMapper.toProtobuf(gameAnnouncement));
        SnakesProto.GameMessage announcementMessage = MessageBuilder.buildAnnouncementMessageBroadcast(announcementList, localId);
        transferProtocol.send(announcementMessage, mcastAddress, mcastPort);
    }

    @Override
    public void sendAnnouncement(InetAddress receiverIp, int receiverPort) {
        GameAnnouncement gameAnnouncement = new GameAnnouncement(gameState.getPlayers(), gameState.getConfig(), checkCanJoin(), gameState.getConfig().getGameName(), localId);
        List<SnakesProto.GameAnnouncement> announcementList = new ArrayList<>();
        announcementList.add(AnnouncementMapper.toProtobuf(gameAnnouncement));
        SnakesProto.GameMessage message = MessageBuilder.buildAnnouncementMessage(announcementList, 0, localId);
        transferProtocol.send(message, receiverIp, receiverPort);
    }

    //TODO реализовать
    private Boolean checkCanJoin() {
        return true;
    }

    private void sendAck(long msgSeq, int senderId, int receiverId, InetAddress receiverIp, int receiverPort) {
        transferProtocol.send(MessageBuilder.buildAckMessage(msgSeq, senderId, receiverId), receiverIp, receiverPort);
    }

    private void sendRoleChangeToDeputy(InetAddress receiverIp, int receiverPort) {
        SnakesProto.GameMessage mes = MessageBuilder.buildRoleChangeMessage(SnakesProto.NodeRole.MASTER, SnakesProto.NodeRole.DEPUTY, transferProtocol.getNextSeqNum());
        deputyAckAwaiting = true;
        transferProtocol.send(mes, receiverIp, receiverPort);
    }

    private void sendRoleChangeToViewer(GamePlayer player) {

        if (player.getIsLocal()) {
            SnakesProto.GameMessage mes = MessageBuilder.buildRoleChangeMessage(SnakesProto.NodeRole.MASTER, SnakesProto.NodeRole.VIEWER, -1);
            transferProtocol.sendMyself(mes);
        } else {
            SnakesProto.GameMessage mes = MessageBuilder.buildRoleChangeMessage(SnakesProto.NodeRole.MASTER, SnakesProto.NodeRole.VIEWER, transferProtocol.getNextSeqNum());
            transferProtocol.send(mes, player.getIpAddress(), player.getPort());
        }
    }

    @Override
    public void handleJoin(long msgSeq, String gameName, String playerName, SnakesProto.PlayerType playerType, SnakesProto.NodeRole requestedRole, InetAddress newPlayerIp, int newPlayerPort) {
        if ((requestedRole == SnakesProto.NodeRole.NORMAL)) {
            int newPlayerId = getNextId();
            try {
                gameState.addSnake(new Snake(newPlayerId, new Coord((int) (Math.random() * 100), (int) (Math.random() * 100)), generateOffset(), gameState.getConfig()));

            } catch (RuntimeException e) {
                SnakesProto.GameMessage message = MessageBuilder.buildErrorMessage("failed to place the snake, try again");
                transferProtocol.send(message, newPlayerIp, newPlayerPort);
                return;
            }
            GamePlayer newPlayer = new GamePlayer(playerName, newPlayerId, newPlayerIp, newPlayerPort, RoleMapper.toDomen(requestedRole), TypeMapper.toDomen(playerType), 0);
            gameState.addPlayer(newPlayer);
            sendAck(msgSeq, localId, newPlayerId, newPlayerIp, newPlayerPort);
            if (gameState.getPlayersCount() == 2) {
                sendRoleChangeToDeputy(newPlayerIp, newPlayerPort);
            }
        } else {
            logger.error("ServerImpl.handleJoin(): unavailable role requested");
        }
    }

    @Override
    public void handleRoleChangeToViewer(InetAddress requesterIp, int requesterPort, int playerId) {

    }

    private void moveTheWorld() {
        gameState.setNextStateOrder();
        gameState.getSnakes().forEach((id, snake) -> {
            snake.move();
            snake.normalizeCoords();
            snake.setDirUpdated(false);
        });
        gameState.getSnakes().forEach((id1, snake1) -> {
            gameState.getSnakes().forEach((id2, snake2) -> {
                if (snake1.getPlayerId() != snake2.getPlayerId() && snake1.isBumped(snake2.getBody().get(0))) {
                    diedIds.add(snake2.getPlayerId());
                }
            });
            if (snake1.isBumpedSelf()) {
                diedIds.add(snake1.getPlayerId());
            }
        });
        diedIds.forEach(id -> {
            gameState.getSnakes().remove(id);
            GamePlayer diedPlayer = gameState.getPlayers().get(id);
            diedPlayer.setRole(NodeRole.VIEWER);
            sendRoleChangeToViewer(diedPlayer);
        });
        ArrayList<Coord> eatenFoods = new ArrayList<>();
        gameState.getSnakes().forEach((id, snake) -> {
            gameState.getFoods().forEach(food -> {
                if (snake.isBumped(food)) {
                    if (eatenFoods.add(food)) {
                        snake.grow();
                        gameState.getPlayers().get(id).increaseScore(1);
                    }
                }
            });
        });
        eatenFoods.forEach(food -> gameState.getFoods().remove(food));
        if (gameState.getFoods().size() < gameState.getConfig().getFoodStatic()) {
            for (int i = 0; i < gameState.getConfig().getFoodStatic() - gameState.getFoods().size(); i++) {
                gameState.addFood(generateFood());
            }
        }

    }

    public void shutdown() {
        mcastChannel.close();
        announcementSender.cancel();
        stateSender.cancel();
    }
}
