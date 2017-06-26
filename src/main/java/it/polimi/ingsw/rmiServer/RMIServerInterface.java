package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.rmiClient.RMIClientInterface;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * RMI server interface used for remote method invocation from client to server.
 */
public interface RMIServerInterface extends Remote{

    /**
     * Player login method.
     * @param username provided by the client to login.
     * @param password provided by the client to login.
     * @param rmiPlayer is trying to login.
     * @return a unique identifier of the player.
     * @throws IOException if errors occur during login proceedings.
     */
    String loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException;

    /**
     * Player sign in method.
     * @param username provided by the client to sign in.
     * @param password provided by the client to sign in.
     * @throws IOException
     */
    void signInPlayer(String username, String password) throws IOException;

    /**
     * Remote method to join the player to the first game room
     * @param username which is making the request
     * @throws RoomException if the server is not reachable
     */
    void joinFirstRoom(String username) throws RoomException, IOException;

    /**
     * Create a new room.
     * @param id to get the player from the cache.
     * @param maxPlayersNumber allowed in the room.
     * @return the configuration number.
     */
    void createNewRoom(String id, int maxPlayersNumber) throws RoomException, IOException;

    void notifyPersonalBoardChoice(String playerID, PersonalBoardTile personalBoardTile) throws RemoteException;

    void notifyLeaderCardChoice(String playerID, LeaderCard leaderCard) throws RemoteException;

    void setFamilyMemberInTower(String playerID, FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, Map<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInCouncil(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInMarket(String playerID, FamilyMemberColor familyMemberColor, int servants, int marketIndex, Map<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInHarvestSimple(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInHarvestExtended(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInProductionSimple(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInProductionExtended(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException;

    void endTurn(String playerID) throws RemoteException;
}