package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;

import java.util.HashMap;
import java.util.Map;

/**
 * This class the abstraction of the server.
 * It contains all methods shared between concrete communication classes.
 */
public abstract class AbstractClient {

    /**
     * Client interface.
     */
    private final ClientInterface clientInterface;

    /**
     * Server address.
     */
    private final String address;

    /**
     * Server port.
     */
    private final int port;

    /**
     * Class constructor.
     * @param clientInterface client controller.
     * @param address of the server.
     * @param port of the server.
     */
    public AbstractClient(ClientInterface clientInterface, String address, int port){
        this.clientInterface = clientInterface;
        this.address = address;
        this.port = port;
    }

    /**
     * Method to get the server ip address.
     * @return the server address.
     */
    protected String getAddress(){
        return this.address;
    }

    /**
     * Method to get the server port.
     * @return the server port.
     */
    protected int getPort(){
        return this.port;
    }

    /**
     * Method to get the ClientInterface.
     * @return the client controller.
     */
    protected ClientInterface getClient() {
        return clientInterface;
    }

    /**
     * Abstract method to connect client to a server.
     * @throws ConnectionException if errors occur during connection to the server.
     */
    public abstract void connectToServer() throws ConnectionException;

    /**
     * Abstract method to loginPlayer user on a server.
     * @param username for the login.
     * @param password for the login.
     * @throws NetworkException if errors occur during login.
     */
    public abstract void loginPlayer(String username, String password) throws NetworkException;

    /**
     * Abstract method to sign in a user on a server.
     * @param username for the sign in.
     * @param password for the sign in.
     * @throws NetworkException if errors occur during sign in.
     */
    public abstract void signInPlayer(String username, String password) throws NetworkException;

    /**
     * Abstract method to join the first game room
     * @throws NetworkException
     */
    public abstract void joinRoom() throws NetworkException, RoomException;

    /**
     * Abstract method to create a new room
     * @param maxPlayersNumber the max number of players that can join the room
     * @throws NetworkException if error occurs during network communication
     * @throws RoomException if error occurs during the room creation
     */
    public abstract void createNewRoom(int maxPlayersNumber) throws NetworkException, RoomException;

    /**
     * Abstract method to notify personal board tile choice
     * @param personalBoardTile the personal board tile choosen
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifyPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) throws NetworkException;

    /**
     * Abstract method to notify leader card choice
     * @param leaderCard the leader card
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifyLeaderCardChoice(LeaderCard leaderCard) throws NetworkException;

    /**
     * Abstract method to place family member in a tower
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex,
                                                      int cellIndex, HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to place family member in council
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants,
                                                        HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to place family member in a market
     * @param familyMemberColor the color of the family member to be placed inside the market
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex,
                                                       HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to place family member in harvest simple
     * @param familyMemberColor the color of the family member to be placed inside the harvest simple
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants,
                                                              HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to place family member in production simple
     * @param familyMemberColor the color of the family member to be placed inside the production simple
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants,
                                                                 HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to place family member in harvest extended
     * @param familyMemberColor the color of the family member to be placed inside the hervest extended
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants,
                                                                HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to place family member in production extended
     * @param familyMemberColor the color of the family member to be placed inside the production extended
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants,
                                                                   HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to notify to activate a leader card
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifyActivateLeader(int leaderCardIndex, int servants, HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to notify to discard a leader card
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifyDiscardLeader(int leaderCardIndex, HashMap<String, Object> playerChoices) throws NetworkException;

    /**
     * Abstract method to notify the support to the church
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifySupportForTheChurch(boolean choice) throws NetworkException;

    /**
     * Abstract method for the end of the turn
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void endTurn() throws NetworkException;
}
