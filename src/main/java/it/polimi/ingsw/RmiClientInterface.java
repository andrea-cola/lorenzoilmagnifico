package it.polimi.ingsw;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientInterface extends Remote{
    void serverConnected() throws RemoteException;
}
