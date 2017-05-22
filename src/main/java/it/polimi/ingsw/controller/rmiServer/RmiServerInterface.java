package it.polimi.ingsw.controller.rmiServer;

import it.polimi.ingsw.controller.exceptions.LoginException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote{

    void login(String username, String password, RmiPlayer rmiPlayer) throws LoginException;

}
