package it.polimi.ingsw.rmiserver;

import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.rmiclient.RMIClientInterface;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
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
     * @throws IOException if errors occur during login proceedings.
     */
    void loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException;

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
     * @param username to get the player from the cache.
     * @param maxPlayersNumber allowed in the room.
     * @return the configuration number.
     */
    void createNewRoom(String username, int maxPlayersNumber) throws RoomException, IOException;

    void notifyPersonalBoardChoice(String username, PersonalBoardTile personalBoardTile) throws RemoteException;

    void notifyLeaderCardChoice(String username, LeaderCard leaderCard) throws RemoteException;

    void setFamilyMemberInTower(String username, FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, HashMap<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInCouncil(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInMarket(String username, FamilyMemberColor familyMemberColor, int servants, int marketIndex, HashMap<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInHarvestSimple(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInHarvestExtended(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInProductionSimple(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    void setFamilyMemberInProductionExtended(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    void activateLeaderCard(String username, int leaderCardIndex, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    void discardLeader(String username, int leaderCardIndex, HashMap<String, Object> playerChoices) throws RemoteException;

    void notifySupportForTheChurch(String username, boolean choice) throws RemoteException;

    void endTurn(String username) throws RemoteException;
}