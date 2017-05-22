package it.polimi.ingsw.controller.rmiClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientInterface extends Remote{
    void serverConnected() throws RemoteException;
}
