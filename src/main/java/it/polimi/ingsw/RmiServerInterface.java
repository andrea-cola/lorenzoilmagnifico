package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.LoginException;

import java.rmi.Remote;

public interface RmiServerInterface extends Remote{

    void login(String username, String password, RmiPlayer rmiPlayer) throws LoginException;

}
