package it.polimi.ingsw.ui;

import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.ui.cli.ConnectionType;

import java.util.List;

/**
 * Interface implemented by the Game class, contains all functions related to the Game that need to
 * be updated in real time and notified for every change.
 */
public interface UiController {

    String getUsername();

    void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException;

    void loginPlayer(String nickname, String password, boolean flag);

    void joinRoom();

    void createRoom(int maxPlayers);

    void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile);

    void notifyLeaderCardChoice(LeaderCard leaderCard);

    Game getGameModel();

    Player getPlayer();

    void endTurn();
}