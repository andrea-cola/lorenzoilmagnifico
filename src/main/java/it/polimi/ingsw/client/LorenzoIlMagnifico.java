package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.ui.AbstractUserInterface;
import it.polimi.ingsw.ui.UserInterface;
import it.polimi.ingsw.utility.Printer;
import it.polimi.ingsw.rmiclient.RMIClient;
import it.polimi.ingsw.socketclient.SocketClient;
import it.polimi.ingsw.ui.cli.CommandLineInterface;
import it.polimi.ingsw.ui.ConnectionType;
import it.polimi.ingsw.ui.gui.GraphicUserInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the basic game class that runs the main function; it implements the two interfaces UserInterface and
 * ClientInterface which contains respectively the game related functions and the client actions.
 */
/*package-local*/ class LorenzoIlMagnifico implements UserInterface, ClientInterface {

    /**
     * Player's username
     */
    private String username;

    /**
     * User Interface
     */
    private AbstractUserInterface userInterface;

    /**
     * Client
     */
    private AbstractClient client;

    /**
     * Player turn choices
     */
    private HashMap<String, Object> playerTurnChoices;

    /**
     * Game instance to manage the logic of the game
     */
    private Game game;

    private List<String> moveMessages;

    /**
     * Class constructor. A new user interface is created.
     * @param ui index of the preferred interface.
     */
    /*package-local*/ LorenzoIlMagnifico(int ui) throws InterruptedException {
        if(ui == 1)
            userInterface = new CommandLineInterface(this);
        else if(ui == 2)
            userInterface = new GraphicUserInterface(this);
        else
            throw new InterruptedException();
        playerTurnChoices = new HashMap<>();
    }

    /**
     * Method to start the user interface
     */
    public void start(){
        userInterface.chooseConnectionType();
    }

    /**
     * Get the player's username
     * @return
     */
    @Override
    public String getUsername(){
        return this.username;
    }

    /**
     * Get the available moves
     * @return
     */
    public List<String> getMoveMessages(){
        return this.moveMessages;
    }

    /**
     * Method to set the network
     * @param connectionType
     * @param address
     * @param port
     * @throws ConnectionException
     */
    @Override
    public void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException {
        if(connectionType.equals(ConnectionType.SOCKET))
            client = new SocketClient(this, address, port);
        else if(connectionType.equals(ConnectionType.RMI))
            client = new RMIClient(this, address, port);
        else
            throw new ConnectionException();
        client.connectToServer();
        userInterface.loginScreen();
    }

    /**
     * Method to set the player turn
     * @param operation
     * @param choice
     */
    @Override
    public void setPlayerTurnChoices(String operation, Object choice) {
        this.playerTurnChoices.put(operation, choice);
    }

    /**
     * Method to get the player turn
     * @return
     */
    @Override
    public Map<String, Object> getPlayerTurnChoices(){
        return this.playerTurnChoices;
    }

    /**
     * Method to manage the login of the player
     * @param username
     * @param password
     * @param flag
     */
    @Override
    public void loginPlayer(String username, String password, boolean flag) {
        try{
            if(flag)
                client.signInPlayer(username, password);
            client.loginPlayer(username, password);
            this.username = username;
            userInterface.joinRoomScreen();
        }catch (LoginException e) {
            Printer.printDebugMessage(this.getClass().getSimpleName(), e.getError().toString());
            userInterface.loginScreen();
        }catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot send request.");
        }
    }

    /**
     * Method to join the room of the match
     */
    @Override
    public void joinRoom(){
        try{
            client.joinRoom();
        }catch (RoomException e) {
            Printer.printStandardMessage("No rooms available. Create new one.");
            userInterface.createRoomScreen();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot send join room request." + e.getMessage());
        }
    }

    /**
     * Method to create a new room for the match
     * @param maxPlayers
     */
    @Override
    public void createRoom(int maxPlayers) {
        try {
            client.createNewRoom(maxPlayers);
        } catch (RoomException e) {
            Printer.printDebugMessage("You're added in another room created by other player meanwhile.");
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot send create room request.");
        }
    }

    /**
     * Method to choose the personal board tile
     * @param personalBoardTileList
     */
    @Override
    public void choosePersonalBoardTile(List<PersonalBoardTile> personalBoardTileList) {
        userInterface.choosePersonalTile(personalBoardTileList);
    }

    /**
     * Method to send the personal board tile choice
     * @param personalBoardTile
     */
    @Override
    public void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) {
        try{
            client.notifyPersonalBoardTileChoice(personalBoardTile);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot send request.");
        }
    }

    /**
     * Method to choose leader cards
     * @param leaderCards
     */
    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {
        userInterface.chooseLeaderCards(leaderCards);
    }

    /**
     * Method to notify the leader card choice
     * @param leaderCard
     */
    @Override
    public void notifyLeaderCardChoice(LeaderCard leaderCard){
        try{
            client.notifyLeaderCardChoice(leaderCard);
            playerTurnChoices = new HashMap<>();
        } catch(NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot send leader card choice.");
        }
    }

    /**
     * Method to place a family member in a tower
     * @param familyMemberColor
     * @param servants
     * @param towerIndex
     * @param cellIndex
     */
    @Override
    public void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex) {
        try {
            client.notifySetFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move on tower.");
        }
    }

    /**
     * Method to place a family member in the council palace
     * @param familyMemberColor
     * @param servants
     */
    @Override
    public void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInCouncil(familyMemberColor, servants, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in council palace.");
        }
    }

    /**
     * Method to place a family member in the market
     * @param familyMemberColor
     * @param servants
     * @param cellIndex
     */
    @Override
    public void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int cellIndex) {
        try {
            client.notifySetFamilyMemberInMarket(familyMemberColor, servants, cellIndex, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in market.");
        }
    }

    /**
     * Method to place a family member in the harvest simple space
     * @param familyMemberColor
     * @param servants
     */
    @Override
    public void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInHarvestSimple(familyMemberColor, servants, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in harvest simple.");
        }
    }

    /**
     * Method to place a family member in the harvest extended space
     * @param familyMemberColor
     * @param servants
     */
    @Override
    public void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInHarvestExtended(familyMemberColor, servants, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in harvest extended.");
        }
    }

    /**
     * Method to place a family member in the production simple space
     * @param familyMemberColor
     * @param servants
     */
    @Override
    public void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInProductionSimple(familyMemberColor, servants, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in production simple.");
        }
    }

    /**
     * Method to place a family member in the production extended space
     * @param familyMemberColor
     * @param servants
     */
    @Override
    public void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInProductionExtended(familyMemberColor, servants, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in production extended.");
        }
    }

    /**
     * Method to activate a leader card
     * @param leaderCardIndex
     * @param servants
     */
    @Override
    public void notifyActivateLeader(int leaderCardIndex, int servants) {
        try {
            client.notifyActivateLeader(leaderCardIndex, servants, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify activation of your leader.");
        }
    }

    /**
     * Method to discard a leader card
     * @param leaderCardIndex
     */
    @Override
    public void notifyDiscardLeader(int leaderCardIndex) {
        try {
            client.notifyDiscardLeader(leaderCardIndex, playerTurnChoices);
            playerTurnChoices = new HashMap<>();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify discard of your leader.");
        }
    }

    /**
     * Method to manage the excommunication chocie
     * @param choice
     */
    @Override
    public void notifyExcommunicationChoice(boolean choice) {
        try {
            client.notifySupportForTheChurch(choice);
        } catch (NetworkException e) {
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your excommunication choice.");
        }
    }

    /**
     * Method to set the game model
     * @param game
     */
    @Override
    public void setGameModel(Game game) {
        this.game = game;
        userInterface.notifyGameStarted();
    }

    /**
     * Method to get the game model
     * @return
     */
    @Override
    public Game getGameModel(){
        return this.game;
    }

    /**
     * Method to get the player
     * @return
     */
    @Override
    public Player getPlayer() {
        return this.game.getPlayer(username);
    }

    /**
     * Method to notify that the turn has started
     * @param username
     * @param seconds
     */
    @Override
    public void notifyTurnStarted(String username, long seconds) {
        userInterface.turnScreen(username, seconds);
    }

    /**
     * Method to notify changes in the game model
     * @param clientUpdatePacket
     */
    @Override
    public void notifyModelUpdate(ClientUpdatePacket clientUpdatePacket) {
        this.game = clientUpdatePacket.getGame();
        this.moveMessages = clientUpdatePacket.getMessages();
    }

    /**
     * Method to manage the support to the church
     * @param flag
     */
    @Override
    public void supportForTheChurch(boolean flag) {
        userInterface.supportForTheChurch(flag);
    }

    /**
     * Method to notify the end of the game
     * @param players
     */
    @Override
    public void notifyEndGame(ServerPlayer[] players) {
        userInterface.notifyEndGame(players);
    }

    /**
     * Method that notifies the end of the turn
     */
    @Override
    public void endTurn() {
        try {
            client.endTurn();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot end turn send request.");
        }
    }

}