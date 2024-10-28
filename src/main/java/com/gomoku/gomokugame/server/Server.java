package com.gomoku.gomokugame.server;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.gomoku.gomokugame.ClientCallback;
import com.gomoku.gomokugame.RemoteService;
import com.gomoku.gomokugame.objects.Chip;
import com.gomoku.gomokugame.objects.GameTable;
import io.reactivex.rxjava3.core.Single;

public class Server extends UnicastRemoteObject implements RemoteService {
    private final ArrayList<String> playersList = new ArrayList<>();
    private final GameTable table = new GameTable();
    private Boolean gameStarted = false;

    private List<ClientCallback> clients = new ArrayList<>();;

    public Server() throws RemoteException {}

    @Override
    public String connect() throws RemoteException {
        if (playersList.size() > 2) return "-1";

        String playerId = "player " + playersList.size();
        playersList.add(playerId);

        if (playersList.size() == 2) gameStarted = true;
        return playerId;
    }

    @Override
    public Boolean setChip(Chip ch) throws RemoteException {
        if (!gameStarted) return false;

        table.setChip(ch);


        return true;
    }

    @Override
    public String getData(String request) throws RemoteException {
        // Обрабатываем запрос и возвращаем результат асинхронно
        return Single.fromCallable(() -> {
            System.out.println("Обработка запроса: " + request);
            Thread.sleep(2000); // Симуляция задержки
            return "Ответ от сервера для: " + request;
        }).blockingGet();
    }

    //-----------------------------------------------------------------------

    @Override
    public void registerClient(ClientCallback client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void unregisterClient(ClientCallback client) throws RemoteException {
        clients.remove(client);
    }

    @Override
    public void sendUpdateToClients(String message) throws RemoteException {
        for (ClientCallback client : clients) {
            try {
                client.notifyUpdate(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    //-----------------------------------------------------------------------

    public static void main(String[] args) {
        try {
            Server server = new Server();
            RemoteService stub = (RemoteService) UnicastRemoteObject.exportObject(server, 0);

            // Регистрация сервиса в RMI Registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RemoteService", stub);

            System.out.println("Сервер готов к работе...");
        } catch (RemoteException e) {
            System.err.println("Server not started. " + e.getMessage());
        }
    }
}
