package it.polimi.ingsw.socketServer;

import it.polimi.ingsw.exceptions.LoginException;

/**
 * Created by andrea on 22/05/17.
 */
public interface CommunicationRulesInterface {

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
