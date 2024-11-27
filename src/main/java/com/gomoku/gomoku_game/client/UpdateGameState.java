package com.gomoku.gomoku_game.client;

import com.gomoku.gomoku_game.global_objects.Chip;

public interface UpdateGameState {
    void drawNewChip(Chip chip);
    void onStartEvent();
    void setMessage(String message);
    void startButtonToRestart();
}
