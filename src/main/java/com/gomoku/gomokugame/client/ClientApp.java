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
    UpdateGameState updater;

    public ClientApp(UpdateGameState updater) {
        this.updater = updater;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            server = (RemoteService) registry.lookup("RemoteService");
            ClientCallback callback = (ClientCallback) UnicastRemoteObject.exportObject(this, 0);
            playerColor = server.registerListener(callback); // Регистрация на сервере
            System.out.println("Клиент зарегистрирован на сервере для получения обновлений.");

            Thread.sleep(5000); // Ожидание завершения обработки для демонстрации
        } catch (Exception e) {
            System.err.println("Client not started. " + e.getMessage());
        }
    }

    @Override
    public void onChipAdded(Chip chip) throws RemoteException {
        System.out.println("New chip: " + chip.toString());
        updater.drawNewChip(chip);
    }

    @Override
    public void updateStatus(GameStatus status) throws RemoteException {
        System.out.println("New status " + status);
    }

    @Override
    public void setPlayerColor(TableValue playerColor) {
        this.playerColor = playerColor;
    }

    @Override
    public TableValue getPlayerColor() throws RemoteException {
        return playerColor;
    }

    public Boolean startGame() {
        try {
            return server.startGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean setChip(Chip chip) {
        try {
            Boolean res = server.setChip(chip);
            if (res) System.out.println("Игра завершена!");
            return res;
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

