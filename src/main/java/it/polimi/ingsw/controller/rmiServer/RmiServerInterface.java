package it.polimi.ingsw.controller.rmiServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote{

    void login(String username, String password, RmiPlayer rmiPlayer);

}
