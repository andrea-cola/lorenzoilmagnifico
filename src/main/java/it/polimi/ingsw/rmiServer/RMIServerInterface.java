package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.rmiClient.RMIClientInterface;
import it.polimi.ingsw.utility.Configuration;
import sun.nio.ch.Net;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI server interface used for remote method invocation from client to server.
 */
public interface RMIServerInterface extends Remote{

    /**
     * Player login method.
     * @param username provided by the client to login.
     * @param password provided by the client to login.
     * @param rmiPlayer is trying to login.
     * @return a unique identifier of the player.
     * @throws IOException if errors occur during login proceedings.
     */
    String loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException;

    /**
     * Player sign in method.
     * @param username provided by the client to sign in.
     * @param password provided by the client to sign in.
     * @throws IOException
     */
    void signInPlayer(String username, String password) throws IOException;

    /**
     * Remote method to join the player to the first game room
     * @param username which is making the request
     * @throws RoomException if the server is not reachable
     */
    void joinFirstRoom(String username) throws RoomException, IOException;

    /**
     * Create a new room.
     * @param id to get the player from the cache.
     * @param maxPlayersNumber allowed in the room.
     * @return the configuration number.
     */
    Configuration createNewRoom(String id, int maxPlayersNumber) throws RoomException, IOException;

}