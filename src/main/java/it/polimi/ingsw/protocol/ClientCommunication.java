package it.polimi.ingsw.protocol;

import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to define the communication socket protocol. Used by the client to interact
 * with {@link ServerCommunication}.
 */
public class ClientCommunication {

    /**
     * Input stream object.
     */
    private ObjectInputStream objectInputStream;

    /**
     * Output stream object.
     */
    private ObjectOutputStream objectOutputStream;

    /**
     * Client interface containing communication methods.
     */
    private ClientInterface clientInterface;

    /**
     * Hash map containing all responses codes from the server.
     */
    private HashMap<Object, ResponseHandler> responseTable;

    /**
     * Class constructor.
     * @param objectInputStream input stream.
     * @param objectOutputStream output stream.
     * @param clientInterface callback interface.
     */
    public ClientCommunication(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, ClientInterface clientInterface) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.clientInterface = clientInterface;
        responseTable = new HashMap<>();
        setupResponsesTable();
    }

    /**
     * Put in the hash map all possible responses and associate method handler.
     */
    private void setupResponsesTable() {
        responseTable.put(SharedCostants.GAME_MODEL, this::receiveGameInfo);
        responseTable.put(SharedCostants.PERSONAL_TILES, this::getPersonalTile);
        responseTable.put(SharedCostants.LEADER_CARDS, this::getLeaderCards);
        responseTable.put(SharedCostants.TURN_STARTED, this::notifyTurnStarted);
        responseTable.put(SharedCostants.MODEL_UPDATE, this::notifyModelUpdate);
        responseTable.put(SharedCostants.SUPPORT_FOR_THE_CHURCH, this::supportForTheChurch);
        responseTable.put(SharedCostants.GAME_END, this::notifyGameEnd);
    }

    /**
     * Method to sign in the player to server. This method is blocking.
     * @param username to sign in.
     * @param password to sign in.
     * @throws NetworkException if communication or sign in errors occur.
     */
    public void playerSignIn(String username, String password) throws NetworkException {
        int response;
        try{
            objectOutputStream.writeObject(SharedCostants.SIGNIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            response = (int)objectInputStream.readObject();
        }catch(IOException | ClassNotFoundException e){
            throw new NetworkException();
        }
        if(response == SharedCostants.USER_FAIL_GENERIC)
            throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
        if(response == SharedCostants.USER_ALREADY_EXISTS)
            throw new LoginException(LoginErrorType.USER_ALREADY_EXISTS);
    }

    /**
     * Method to login the player to server. This method is blocking.
     * @param username to login.
     * @param password to login.
     * @throws NetworkException if communication or login errors occur.
     */
    public void playerLogin(String username, String password) throws NetworkException {
        int response;
        try{
            objectOutputStream.writeObject(SharedCostants.LOGIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            response = (int)objectInputStream.readObject();
        }catch(IOException | ClassNotFoundException e){
            throw new NetworkException(e);
        }
        if(response == SharedCostants.USER_ALREADY_LOGGEDIN)
            throw new LoginException(LoginErrorType.USER_ALREADY_LOGGEDIN);
        if(response == SharedCostants.USER_NOT_EXISTS)
            throw new LoginException(LoginErrorType.USER_NOT_EXISTS);
        if(response == SharedCostants.USER_LOGIN_WRONG_PASSWORD || response == SharedCostants.USER_FAIL_GENERIC)
            throw new LoginException(LoginErrorType.USER_WRONG_PASSWORD);
    }

    /**
     * Manage the request to the server or join user in the first game room
     * @throws RoomException if there are no game room available
     * @throws NetworkException if the server doesn't work or something happened
     */
    public void playerJoinRoom() throws NetworkException, RoomException {
        int responseCode;
        try {
            objectOutputStream.writeObject(SharedCostants.JOIN_ROOM_REQUEST);
            objectOutputStream.flush();
            responseCode = (int) objectInputStream.readObject();
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            throw new NetworkException(e);
        }
        if (responseCode == SharedCostants.NO_ROOM_AVAILABLE)
            throw new RoomException();
    }

    /**
     * Requires the server to create a new room.
     * @param maxPlayersNumber allowed in the room.
     * @return configuration bundle.
     * @throws NetworkException if errors occur during room creation or network communication.
     */
    public void createNewRoom(int maxPlayersNumber) throws NetworkException{
        try{
            objectOutputStream.writeObject(SharedCostants.CREATE_ROOM_REQUEST);
            objectOutputStream.writeObject(maxPlayersNumber);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new NetworkException(e);
        }
    }

    /**
     * This method handles receive game info request
     */
    private void receiveGameInfo() {
        try {
            Game game = (Game)objectInputStream.readObject();
            clientInterface.setGameModel(game);
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot handle receive game info request.");
        }
    }

    /**
     * This method handles support for the church request
     */
    private void supportForTheChurch(){
        try{
            boolean flag = (boolean)objectInputStream.readObject();
            clientInterface.supportForTheChurch(flag);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot handle support for the church request.");
        }
    }


    /**
     * This method handles personal board tile request from server
     */
    @SuppressWarnings("unchecked")
    private void getPersonalTile() {
        try {
            List<PersonalBoardTile> personalBoardTileList = (List<PersonalBoardTile>)objectInputStream.readObject();
            clientInterface.choosePersonalBoardTile(personalBoardTileList);
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot handle personal board tile request from server.");
        }
    }

    /**
     * This method handles game end notification
     */
    private void notifyGameEnd(){
        try{
            ServerPlayer[] players = (ServerPlayer[])objectInputStream.readObject();
            clientInterface.notifyEndGame(players);
        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot handle game end notification");
        }
    }

    /**
     * This method notifies the personal board tile choice to the server
     * @param personalBoardTile
     * @throws NetworkException
     */
    @SuppressWarnings("Duplicates")
    public void notifyPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) throws NetworkException{
        try{
            objectOutputStream.writeObject(SharedCostants.PERSONAL_TILES);
            objectOutputStream.writeObject(personalBoardTile);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new NetworkException();
        }
    }

    /**
     * This method handles leader card request from server
     */
    @SuppressWarnings("unchecked")
    private void getLeaderCards(){
        try{
            List<LeaderCard> leaderCards = (List<LeaderCard>)objectInputStream.readObject();
            clientInterface.chooseLeaderCards(leaderCards);
        } catch (ClassCastException | ClassNotFoundException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot handle leader card request from server.");
        }
    }

    /**
     * This method notifies the leader card choice to the server
     * @param leaderCard
     * @throws NetworkException
     */
    @SuppressWarnings("Duplicates")
    public void notifyLeaderCardChoice(LeaderCard leaderCard) throws NetworkException{
        try{
            objectOutputStream.writeObject(SharedCostants.LEADER_CARDS);
            objectOutputStream.writeObject(leaderCard);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new NetworkException();
        }
    }

    /**
     * This method notifies that the turn started
     */
    public void notifyTurnStarted(){
        try{
            String username = (String)objectInputStream.readObject();
            long seconds = (long)objectInputStream.readObject();
            clientInterface.notifyTurnStarted(username, seconds);
        } catch (ClassCastException | ClassNotFoundException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot handle notify turn started.");
        }
    }

    /**
     * This method updates the model
     */
    public void notifyModelUpdate(){
        try{
            ClientUpdatePacket clientUpdatePacket = (ClientUpdatePacket)objectInputStream.readObject();
            clientInterface.notifyModelUpdate(clientUpdatePacket);
        } catch (ClassCastException | ClassNotFoundException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot handle new model update request.");
        }
    }

    /**
     * Method to update the objectOutputStream for a family member inside the tower
     * @param familyMemberColor
     * @param servants
     * @param towerIndex
     * @param cellIndex
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex,
                                             int cellIndex, HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.FAMILIAR_IN_TOWER);
            objectOutputStream.writeObject(familyMemberColor);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(towerIndex);
            objectOutputStream.writeObject(cellIndex);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a family member inside the council
     * @param familyMemberColor
     * @param servants
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants,
                                               HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.FAMILIAR_IN_COUNCIL);
            objectOutputStream.writeObject(familyMemberColor);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a family member inside the market
     * @param familyMemberColor
     * @param servants
     * @param marketIndex
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex,
                                              HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.FAMILIAR_IN_MARKET);
            objectOutputStream.writeObject(familyMemberColor);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(marketIndex);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a family member inside the harvest simple
     * @param familyMemberColor
     * @param servants
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants,
                                                     HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.FAMILIAR_IN_HARVEST_SIMPLE);
            objectOutputStream.writeObject(familyMemberColor);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a family member inside the production simple space
     * @param familyMemberColor
     * @param servants
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants,
                                                        HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.FAMILIAR_IN_PRODUCTION_SIMPLE);
            objectOutputStream.writeObject(familyMemberColor);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a family member inside the harvest extended space
     * @param familyMemberColor
     * @param servants
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants,
                                                       HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.FAMILIAR_IN_HARVEST_EXTENDED);
            objectOutputStream.writeObject(familyMemberColor);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a family member inside the production extended space
     * @param familyMemberColor
     * @param servants
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants,
                                                          HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.FAMILIAR_IN_PRODUCTION_EXTENDED);
            objectOutputStream.writeObject(familyMemberColor);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a leader card when it is activated
     * @param leaderCardIndex
     * @param servants
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void activateLeader(int leaderCardIndex, int servants, HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.ACTIVATE_LEADER_CARD);
            objectOutputStream.writeObject(leaderCardIndex);
            objectOutputStream.writeObject(servants);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for a leader card when it is discarded
     * @param leaderCardIndex
     * @param playerTurnChoices
     * @throws NetworkException
     */
    public void discardLeader(int leaderCardIndex, HashMap<String, Object> playerTurnChoices) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.DISCARD_LEADER_CARD);
            objectOutputStream.writeObject(leaderCardIndex);
            objectOutputStream.writeObject(playerTurnChoices);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to update the objectOutputStream for the support for the church
     * @param flag
     * @throws NetworkException
     */
    public void notifySupportForTheChurch(boolean flag) throws NetworkException{
        try{
            objectOutputStream.reset();
            objectOutputStream.writeObject(SharedCostants.SUPPORT_FOR_THE_CHURCH_CHOICE);
            objectOutputStream.writeObject(flag);
            objectOutputStream.flush();
        } catch (IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to notify the end of the turn
     */
    public void endTurn() {
        try{
            objectOutputStream.writeObject(SharedCostants.END_TURN);
            objectOutputStream.flush();
        } catch (IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Cannot notify end turn.");
        }
    }

    /**
     * Handle server response and run the associated method.
     * @param object of the response.
     */
    public void handleResponse(Object object) {
        ResponseHandler handler = responseTable.get(object);
        if (handler != null) {
            handler.handle();
        }
    }

    @FunctionalInterface
    private interface ResponseHandler {
        void handle();
    }
}