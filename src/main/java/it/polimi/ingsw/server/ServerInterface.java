package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginException;

public interface ServerInterface {

    /**
     * Method to login a new user.
     * @param nickname
     * @param password
     * @param player
     */
    void login(String nickname, String password, AbstractPlayer player) throws LoginException;

    void signin(String username, String password) throws LoginException;

    AbstractPlayer getUser(String username);

}
