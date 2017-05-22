package it.polimi.ingsw.controller.socketServer;

import it.polimi.ingsw.controller.exceptions.LoginException;

/**
 * Created by andrea on 22/05/17.
 */
public interface SocketCommunicationRulesInterface {

    /**
     * Method to handle user login.
     * @param username
     * @param password
     */
    void login(String username, String password) throws LoginException; //gestire exception

    /**
     * Method to join in a room.
     */
    void joinGame();

    /**
     * Place family member on the main board
     */
    void placeFamilyMember();

    /**
     * End turn.
     */
    void endTurn();

}
