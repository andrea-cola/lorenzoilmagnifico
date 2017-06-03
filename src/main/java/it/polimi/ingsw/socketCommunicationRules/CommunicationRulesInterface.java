package it.polimi.ingsw.socketCommunicationRules;

import it.polimi.ingsw.exceptions.LoginException;

public interface CommunicationRulesInterface {

    /**
     * Method to handle user login request.
     * @param username
     * @param password
     */
    void login(String username, String password) throws LoginException;

    /**
     * Method to handle user sign in request.
     * @param username
     * @param password
     * @throws LoginException
     */
    void signin(String username, String password) throws LoginException;

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
