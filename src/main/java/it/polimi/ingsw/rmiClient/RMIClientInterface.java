package it.polimi.ingsw.rmiClient;

import it.polimi.ingsw.model.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This class represents the RMI client interface used to remote method invocation
 * from the server to the client.
 */
public interface RMIClientInterface extends Remote{

    void setGameInfo(Game game) throws RemoteException;

    String ping() throws RemoteException;
}
