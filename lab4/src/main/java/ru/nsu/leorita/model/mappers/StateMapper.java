package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.*;
import ru.nsu.leorita.serializer.SnakesProto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StateMapper {
    public static GameState toDomen(SnakesProto.GameState stateProto, int localId, GameConfig config) {
        HashMap<Integer, Snake> snakes = new HashMap<>();
        stateProto.getSnakesList().forEach(snake -> snakes.put(snake.getPlayerId(), SnakeMapper.toDomen(snake, config)));
        ArrayList<Coord> foods = new ArrayList<>();
        stateProto.getFoodsList().forEach(coord -> foods.add(new Coord(coord.getX(), coord.getY())));
        ConcurrentHashMap<Integer, GamePlayer> players = new ConcurrentHashMap<>();
        stateProto.getPlayers().getPlayersList().forEach(player -> players.put(player.getId(), PlayerMapper.toDomen(player)));
        return new GameState(config, stateProto.getStateOrder(), snakes, foods, players, localId);
    }

    public static SnakesProto.GameState toProtobuf(GameState state, int localId) {
        List<SnakesProto.GameState.Snake> snakesProto = new ArrayList<>();
        state.getSnakes().forEach((id, snake) -> snakesProto.add(SnakeMapper.toProtobuf(snake)));
        List<SnakesProto.GameState.Coord> coordsProto = new ArrayList<>();
        state.getFoods().forEach(coord -> coordsProto.add(SnakesProto.GameState.Coord.newBuilder().setX(coord.getX()).setY(coord.getY()).build()));
        List<SnakesProto.GamePlayer> playersProto = new ArrayList<>();
        state.getPlayers().forEach((id, player) -> {
            if (id != localId) {
                playersProto.add(PlayerMapper.toProtobuf(player));
            } else {
                playersProto.add(PlayerMapper.localToProtobuf(player));
            }
        });
        return SnakesProto.GameState.newBuilder()
                .setStateOrder(state.getStateOrder())
                .addAllSnakes(snakesProto)
                .addAllFoods(coordsProto)
                .setPlayers(SnakesProto.GamePlayers.newBuilder().addAllPlayers(playersProto))
                .build();
    }
}
