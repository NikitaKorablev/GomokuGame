package com.gomoku.gomokugame;

import java.rmi.RemoteException;

public interface ClientCallback {
    void notifyUpdate(String message) throws RemoteException;
}
