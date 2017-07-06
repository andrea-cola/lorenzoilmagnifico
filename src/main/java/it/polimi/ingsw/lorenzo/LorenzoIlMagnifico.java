package it.polimi.ingsw.lorenzo;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Printer;
import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.rmiclient.RMIClient;
import it.polimi.ingsw.socketclient.SocketClient;
import it.polimi.ingsw.ui.cli.CommandLineInterface;
import it.polimi.ingsw.ui.ConnectionType;
import it.polimi.ingsw.ui.gui.GraphicUserInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the basic game class that runs the main function; it implements the two interfaces UiController and
 * ClientInterface which contains respectively the game related functions and the client actions.
 */
/*package-local*/ class LorenzoIlMagnifico implements UiController, ClientInterface {

    private String username;

    private AbstractUI userInterface;

    private AbstractClient client;

    private Map<String, Object> playerTurnChoices;

    private Game game;

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

    public void start(){
        userInterface.chooseConnectionType();
    }

    @Override
    public String getUsername(){
        return this.username;
    }

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

    @Override
    public void setPlayerTurnChoices(String operation, Object choice) {
        this.playerTurnChoices.put(operation, choice);
    }

    @Override
    public Map<String, Object> getPlayerTurnChoices(){
        return this.playerTurnChoices;
    }

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

    @Override
    public void choosePersonalBoardTile(List<PersonalBoardTile> personalBoardTileList) {
        userInterface.choosePersonalTile(personalBoardTileList);
    }

    @Override
    public void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) {
        try{
            client.notifyPersonalBoardTileChoice(personalBoardTile);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot send request.");
        }
    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {
        userInterface.chooseLeaderCards(leaderCards);
    }

    @Override
    public void notifyLeaderCardChoice(LeaderCard leaderCard){
        try{
            client.notifyLeaderCardChoice(leaderCard);
        } catch(NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot send leader card choice.");
        }
    }

    @Override
    public void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex) {
        try {
            client.notifySetFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move on tower.");
        }
    }

    @Override
    public void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInCouncil(familyMemberColor, servants, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in council palace.");
        }
    }

    @Override
    public void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int cellIndex) {
        try {
            client.notifySetFamilyMemberInMarket(familyMemberColor, servants, cellIndex, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in market.");
        }
    }

    @Override
    public void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInHarvestSimple(familyMemberColor, servants, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in harvest simple.");
        }
    }

    @Override
    public void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInHarvestExtended(familyMemberColor, servants, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in harvest extended.");
        }
    }

    @Override
    public void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInProductionSimple(familyMemberColor, servants, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in production simple.");
        }
    }

    @Override
    public void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants) {
        try {
            client.notifySetFamilyMemberInProductionExtended(familyMemberColor, servants, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your move in production extended.");
        }
    }

    @Override
    public void notifyActivateLeader(int leaderCardIndex, int servants) {
        try {
            client.notifyActivateLeader(leaderCardIndex, servants, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify activation of your leader.");
        }
    }

    @Override
    public void notifyDiscardLeader(int leaderCardIndex) {
        try {
            client.notifyDiscardLeader(leaderCardIndex, playerTurnChoices);
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify discard of your leader.");
        }
    }

    @Override
    public void notifyExcommunicationChoice(boolean choice) {
        try {
            client.notifySupportForTheChurch(choice);
        } catch (NetworkException e) {
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify your excommunication choice.");
        }
    }

    @Override
    public void setGameModel(Game game) {
        this.game = game;
        userInterface.notifyGameStarted();
    }

    @Override
    public Game getGameModel(){
        return this.game;
    }

    @Override
    public Player getPlayer() {
        return this.game.getPlayer(username);
    }

    @Override
    public void notifyTurnStarted(String username, long seconds) {
        userInterface.turnScreen(username, seconds);
    }

    @Override
    public void notifyModelUpdate(ClientUpdatePacket clientUpdatePacket) {
        this.game = clientUpdatePacket.getGame();
    }

    @Override
    public void supportForTheChurch(boolean flag) {
        userInterface.supportForTheChurch(flag);
    }

    @Override
    public void notifyEndGame(ServerPlayer[] players) {
        userInterface.notifyEndGame(players);
    }

    @Override
    public void endTurn() {
        try {
            client.endTurn();
        } catch (NetworkException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot end turn send request.");
        }
    }

}