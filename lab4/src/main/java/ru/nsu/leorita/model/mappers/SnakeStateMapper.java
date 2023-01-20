package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.SnakeState;
import ru.nsu.leorita.serializer.SnakesProto;

public class SnakeStateMapper {
    public static SnakeState toDomen(SnakesProto.GameState.Snake.SnakeState snakeStateProto) {
        switch (snakeStateProto) {
            case ALIVE: return SnakeState.ALIVE;
            case ZOMBIE: return SnakeState.ZOMBIE;
        }
        return null;
    }
    public static SnakesProto.GameState.Snake.SnakeState toProto(SnakeState snakeState) {
        switch (snakeState) {
            case ALIVE: return SnakesProto.GameState.Snake.SnakeState.ALIVE;
            case ZOMBIE: return SnakesProto.GameState.Snake.SnakeState.ZOMBIE;
        }
        return null;
    }
}
