package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.Direction;
import ru.nsu.leorita.serializer.SnakesProto;

public class DirectionMapper {
    public static Direction toDomen(SnakesProto.Direction directionProto) {
        switch (directionProto) {
            case UP:
                return Direction.UP;
            case DOWN:
                return Direction.DOWN;
            case LEFT:
                return Direction.LEFT;
            case RIGHT:
                return Direction.RIGHT;
        }
        return null;
    }

    public static SnakesProto.Direction toProtobuf(Direction direction) {
        switch (direction) {
            case UP:
                return SnakesProto.Direction.UP;
            case DOWN:
                return SnakesProto.Direction.DOWN;
            case LEFT:
                return SnakesProto.Direction.LEFT;
            case RIGHT:
                return SnakesProto.Direction.RIGHT;
        }
        return null;
    }
}
