package com.gomoku.gomokugame.global_objects;

import com.gomoku.gomokugame.global_objects.enums.TableValue;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class Chip implements Serializable {
    private int x;
    private int y;
    private TableValue color;

    public Chip(int x, int y, TableValue color) {
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

    public Circle drawChip(TableValue color, double rad) {
        Circle circle = new Circle(rad);
        circle.setFill(color.value());
        circle.setCenterX(rad);
        circle.setCenterY(rad);

        return circle;
    }

    @Override
    public String toString() {
        return "Chip{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
