package it.polimi.ingsw.client;

import it.polimi.ingsw.model.ClientUpdatePacket;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;

import java.util.List;

/**
 * This interface manages the client actions
 * This class is the client controller.
 */
public interface ClientInterface {

    void setGameModel(Game game);

    void choosePersonalBoardTile(List<PersonalBoardTile> personalBoardTileList);

    void chooseLeaderCards(List<LeaderCard> leaderCards);

    void notifyTurnStarted(String username, long seconds);

    void notifyModelUpdate(ClientUpdatePacket clientUpdatePacket);
}
