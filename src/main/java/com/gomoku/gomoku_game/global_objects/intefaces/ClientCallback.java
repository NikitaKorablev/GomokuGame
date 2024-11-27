package com.gomoku.gomoku_game.global_objects.intefaces;

import com.gomoku.gomoku_game.global_objects.Chip;
import com.gomoku.gomoku_game.global_objects.enums.GameStatus;
import com.gomoku.gomoku_game.global_objects.enums.TableValue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    void onChipAdded(Chip chip) throws RemoteException;
    void setGameIsStarted() throws RemoteException;
    void finishGame(GameStatus status) throws RemoteException;
    void setPlayerColor(TableValue color) throws RemoteException;
    void enableSetChip() throws RemoteException;
    void disableSetChip() throws RemoteException;
    TableValue getPlayerColor() throws RemoteException;
}
