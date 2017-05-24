package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.rmiClient.RmiClientInterface;

import java.rmi.Remote;

public interface RmiServerInterface extends Remote{

    String login(String username, String password, RmiClientInterface rmiPlayer) throws LoginException;

    void signin(String username, String password) throws LoginException;

}
