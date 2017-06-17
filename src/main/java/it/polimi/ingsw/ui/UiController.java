package it.polimi.ingsw.ui;

import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.ui.cli.ConnectionType;

/**
 * Interface implemented by the Game class, contains all functions related to the Game that need to
 * be updated in real time and notified for every change.
 */
public interface UiController {

    void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException;

    void loginPlayer(String nickname, String password, boolean flag);

    void joinRoom();

    void createRoom(int maxPlayers);

}
