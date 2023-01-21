package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.NodeRole;
import ru.nsu.leorita.serializer.SnakesProto;

public class RoleMapper {
    public static SnakesProto.NodeRole toProtobuf(NodeRole role) {
        switch (role) {
            case DEPUTY:
                return SnakesProto.NodeRole.DEPUTY;
            case MASTER:
                return SnakesProto.NodeRole.MASTER;
            case NORMAL:
                return SnakesProto.NodeRole.NORMAL;
            case VIEWER:
                return SnakesProto.NodeRole.VIEWER;
        }
        return null;
    }

    public static NodeRole toDomen(SnakesProto.NodeRole role) {
        switch (role) {
            case NORMAL:
                return NodeRole.NORMAL;
            case VIEWER:
                return NodeRole.VIEWER;
            case MASTER:
                return NodeRole.MASTER;
            case DEPUTY:
                return NodeRole.DEPUTY;
        }
        return null;
    }
}
