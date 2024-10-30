package com.gomoku.gomokugame.global_objects.intefaces;

import com.gomoku.gomokugame.global_objects.Chip;
import com.gomoku.gomokugame.global_objects.enums.GameStatus;
import com.gomoku.gomokugame.global_objects.enums.TableValue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    void onChipAdded(Chip chip) throws RemoteException;
    void setGameIsStarted() throws RemoteException;
    void updateStatus(GameStatus status) throws RemoteException;
    void setPlayerColor(TableValue color) throws RemoteException;
    void enableSetChip() throws RemoteException;
    void disableSetChip() throws RemoteException;
    TableValue getPlayerColor() throws RemoteException;
}
