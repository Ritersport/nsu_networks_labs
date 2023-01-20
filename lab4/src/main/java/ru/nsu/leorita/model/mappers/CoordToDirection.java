package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.Coord;
import ru.nsu.leorita.model.Direction;

public class CoordToDirection {
    public static Direction map(Coord coord) {
        int x = coord.getX();;
        int y = coord.getY();
        if ((x == 0 && y == 0) || (x != 0 && y != 0)) return null;
        if (x > 0) return Direction.RIGHT;
        if (x < 0) return Direction.LEFT;
        if (y > 0) return Direction.DOWN;
        if (y < 0) return Direction.UP;
        return null;
    }
}
