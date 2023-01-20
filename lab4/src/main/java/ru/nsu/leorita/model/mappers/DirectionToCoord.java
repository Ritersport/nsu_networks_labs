package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.Coord;
import ru.nsu.leorita.model.Direction;

public class DirectionToCoord {
    public static Coord map(Direction dir) {
        switch (dir) {
            case UP: return new Coord(0, -1);
            case DOWN: return new Coord(0, 1);
            case LEFT: return new Coord(-1, 0);
            case RIGHT: return new Coord(1, 0);
        }
        return null;
    }
}
