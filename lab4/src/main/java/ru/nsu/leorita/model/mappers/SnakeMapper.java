package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.Coord;
import ru.nsu.leorita.model.GameConfig;
import ru.nsu.leorita.model.Snake;
import ru.nsu.leorita.serializer.SnakesProto;

import java.util.ArrayList;
import java.util.List;

public class SnakeMapper {
    public static Snake toDomen(SnakesProto.GameState.Snake snakeProto, GameConfig config) {
        ArrayList<Coord> pointsList = new ArrayList<>();
        snakeProto.getPointsList().forEach(coord -> {
            pointsList.add(new Coord(coord.getX(), coord.getY()));
        });
        return new Snake(
                SnakeStateMapper.toDomen(snakeProto.getState()),
                snakeProto.getPlayerId(),
                pointsList, config);
    }

    public static SnakesProto.GameState.Snake toProtobuf(Snake snake) {
        List<SnakesProto.GameState.Coord> pointsListProto = new ArrayList<>();
        snake.getKeyPoints().forEach(coord -> pointsListProto.add(SnakesProto.GameState.Coord.newBuilder().setX(coord.getX()).setY(coord.getY()).build()));
        return SnakesProto.GameState.Snake.newBuilder()
                .setState(SnakeStateMapper.toProto(snake.getSnakeState()))
                .addAllPoints(pointsListProto)
                .setPlayerId(snake.getPlayerId())
                .setHeadDirection(DirectionMapper.toProtobuf(snake.getHeadDirection()))
                .build();
    }
}
