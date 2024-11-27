package com.gomoku.gomoku_game.client;

import com.gomoku.gomoku_game.global_objects.Chip;
import com.gomoku.gomoku_game.global_objects.enums.TableValue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;

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
    private Label messageHolder;

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
    public void onStartButtonClicked() {
        if (!gameController.gameIsStarted) {
            startButton.setDisable(true);
            gameController.startGame();
        }
    }

    @Override
    public void drawNewChip(Chip chip) {
        Platform.runLater(() -> {
            Pane pane = table.get(chip.getX()).get(chip.getY());
            Circle circle = chip.drawChip(chip.getColor(), pane.getHeight() / 2);
            pane.getChildren().add(circle);
        });
    }

    @Override
    public void onStartEvent() {
        if (!gameController.gameIsStarted) {
            gameController.gameIsStarted = true;
            startButton.setDisable(true);
        }
    }

    @Override
    public void setMessage(String message) {
        Platform.runLater(() ->
            messageHolder.setText(message)
        );
    }

    @Override
    public void startButtonToRestart() {
        startButton.setOnMouseClicked(event -> {
            nullifyTable();
            gameController.nullifyTable();
            startButton.setDisable(true);
        });

        Platform.runLater(() -> {
            startButton.setText("Restart");
            startButton.setDisable(false);
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

    private void nullifyTable() {
        Platform.runLater(() -> {
            for (int i=0; i < TABLE_ROWS_COUNT; ++i) {
                for (int j=0; j < TABLE_COLUMNS_COUNT; ++j) {
                    table.get(i).get(j).getChildren().removeAll();
                }
            }
        });
    }

    private void setOnHoverEvent(Pane pane) {
        String originalColor = pane.getStyle();
        // Событие при наведении
        pane.setOnMouseEntered(event -> {
            if (!pane.getChildren().isEmpty()) return;

            if (gameController.gameIsStarted && gameController.setChipIsAllow)
                pane.setStyle("-fx-background-color: " + TableValue.GREY.toRgbString() +"; -fx-border-color: black;");
        });
        // Событие при выходе курсора
        pane.setOnMouseExited(event -> {
            if (gameController.gameIsStarted)
                pane.setStyle(originalColor);
        });
    }

    private void setOnClickEvent(Pane pane, int i, int j) {
        pane.setOnMouseClicked(event  -> {
            if (gameController == null
                    || !gameController.setChipIsAllow
                    || !pane.getChildren().isEmpty()) return;
            boolean res = gameController.setChip(new Chip(i, j, gameController.playerColor));
            if (!res) System.err.println("Set chip: " + i + " " + j + ", is failed");
        });
    }
}