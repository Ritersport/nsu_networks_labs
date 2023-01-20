package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.GameAnnouncement;
import ru.nsu.leorita.model.GameConfig;
import ru.nsu.leorita.model.GamePlayer;
import ru.nsu.leorita.serializer.SnakesProto;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AnnouncementMapper {
    public static GameAnnouncement toDomen(SnakesProto.GameAnnouncement announcement, InetAddress masterIp, int masterPort, int masterId) {
        List<SnakesProto.GamePlayer> playerList = announcement.getPlayers().getPlayersList();
        ConcurrentHashMap<Integer, GamePlayer> gamePlayers = new ConcurrentHashMap<>();
        playerList.forEach(player -> {

            GamePlayer gamePlayer = PlayerMapper.toDomen(player);
            gamePlayers.put(gamePlayer.getId(), gamePlayer);

        });
        GameConfig gameConfig = ConfigMapper.toDomen(announcement.getConfig());
        return new GameAnnouncement(gamePlayers, gameConfig, announcement.getCanJoin(), announcement.getGameName(), masterIp, masterPort, masterId);
    }

    public static SnakesProto.GameAnnouncement toProtobuf(GameAnnouncement announcement) {
        List<SnakesProto.GamePlayer> playersProto = new ArrayList<>();
        announcement.getPlayers().forEach((id, player) -> {
            SnakesProto.GamePlayer playerProto;
            if (player.getIsLocal()) {
                playerProto = PlayerMapper.localToProtobuf(player);
            } else {
                playerProto = PlayerMapper.toProtobuf(player);
            }
            playersProto.add(playerProto);
        });
        SnakesProto.GamePlayers gamePlayers = SnakesProto.GamePlayers.newBuilder().addAllPlayers(playersProto).build();

        return SnakesProto.GameAnnouncement.newBuilder()
                .setCanJoin(announcement.getCanJoin())
                .setConfig(ConfigMapper.toProtobuf(announcement.getConfig()))
                .setPlayers(gamePlayers)
                .setGameName(announcement.getGameName())
                .build();
    }
}
