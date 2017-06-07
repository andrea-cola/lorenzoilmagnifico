package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.rmiClient.RMIClientInterface;

import java.io.IOException;
import java.rmi.Remote;

public interface RMIServerInterface extends Remote{

    String loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException;

    void signInPlayer(String username, String password) throws IOException;

}
