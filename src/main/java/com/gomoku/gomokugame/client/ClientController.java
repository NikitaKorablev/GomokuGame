package com.gomoku.gomokugame.client;

import com.gomoku.gomokugame.global_objects.Chip;
import com.gomoku.gomokugame.global_objects.enums.TableValue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientController implements UpdateGameState {
    private final int TABLE_ROWS_COUNT = 15;
    private final int TABLE_COLUMNS_COUNT = 15;

    private ClientApp gameController = null;
    private final ArrayList<ArrayList<Pane>> table = new ArrayList<>();

    @FXML
    private GridPane tableGrid;
    @FXML
    private Button connectButton;
    @FXML
    private Button startButton;

    @FXML
    public void initialize() throws Exception {
        startButton.setDisable(true);


    }

    @FXML
    protected void onConnectButtonClicked() {
        if (gameController != null) return;
        new Thread(() -> {
            gameController = new ClientApp(this);
            startButton.setDisable(false);
            connectButton.setDisable(true);
        }).start();

        Platform.runLater(this::tableInit);
    }

    @FXML
    protected void onStartButtonClicked() {
        if (gameController.startGame()) startButton.setDisable(true);

        gameController.setChip(new Chip(1, 1, TableValue.BLACK));
    }

    @Override
    public void drawNewChip(Chip chip) {
        Platform.runLater(() -> {
            Pane pane = table.get(chip.getX()).get(chip.getY());
            Circle circle = chip.drawChip(chip.getColor(), pane.getHeight() / 2);
            pane.getChildren().add(circle);
//            System.out.println("");
        });
    }

    private void tableInit() {
        List<Node> nodeList = tableGrid.getChildren();
        nodeList.sort(Comparator
                .comparingInt((Node node) -> GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0)
                .thenComparingInt(node -> GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0));

        for (int i=0; i < TABLE_ROWS_COUNT; ++i) {
            ArrayList<Pane> paneRow = new ArrayList<>();
            for (int j=0; j < TABLE_COLUMNS_COUNT; ++j) {
                Node node = nodeList.get(TABLE_ROWS_COUNT * i + j);
                Pane pane = node instanceof Pane? (Pane) node : null;
                if (pane == null) throw new RuntimeException("Undefined pane: " + i + " " + j);
                setOnHoverEvent(pane);
                setOnClickEvent(pane, i, j);
                paneRow.add(pane);
            }
            table.add(paneRow);
        }
    }

    private void setOnHoverEvent(Pane pane) {
        Color originalColor = Color.WHITE;
        pane.setStyle("-fx-background-color: " + toRgbString(originalColor) + "; -fx-border-color: black;");
        // Событие при наведении
        pane.setOnMouseEntered(event ->
                pane.setStyle("-fx-background-color: gray; -fx-border-color: black;")
        );
        // Событие при выходе курсора
        pane.setOnMouseExited(event ->
                pane.setStyle("-fx-background-color: " + toRgbString(originalColor) + "; -fx-border-color: black;")
        );
    }

    private void setOnClickEvent(Pane pane, int i, int j) {
        pane.setOnMouseClicked(event  -> {
            if (gameController == null) return;
            boolean res = gameController.setChip(new Chip(i, j, gameController.playerColor));
            if (!res) System.err.println("Set chip: " + i + " " + j + ", is failed");
        });
    }

    private String toRgbString(Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        return String.format("rgb(%d, %d, %d);", red, green, blue);
    }
}