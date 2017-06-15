package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.rmiClient.RMIClientInterface;

import java.io.IOException;
import java.rmi.Remote;

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
    public void joinFirstRoom(String username) throws RoomException;

    /**
     *
     */
    public void Configuration createNewRoom(String username, int maxPlayer);

}
