package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.LoginException;

public interface ServerInterface {

    /**
     * Method to login a new user.
     * @param nickname
     * @param password
     * @param player
     */
    void login(String nickname, String password, AbstractPlayer player) throws LoginException;

    void signup(String username, String password, AbstractPlayer player);

    AbstractPlayer getUser(String username);

}
