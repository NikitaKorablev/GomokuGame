package com.gomoku.gomokugame.objects;

import javafx.scene.control.Tab;

import java.awt.*;

public class Chip {
    private int x;
    private int y;
    private TableValue color;

    Chip(int x, int y, TableValue color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public TableValue getColor() { return color; }

    public void setX(int x) throws Exception {
        if (x < 0 || x >= 15) throw new Exception("X out of bounds");
        this.x = x;
    }
    public void setY(int y) throws Exception {
        if (y < 0 || y >= 15) throw new Exception("Y out of bounds");
        this.y = y;
    }
    public void setColor(TableValue color) { this.color = color; }

    @Override
    public String toString() {
        return "Chip{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
