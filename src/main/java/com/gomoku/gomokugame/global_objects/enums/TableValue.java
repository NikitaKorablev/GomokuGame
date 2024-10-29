package com.gomoku.gomokugame.global_objects.enums;

import java.io.Serializable;
import javafx.scene.paint.Color;

public enum TableValue implements Serializable {
    BLACK, WHITE, NULL;

    public javafx.scene.paint.Paint value() {
        return switch (this) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.GREY;
            case NULL -> throw new Error("NULL color");
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "Черный";
            case WHITE -> "Белый";
            case NULL -> throw new Error("NULL color");
        };
    }
}
