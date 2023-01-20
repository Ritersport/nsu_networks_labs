package ru.nsu.leorita.model;

public class Coord {
    private int x;
    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coord normalize(int fieldWidth, int fieldHeight){
        this.x = this.x % fieldWidth;
        this.y = this.y % fieldHeight;

        if (this.x < 0) {
            this.x += fieldWidth;
        }
        if (this.y < 0) {
            this.y += fieldHeight;
        }
        return this;
    }
}
