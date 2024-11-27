package com.gomoku.gomoku_game.global_objects.enums;

import java.io.Serializable;
import java.awt.Color;

public enum TableValue implements Serializable {
    BLACK, WHITE, GREY, NULL;

    public Color value() {
        return switch (this) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.GRAY;
            case GREY -> new Color(60, 60, 60);
            case NULL -> throw new Error("NULL color");
        };
    }

    public javafx.scene.paint.Paint getValue() {
        return switch (this) {
            case BLACK -> javafx.scene.paint.Color.BLACK;
            case WHITE -> javafx.scene.paint.Color.GRAY;
            case GREY -> javafx.scene.paint.Color.rgb(151, 151, 151);
            case NULL -> throw new Error("NULL color");
        };
    }

    public String toRgbString() {
        return switch (this) {
            case BLACK -> String.format("rgb(%d, %d, %d);", 0, 0, 0);
            case WHITE -> String.format("rgb(%d, %d, %d);", 250, 250, 250);
            case GREY -> String.format("rgb(%d, %d, %d);", 200, 200, 200);
            case NULL -> throw new Error("NULL color");
        };
    }
}
