package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.PlayerType;
import ru.nsu.leorita.serializer.SnakesProto;

public class TypeMapper {
    public static SnakesProto.PlayerType toProtobuf(PlayerType type) {
        switch (type) {
            case HUMAN:
                return SnakesProto.PlayerType.HUMAN;
            case ROBOT:
                return SnakesProto.PlayerType.ROBOT;
        }
        return null;
    }

    public static PlayerType toDomen(SnakesProto.PlayerType type) {
        switch (type) {
            case ROBOT:
                return PlayerType.ROBOT;
            case HUMAN:
                return PlayerType.HUMAN;
        }
        return null;
    }
}
