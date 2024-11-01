package com.gomoku.gomokugame.client;

import com.gomoku.gomokugame.global_objects.Chip;

public interface UpdateGameState {
    void drawNewChip(Chip chip);
    void onStartEvent();
    void setMessage(String message);
    void startButtonToRestart();
}
