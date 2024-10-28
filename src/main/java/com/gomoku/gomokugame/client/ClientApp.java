package com.gomoku.gomokugame.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.gomoku.gomokugame.ClientCallback;
import com.gomoku.gomokugame.RemoteService;

public class ClientApp {

    public static void main(String[] args) {
        try {
            // Поиск сервиса в реестре RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RemoteService server = (RemoteService) registry.lookup("RemoteService");

            ClientCallback client = new Client();
            server.registerClient(client);
            System.out.println("Клиент зарегистрирован на сервере для получения обновлений.");

            // Отправка запроса и асинхронное ожидание результата
            String response = server.getData("Запрос от клиента");
            System.out.println("Ответ от сервера: " + response);

            System.out.println("Запрос отправлен...");
            Thread.sleep(5000); // Ожидание завершения обработки для демонстрации
        } catch (Exception e) {
            System.err.println("Client not started. " + e.getMessage());
        }
    }
}

