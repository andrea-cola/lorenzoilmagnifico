package it.polimi.ingsw.socketCommunicationProtocol;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Configuration;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Interface used as callback from clients.
 */
public interface ServerCommunicationProtocolInterface {

    /**
     * Method to handle user loginPlayer request.
     * @param username provided by the client.
     * @param password provided by the client.
     * @throws LoginException if loginPlayer error occurs.
     */
    void loginPlayer(String username, String password) throws LoginException;

    /**
     * Method to handle user sign in request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if signInPlayer error occurs.
     */
    void signInPlayer(String username, String password) throws LoginException;

    /**
     * Try to join a room.
     * @throws RoomException if errors occur during the access.
     */
    void joinRoom() throws RoomException;

    /**
     * Create a new room and return a configuration bundle.
     * @param maxPlayersNumber allowed in the room.
     * @return configuration bundle.
     */
    void createNewRoom(int maxPlayersNumber) throws RoomException;

    void notifyPlayerPersonalBoardTileChoice(PersonalBoardTile personalBoardTile);

    void notifyPlayerLeaderCardChoice(LeaderCard leaderCard);

    void setFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, Map<String, Object> playerChoices);

    void setFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices);

    void setFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex, Map<String, Object> playerChoices);

    void setFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices);

    void setFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices);

    void setFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices);

    void setFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices);

    void activateLeaderCard(int leaderCardIndex, Map<String, Object> playerChoices);

    void discardLeader(int leaderCardIndex, Map<String, Object> playerChoices);

    void endTurn();
}