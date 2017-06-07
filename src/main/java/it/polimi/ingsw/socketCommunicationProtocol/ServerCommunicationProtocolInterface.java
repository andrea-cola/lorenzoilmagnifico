package it.polimi.ingsw.socketCommunicationProtocol;

import it.polimi.ingsw.exceptions.LoginException;

/**
 * Interface used as callback from clients.
 */
public interface ServerCommunicationProtocolInterface {

    /**
     * Method to handle user loginPlayer request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if loginPlayer error occurs.
     */
    void loginPlayer(String username, String password) throws LoginException;

    /**
     * Method to handle user sign in request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if signInPlayer error occurs.
     */
    void signInPlayer(String username, String password) throws LoginException;

}
