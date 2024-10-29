package com.gomoku.gomokugame.global_objects;

import com.gomoku.gomokugame.global_objects.enums.TableValue;

import java.util.ArrayList;

public class GameTable {
    private ArrayList<ArrayList<TableValue>> table = new ArrayList<>();
    private final static int availableChipCount = 4;
    private static int tmpAvailableChipCount = availableChipCount;

    public GameTable() {
        for (int i = 0; i < 15; ++i) {
            ArrayList<TableValue> row = new ArrayList<>();
            for (int j = 0; j < 15; ++j) {
                row.add(TableValue.NULL);
            }
            table.add(row);
        }
    }

    public ArrayList<ArrayList<TableValue>> getTable() {
        return table;
    }

    public boolean setChip(Chip chip) {
        if (table.get(chip.getX()).get(chip.getY()) == TableValue.NULL) return false;

        table.get(chip.getX()).set(chip.getY(), chip.getColor());
        return true;
    }

    public boolean isWin(Chip chip) {
        if (checkLine(chip, 1, 1)) return true;
        if (checkLine(chip, 1, 0)) return true;
        if (checkLine(chip, 1, -1)) return true;
        return checkLine(chip, 0, 1);
    }

    private boolean checkLine(Chip chip, int x_right, int y_up) {
        int x = chip.getX()+x_right;
        int y = chip.getY()-y_up;
        updateTmpAvailableChipCount(x, y, chip.getColor(), x_right, y_up);

        x = chip.getX()+(-x_right);
        y = chip.getY()+(-y_up);
        updateTmpAvailableChipCount(x, y, chip.getColor(), x_right, y_up);

        return tmpAvailableChipCount == 0;
    }

    private void updateTmpAvailableChipCount(int x, int y, TableValue color, int x_right, int y_up) {
        if (x < 0 || y < 0 || x >= 15 || y >= 15) return;
        if (table.get(x).get(y) == TableValue.NULL) return;

        if (table.get(x).get(y) == color) tmpAvailableChipCount--;
        if (tmpAvailableChipCount < 0) return;

        x = x + x_right; y = y + y_up;
        updateTmpAvailableChipCount(x, y, color, x_right, y_up);
    }

    @Override
    public String toString() {
        return "GameTable{" +
                "table=" + table +
                '}';
    }
}
