package com.gomoku.gomokugame.client;

import com.gomoku.gomokugame.ClientCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ClientCallback {
    public Client() throws RemoteException {}

    @Override
    public void notifyUpdate(String message) throws RemoteException {
        System.out.println("Получено обновление от сервера: " + message);
    }
}
