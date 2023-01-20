package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.GamePlayer;
import ru.nsu.leorita.serializer.SnakesProto;

import java.net.InetAddress;

public class PlayerMapper {
    public static GamePlayer toDomen(SnakesProto.GamePlayer player) {
        try {
            return new GamePlayer(player.getName(), player.getId(),

                    InetAddress.getByName(player.getIpAddress()),

                    player.getPort(), RoleMapper.toDomen(player.getRole()), TypeMapper.toDomen(player.getType()), player.getScore());
        } catch (Exception ignored) {
            return new GamePlayer(player.getName(), player.getId(), null, TypeMapper.toDomen(player.getType()), player.getScore());
        }
    }

    public static SnakesProto.GamePlayer toProtobuf(GamePlayer player) {
        return SnakesProto.GamePlayer.newBuilder().setName(player.getName()).setId(player.getId()).setIpAddress(player.getIpAddress().toString()).setPort(player.getPort()).setRole(RoleMapper.toProtobuf(player.getRole())).setType(TypeMapper.toProtobuf(player.getPlayerType())).setScore(player.getScore()).build();
    }

    public static SnakesProto.GamePlayer localToProtobuf(GamePlayer player) {
        return SnakesProto.GamePlayer.newBuilder().setName(player.getName()).setId(player.getId()).setRole(RoleMapper.toProtobuf(player.getRole())).setType(TypeMapper.toProtobuf(player.getPlayerType())).setScore(player.getScore()).build();
    }
}
