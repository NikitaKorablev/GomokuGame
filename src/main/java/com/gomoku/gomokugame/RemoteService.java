package com.gomoku.gomokugame;

import com.gomoku.gomokugame.objects.Chip;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteService extends Remote {
    String connect() throws RemoteException;
    Boolean setChip(Chip ch) throws RemoteException;
    String getData(String request) throws RemoteException;

    void registerClient(ClientCallback client) throws RemoteException;
    void unregisterClient(ClientCallback client) throws RemoteException;
    void sendUpdateToClients(String message) throws RemoteException;

}
