package com.gomoku.gomokugame.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.gomoku.gomokugame.global_objects.intefaces.ClientCallback;
import com.gomoku.gomokugame.global_objects.intefaces.RemoteService;
import com.gomoku.gomokugame.global_objects.Chip;
import com.gomoku.gomokugame.global_objects.enums.GameStatus;
import com.gomoku.gomokugame.global_objects.enums.TableValue;

public class ClientApp implements ClientCallback, Serializable {
    RemoteService server;
    TableValue playerColor = TableValue.NULL;
    Boolean gameIsStarted = false;
    Boolean setChipIsAllow = false;
    UpdateGameState updater;

    public ClientApp(UpdateGameState updater) {
        this.updater = updater;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            server = (RemoteService) registry.lookup("RemoteService");
            ClientCallback callback = (ClientCallback) UnicastRemoteObject.exportObject(this, 0);
            playerColor = server.registerListener(callback); // Регистрация на сервере
            System.out.println("Клиент зарегистрирован на сервере для получения обновлений.");
        } catch (Exception e) {
            System.err.println("Client not started. " + e.getMessage());
        }
    }

    @Override
    public void onChipAdded(Chip chip) throws RemoteException {
        updater.drawNewChip(chip);
    }

    @Override
    public void setGameIsStarted() throws RemoteException {
        updater.onStartEvent();
    }

    @Override
    public void updateStatus(GameStatus status) throws RemoteException {
        if (status == GameStatus.WIN) {
            System.out.println("Игра завершена. Вы выйграли.");
            updater.setMessage("Игра завершена. Вы выйграли.");
        } else {
            System.out.println("Игра заврешена. Вы проиграли.");
            updater.setMessage("Игра завершена. Вы проиграли.");
        }
        gameIsStarted = false;
        setChipIsAllow = false;
    }

    @Override
    public void setPlayerColor(TableValue playerColor) {
        this.playerColor = playerColor;
    }

    @Override
    public void enableSetChip() throws RemoteException {
        setChipIsAllow = true;
        updater.setMessage("Ваш ход");
    }

    @Override
    public void disableSetChip() throws RemoteException {
        setChipIsAllow = false;
        updater.setMessage("Ход противника");
    }

    @Override
    public TableValue getPlayerColor() throws RemoteException {
        return playerColor;
    }

    public void startGame() {
        try {
            server.startGame();
            gameIsStarted = true;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean setChip(Chip chip) {
        try {
            return server.setChip(chip);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "ClientApp{" +
                "server=" + server +
                ", playerColor=" + playerColor +
                '}';
    }
}

