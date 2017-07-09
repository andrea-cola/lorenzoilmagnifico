package it.polimi.ingsw.client;

import it.polimi.ingsw.model.ClientUpdatePacket;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;

import java.util.List;

/**
 * This interface manages the client actions
 * This class is the client controller.
 */
public interface ClientInterface {

    /**
     * Method to set the game model
     * @param game
     */
    void setGameModel(Game game);

    /**
     * Method to choose the personal board tile
     * @param personalBoardTileList
     */
    void choosePersonalBoardTile(List<PersonalBoardTile> personalBoardTileList);

    /**
     * Method to choose the leader cards
     * @param leaderCards
     */
    void chooseLeaderCards(List<LeaderCard> leaderCards);

    /**
     * Method to notify the turn started
     * @param username
     * @param seconds
     */
    void notifyTurnStarted(String username, long seconds);

    /**
     * Method to notify the model update
     * @param clientUpdatePacket
     */
    void notifyModelUpdate(ClientUpdatePacket clientUpdatePacket);

    /**
     * Method to manage the support for the church
     * @param flag
     */
    void supportForTheChurch(boolean flag);

    /**
     * Method to notifiy the end the game
     * @param players
     */
    void notifyEndGame(ServerPlayer[] players);
}
