package com.gomoku.gomokugame.global_objects.intefaces;

import com.gomoku.gomokugame.global_objects.Chip;
import com.gomoku.gomokugame.global_objects.enums.TableValue;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteService extends Remote {
    Boolean setChip(Chip ch) throws RemoteException;
    void startGame() throws RemoteException;
    void nullifyGame() throws RemoteException;

    TableValue registerListener(ClientCallback listener) throws RemoteException;
    void unregisterClient(ClientCallback client) throws RemoteException;
}
