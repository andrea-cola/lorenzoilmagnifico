package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginException;

public interface ServerInterface {

    void loginPlayer(AbstractPlayer player, String nickname, String password) throws LoginException;

    void signInPlayer(String username, String password) throws LoginException;

    AbstractPlayer getUser(String username);

}
