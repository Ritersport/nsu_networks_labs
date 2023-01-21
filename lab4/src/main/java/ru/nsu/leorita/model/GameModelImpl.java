package ru.nsu.leorita.model;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.log4j.Logger;
import ru.nsu.leorita.client.Client;
import ru.nsu.leorita.client.ClientImpl;
import ru.nsu.leorita.client.ui.View;
import ru.nsu.leorita.client.ui.fxext.FxSchedulers;
import ru.nsu.leorita.network.MulticastReceiver;
import ru.nsu.leorita.rdt.ReceivedMessage;
import ru.nsu.leorita.rdt.TransferProtocol;
import ru.nsu.leorita.rdt.TransferPublisher;
import ru.nsu.leorita.serializer.SnakesProto;
import ru.nsu.leorita.server.Server;
import ru.nsu.leorita.server.ServerImpl;

import java.io.IOException;
import java.net.InetAddress;

public class GameModelImpl implements GameModel, TransferPublisher.Subscriber {

    private final Logger logger = Logger.getLogger(GameModelImpl.class);
    private Client client;
    private Server server;
    private TransferProtocol transferProtocol;
    private GamePlayer localPlayer;
    private MulticastReceiver mcastReceiver;
    private Disposable disposable;
    private InetAddress mcastAddress;
    private int mcastPort = 9192;

    public GameModelImpl() {
        try {
            transferProtocol = TransferProtocol.getTransferProtocolInstance();
            transferProtocol.addSubscriber(this);
            mcastAddress = InetAddress.getByName("239.192.0.4");
            mcastReceiver = new MulticastReceiver(mcastAddress, mcastPort);
            localPlayer = new GamePlayer();
            disposable = mcastReceiver.getMulticastFlowable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(FxSchedulers.get())
                    .subscribe(this::handleMessage);
        } catch (IOException e) {
            logger.error("GameModelImpl constructor: " + e);
            logger.info("Shutdown...");
            shutdown();
        }
    }

    @Override
    public void startServer(GameConfig config) {
        transferProtocol.provideStateDelay(config.getStateDelayMs());
        server = new ServerImpl(1, config, localPlayer.getName(), localPlayer.getPlayerType(), client);
        client.setGameConfig(config);
        server.run();
    }

    @Override
    public void startClient(GamePlayer player, Boolean isMaster) {
        client.setLocalId(player.getId());
        client.setIsMaster(isMaster);
        this.localPlayer = player;
    }

    @Override
    public void createClient(View view) {
        client = new ClientImpl(view);
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public GamePlayer getLocalPlayer() {
        return localPlayer;
    }

    public TransferProtocol getTransferProtocolInstance() {
        return transferProtocol;
    }


    @Override
    public void send(SnakesProto.GameMessage message, InetAddress receiverAddress, int receiverPort) {

    }

    @Override
    public void handleMessage(ReceivedMessage message) {
        switch (message.getGameMessage().getTypeCase()) {
            case ACK:
                handleAck(message.getGameMessage(), message.getSenderAddress(), message.getSenderPort());
                break;
            case JOIN:
                handleJoin(message.getGameMessage().getJoin(), message.getSenderAddress(), message.getSenderPort(), message.getGameMessage().getMsgSeq());
                break;
            case PING:
                handlePing(message.getGameMessage().getPing(), message.getSenderAddress(), message.getSenderPort());
                break;
            case ERROR:
                handleError(message.getGameMessage().getError());
                break;
            case STATE:
                handleState(message.getGameMessage().getState(), message.getSenderAddress(), message.getSenderPort());
                break;
            case STEER:
                handleSteer(message.getGameMessage().getSteer(), message.getGameMessage().getSenderId());
                break;
            case DISCOVER:
                handleDiscover(message.getGameMessage().getDiscover(), message.getSenderAddress(), message.getSenderPort());
                break;
            case ROLE_CHANGE:
                handleRoleChange(message.getGameMessage().getRoleChange(), message.getSenderAddress(), message.getSenderPort(), message.getGameMessage().getSenderId());
                break;
            case ANNOUNCEMENT:
                handleAnnouncement(message.getGameMessage().getAnnouncement(), message.getSenderAddress(), message.getSenderPort(), message.getGameMessage().getSenderId());
                break;
            case TYPE_NOT_SET:
                break;
        }
    }

    @Override
    public void update(ReceivedMessage message) {
        handleMessage(message);
    }

    @Override
    public void setLocalPlayerName(String name) {
        localPlayer.setName(name);
    }

    @Override
    public void setLocalPlayerRole(NodeRole role) {
        localPlayer.setRole(role);
    }

    private void handleAck(SnakesProto.GameMessage msg, InetAddress senderIp, int senderPort) {

        if (client.getIsMaster()) {
            server.ackNewDeputy(senderIp, senderPort);
        } else {
            if (client.getJoinAwaiting()) {
                transferProtocol.setLocalId(msg.getReceiverId());
                localPlayer.setId(msg.getReceiverId());
                localPlayer.setPlayerType(PlayerType.HUMAN);
                localPlayer.setScore(0);
                startClient(localPlayer, false);
                client.handleAck(senderIp, senderPort, msg.getReceiverId(), msg.getSenderId());
            } else logger.error("Client didn't request joining, but ack received");
        }
    }

    private void handleJoin(SnakesProto.GameMessage.JoinMsg msg, InetAddress senderIp, int senderPort, long seq) {
        if (client.getIsMaster()) {
            server.handleJoin(seq, msg.getGameName(), msg.getPlayerName(), msg.getPlayerType(), msg.getRequestedRole(), senderIp, senderPort);
        }
    }

    private void handlePing(SnakesProto.GameMessage.PingMsg msg, InetAddress senderIp, int senderPort) {

    }

    private void handleError(SnakesProto.GameMessage.ErrorMsg msg) {
        client.handleErrorMessage(msg.getErrorMessage());
    }

    private void handleState(SnakesProto.GameMessage.StateMsg msg, InetAddress senderIp, int senderPort) {
        client.handleState(msg.getState());
    }

    private void handleSteer(SnakesProto.GameMessage.SteerMsg msg, int senderId) {
        if (localPlayer.getRole() == NodeRole.MASTER) {
            server.handleSteer(msg.getDirection(), senderId);
        }
    }

    private void handleDiscover(SnakesProto.GameMessage.DiscoverMsg msg, InetAddress senderIp, int senderPort) {
        if (localPlayer.getRole() == NodeRole.MASTER) {
            server.sendAnnouncement(senderIp, senderPort);
        }
    }

    private void handleRoleChange(SnakesProto.GameMessage.RoleChangeMsg msg, InetAddress senderIp, int senderPort, int senderId) {
        if (msg.hasSenderRole()) {
            if (msg.getSenderRole() == SnakesProto.NodeRole.MASTER) {
                client.changeMaster(senderIp, senderPort, senderId);
            }
            if (msg.getSenderRole() == SnakesProto.NodeRole.VIEWER) {
                if (localPlayer.getRole() == NodeRole.MASTER) {
                    server.handleRoleChangeToViewer(senderIp, senderPort, senderId);
                } else {
                    logger.error("Requested changing role to viewer, but node is not master");
                }
            }
        }
        if (msg.hasReceiverRole()) {
            if (msg.getReceiverRole() == SnakesProto.NodeRole.VIEWER) {
                if (senderIp == client.getMasterIp()) {
                    client.killSnake();
                } else {
                    logger.error("Kill snake command from not-master node");
                }
            }
            if (msg.getReceiverRole() == SnakesProto.NodeRole.DEPUTY) {
                client.changeRoleToDeputy();
            }
            if ((msg.getReceiverRole() == SnakesProto.NodeRole.MASTER) && (senderIp == client.getMasterIp()) && (senderPort == client.getMasterPort())) {
                changeRoleToMaster();
            }
        }
    }

    private void changeRoleToMaster() {
        localPlayer.setRole(NodeRole.MASTER);
        client.setIsMaster(true);
        server = new ServerImpl(localPlayer.getId(), client.getGameConfig(), localPlayer.getName(), localPlayer.getPlayerType(), client);
    }

    private void handleAnnouncement(SnakesProto.GameMessage.AnnouncementMsg msg, InetAddress senderIp, int senderPort, int senderId) {
        client.handleAnnouncement(msg.getGamesList(), senderIp, senderPort, senderId);
    }

    public void shutdown() {
        if (server != null) {
            server.shutdown();
        }
        disposable.dispose();
        mcastReceiver.shutdown();
        transferProtocol.shutdown();
    }
}
