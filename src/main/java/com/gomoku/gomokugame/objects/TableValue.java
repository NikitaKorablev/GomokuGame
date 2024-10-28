package com.gomoku.gomokugame.objects;

import java.awt.*;

public enum TableValue {
    BLACK, WHITE, NULL;

    public Color value() {
        return switch (this){
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case NULL -> throw new Error("NULL color");
        };
    }
}
