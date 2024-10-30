package com.gomoku.gomokugame.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.gomoku.gomokugame.global_objects.intefaces.ClientCallback;
import com.gomoku.gomokugame.global_objects.intefaces.RemoteService;
import com.gomoku.gomokugame.global_objects.Chip;
import com.gomoku.gomokugame.global_objects.enums.GameStatus;
import com.gomoku.gomokugame.global_objects.GameTable;
import com.gomoku.gomokugame.global_objects.enums.TableValue;
import io.reactivex.rxjava3.core.Single;

public class Server extends UnicastRemoteObject implements RemoteService {
    private final GameTable table = new GameTable();
    private Boolean gameStarted = false;
    public static final int port=8080;

    private final List<ClientCallback> clientsListeners = new ArrayList<>();;

    public Server() throws RemoteException {}

    @Override
    public Boolean setChip(Chip ch) throws RemoteException {
//        if (!gameStarted) return false;
        table.setChip(ch);
        try {
            sendUpdateToClients(ch);
            if (table.isWin(ch)) notifyGameStatus(ch);
        } catch (Exception err) {
            System.err.println(err.getMessage());
            return false;
        }

        return true;

    }

    @Override
    public void startGame() throws RemoteException {
        if (clientsListeners.size() == 2) {
            gameStarted = true;
            notifyGameStarted();
        }
    }

    //-----------------------------------------------------------------------

    @Override
    public TableValue registerListener(ClientCallback listener) throws RemoteException {
        clientsListeners.add(listener);
        listener.setPlayerColor(clientsListeners.size() == 1 ? TableValue.WHITE : TableValue.BLACK);
        System.out.println("New player. Color: " + listener.getPlayerColor());
        return listener.getPlayerColor();
    }

    @Override
    public void unregisterClient(ClientCallback listener) throws RemoteException {
        clientsListeners.remove(listener);
    }

    public void sendUpdateToClients(Chip chip) throws RemoteException {
        for (ClientCallback listener : clientsListeners) {
            try {
                listener.onChipAdded(chip);
                if (chip.getColor() == listener.getPlayerColor()) listener.disableSetChip();
                else listener.enableSetChip();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyGameStatus(Chip chip) throws RemoteException {
        for (ClientCallback listener : clientsListeners) {
            try {
                GameStatus status =
                        chip.getColor() == listener.getPlayerColor()? GameStatus.WIN : GameStatus.LOOSE;
                listener.updateStatus(status);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyGameStarted() throws RemoteException {
        for(ClientCallback listener: clientsListeners) {
            try {
                listener.setGameIsStarted();
                if (listener.getPlayerColor() == TableValue.WHITE) listener.enableSetChip();
                else listener.disableSetChip();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    //-----------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        // Регистрация сервиса в RMI Registry
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("RemoteService", server);

        System.out.println("Сервер готов к работе...");
    }
}
