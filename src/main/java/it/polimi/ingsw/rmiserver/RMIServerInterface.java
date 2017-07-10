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


    /**
     * Notifies from the client to the server the personal board tile chosen
     * @param username the player that performed the chose
     * @param personalBoardTile the personal board tile chosen
     * @throws RemoteException if error occurs during network communication
     */
    void notifyPersonalBoardChoice(String username, PersonalBoardTile personalBoardTile) throws RemoteException;

    /**
     * Notifies from the client to the server the leader card chosen
     * @param username the player that performed the chose
     * @param leaderCard the leader card chosen
     * @throws RemoteException if error occurs during network communication
     */
    void notifyLeaderCardChoice(String username, LeaderCard leaderCard) throws RemoteException;

    /**
     * Updates on the server the family member placed inside the tower
     * @param username the player that performed the action
     * @param familyMemberColor the color of the family member placed inside the tower
     * @param servants the number of servants used to perform the action
     * @param towerIndex the index of the tower
     * @param cellIndex the index of the cell
     * @throws RemoteException if error occurs during network communication
     */
    void setFamilyMemberInTower(String username, FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the family member placed inside the council
     * @param username the player that performed the action
     * @param familyMemberColor the color of the family member placed inside the council
     * @param servants the number of servants used to perform the action
     * @throws RemoteException if error occurs during network communication
     */
    void setFamilyMemberInCouncil(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the family member placed inside the market
     * @param username the player that performed the action
     * @param familyMemberColor the color of the family member placed inside the market
     * @param servants the number of servants used to perform the action
     * @param marketIndex the index of the cell of the market
     * @throws RemoteException if error occurs during network communication
     */
    void setFamilyMemberInMarket(String username, FamilyMemberColor familyMemberColor, int servants, int marketIndex, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the family member placed inside the harvest simple
     * @param username the player that performed the action
     * @param familyMemberColor the color of the family member placed inside the harvest simple
     * @param servants the number of servants used to perform the action
     * @throws RemoteException if error occurs during network communication
     */
    void setFamilyMemberInHarvestSimple(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the family member placed inside the harvest extended
     * @param username the player that performed the action
     * @param familyMemberColor the color of the family member placed inside the harvest extended
     * @param servants the number of servants used to perform the action
     * @throws RemoteException if error occurs during network communication
     */
    void setFamilyMemberInHarvestExtended(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the family member placed inside the production simple
     * @param username the player that performed the action
     * @param familyMemberColor the color of the family member placed inside the production simple
     * @param servants the number of servants used to perform the action
     * @throws RemoteException if error occurs during network communication
     */
    void setFamilyMemberInProductionSimple(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the family member placed inside the production extended
     * @param username the player that performed the action
     * @param familyMemberColor the color of the family member placed inside the production simple
     * @param servants the number of servants used to perform the action
     * @throws RemoteException if error occurs during network communication
     */
    void setFamilyMemberInProductionExtended(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the leader card activated
     * @param username the player that performed the action
     * @param leaderCardIndex the index of the leader card
     * @param servants the number of servants used to perform the action
     * @throws RemoteException if error occurs during network communication
     */
    void activateLeaderCard(String username, int leaderCardIndex, int servants, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the leader card discarded
     * @param username the player that performed the action
     * @param leaderCardIndex the index of the leader card
     * @throws RemoteException if error occurs during network communication
     */
    void discardLeader(String username, int leaderCardIndex, HashMap<String, Object> playerChoices) throws RemoteException;

    /**
     * Updates on the server the support for the church
     * @param username the player that performed the action
     * @param choice the choice
     * @throws RemoteException if error occurs during network communication
     */
    void notifySupportForTheChurch(String username, boolean choice) throws RemoteException;

    /**
     * Notifies to the server that the turn is ended
     * @param username the player that performed the action
     * @throws RemoteException if error occurs during network communication
     */
    void endTurn(String username) throws RemoteException;
}