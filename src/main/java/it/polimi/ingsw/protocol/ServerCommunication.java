package it.polimi.ingsw.protocol;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Printer;
import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to provide to the server all needed methods to communicate with clients.
 */
public class ServerCommunication {

    /**
     * Mutex to synchronized output.
     */
    private static final Object OUTPUT_MUTEX = new Object();

    /**
     * SocketClient interface to communicate with Abstract SocketServerPlayer.
     */
    private final ServerCommunicationInterface serverCommunicationInterface;

    /**
     * Input stream.
     */
    private final ObjectInputStream input;

    /**
     * Output stream.
     */
    private final ObjectOutputStream output;

    /**
     * List of all type of request with their method.
     */
    private final HashMap<Object, Handler> requestsTable;

    /**
     * Mutex object.
     */
    private final Object object = new Object();

    /**
     * Class constructor.
     * @param objectInputStream input stream.
     * @param objectOutputStream output stream.
     * @param serverCommunicationInterface callback interface.
     */
    public ServerCommunication(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, ServerCommunicationInterface serverCommunicationInterface){
        this.input = objectInputStream;
        this.output = objectOutputStream;
        this.serverCommunicationInterface = serverCommunicationInterface;
        this.requestsTable = new HashMap<>();
        this.setupRequestsTable();
    }

    @FunctionalInterface
    private interface Handler{

        void handle();
    }
    /**
     * Put in the hash map all possible requests from the client and their associated method.
     */
    private void setupRequestsTable(){
        requestsTable.put(SharedCostants.LOGIN_REQUEST, this::loginPlayer);
        requestsTable.put(SharedCostants.SIGNIN_REQUEST, this::signInPlayer);
        requestsTable.put(SharedCostants.JOIN_ROOM_REQUEST, this::joinRoom);
        requestsTable.put(SharedCostants.CREATE_ROOM_REQUEST, this::createNewRoom);
        requestsTable.put(SharedCostants.PERSONAL_TILES, this::notifyPlayerPersonalBoardTileChoice);
        requestsTable.put(SharedCostants.LEADER_CARDS, this::notifyLeaderCardChoice);
        requestsTable.put(SharedCostants.FAMILIAR_IN_TOWER, this::setFamilyMemberInTower);
        requestsTable.put(SharedCostants.FAMILIAR_IN_COUNCIL, this::setFamilyMemberInCouncil);
        requestsTable.put(SharedCostants.FAMILIAR_IN_MARKET, this::setFamilyMemberInMarket);
        requestsTable.put(SharedCostants.FAMILIAR_IN_HARVEST_SIMPLE, this::setFamilyMemberInHarvestSimple);
        requestsTable.put(SharedCostants.FAMILIAR_IN_HARVEST_EXTENDED, this::setFamilyMemberInHarvestExtended);
        requestsTable.put(SharedCostants.FAMILIAR_IN_PRODUCTION_SIMPLE, this::setFamilyMemberInProductionSimple);
        requestsTable.put(SharedCostants.FAMILIAR_IN_PRODUCTION_EXTENDED, this::setFamilyMemberInProductionExtended);
        requestsTable.put(SharedCostants.ACTIVATE_LEADER_CARD, this::activateLeader);
        requestsTable.put(SharedCostants.DISCARD_LEADER_CARD, this::discardLeader);
        requestsTable.put(SharedCostants.SUPPORT_FOR_THE_CHURCH_CHOICE, this::notifySupportForTheChurch);
        requestsTable.put(SharedCostants.END_TURN, this::endTurn);
    }

    /**
     * Client requests handler.
     * @param object of the request.
     */
    public void clientRequestHandler(Object object){
        Handler handler = requestsTable.get(object);
        synchronized (OUTPUT_MUTEX) {
            if (handler != null) {
                handler.handle();
            }
        }
    }

    /**
     * Sign in the player. Read username and password from the input stream and
     * call server sign in method. If errors occur a LoginException is thrown with
     * an error attribute that describe the type of problem.
     */
    private void signInPlayer(){
        int response;
        try{
            String username = (String)input.readObject();
            String password = (String)input.readObject();
            response = handleSignIn(username, password);
            output.writeObject(response);
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while handling sign in player request.");
        }
    }

    private int handleSignIn(String username, String password){
        int response;
        try{
            serverCommunicationInterface.signInPlayer(username, password);
            response = SharedCostants.USER_LOGIN_SIGNIN_OK;
        }catch(LoginException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while signing in player request.");
            if(e.getError().equals(LoginErrorType.USER_ALREADY_EXISTS))
                response = SharedCostants.USER_ALREADY_EXISTS;
            else
                response = SharedCostants.USER_FAIL_GENERIC;
        }
        return response;
    }

    /**
     * Login the player. Read username and password from the input stream and
     * call server login method. If errors occur a LoginException is thrown with
     * an error attribute that describe the type of problem.
     */
    private void loginPlayer(){
        int response;
        try{
            String username = (String)input.readObject();
            String password = (String)input.readObject();
            response = handleLogin(username, password);
            output.writeObject(response);
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(),"Error while handling loginPlayer request.");
        }
    }

    private int handleLogin(String username, String password){
        int response;
        try{
            serverCommunicationInterface.loginPlayer(username, password);
            response = SharedCostants.USER_LOGIN_SIGNIN_OK;
        }catch(LoginException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while loginPlayer in the user: " + e.getError());
            if(e.getError().equals(LoginErrorType.USER_ALREADY_LOGGEDIN))
                response = SharedCostants.USER_ALREADY_LOGGEDIN;
            else if(e.getError().equals(LoginErrorType.USER_WRONG_PASSWORD))
                response = SharedCostants.USER_LOGIN_WRONG_PASSWORD;
            else if(e.getError().equals(LoginErrorType.USER_NOT_EXISTS))
                response = SharedCostants.USER_NOT_EXISTS;
            else
                response = SharedCostants.USER_FAIL_GENERIC;
        }
        return response;
    }

    /**
     * Try to join the last room on the server.
     * If fails send a bad response.
     */
    public void joinRoom(){
        try {
            output.writeObject(handleJoinRoom());
            output.flush();
        } catch (IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while joining room.");
        }
    }

    private int handleJoinRoom(){
        int response;
        try {
            serverCommunicationInterface.joinRoom();
            response = SharedCostants.ROOM_JOINED;
        } catch (RoomException e) {
            response = SharedCostants.NO_ROOM_AVAILABLE;
        }
        return response;
    }

    /**
     * Create a new room calling server method.
     * Response is a Configuration object.
     */
    private void createNewRoom(){
        int maxPlayersNumber;
        try{
            maxPlayersNumber = (int)input.readObject();
            serverCommunicationInterface.createNewRoom(maxPlayersNumber);
        } catch (ClassNotFoundException | ClassCastException | RoomException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error in creation room proceedings.");
        }
    }

    /**
     * Send to client the game bundle.
     * @param game to send.
     * @throws NetworkException if errors occur during communication.
     */
    public void sendGameInfo(Game game) throws NetworkException{
        synchronized (object){
            try{
                output.reset();
                output.writeObject(SharedCostants.GAME_MODEL);
                output.writeObject(game);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public void sendPersonalBoardTile(ArrayList<PersonalBoardTile> personalBoardTileList) throws NetworkException{
        synchronized (object){
            try{
                output.reset();
                output.writeObject(SharedCostants.PERSONAL_TILES);
                output.writeObject(personalBoardTileList);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    private void notifyPlayerPersonalBoardTileChoice(){
        try {
            PersonalBoardTile personalBoardTile = (PersonalBoardTile)input.readObject();
            serverCommunicationInterface.notifyPlayerPersonalBoardTileChoice(personalBoardTile);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error in sending player personal board.");
        }
    }

    @SuppressWarnings("Duplicates")
    public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException{
        synchronized (object){
            try {
                output.reset();
                output.writeObject(SharedCostants.LEADER_CARDS);
                output.writeObject(leaderCards);
                output.flush();
            } catch (IOException e) {
                throw new NetworkException();
            }
        }
    }

    public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException{
        synchronized (object){
            try{
                output.reset();
                output.writeObject(SharedCostants.MODEL_UPDATE);
                output.writeObject(clientUpdatePacket);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    public void notifyLeaderCardChoice(){
        try{
            LeaderCard leaderCard = (LeaderCard)input.readObject();
            serverCommunicationInterface.notifyPlayerLeaderCardChoice(leaderCard);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while obtaining leader card choice.");
        }
    }

    public void notifyTurnStarted(String username, long seconds) throws NetworkException{
        synchronized (object){
            try{
                output.reset();
                output.writeObject(SharedCostants.TURN_STARTED);
                output.writeObject(username);
                output.writeObject(seconds);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    public void supportForTheChurch(boolean flag) throws NetworkException{
        synchronized (object){
            try{
                output.reset();
                output.writeObject(SharedCostants.SUPPORT_FOR_THE_CHURCH);
                output.writeObject(flag);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInTower(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            int towerIndex = (int)input.readObject();
            int cellIndex = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.setFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while setting tower as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInCouncil(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.setFamilyMemberInCouncil(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while setting council palace as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInMarket(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            int marketIndex = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.setFamilyMemberInMarket(familyMemberColor, servants, marketIndex, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while setting market palace as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInHarvestSimple(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.setFamilyMemberInHarvestSimple(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while setting harvest simple area as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInHarvestExtended(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.setFamilyMemberInHarvestExtended(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while setting harvest extended area as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInProductionSimple(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.setFamilyMemberInProductionSimple(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while setting production simple area as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInProductionExtended(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.setFamilyMemberInProductionExtended(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while setting production extended as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void activateLeader(){
        try{
            int leaderCardIndex = (int)input.readObject();
            int servants = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.activateLeaderCard(leaderCardIndex, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while activating the leader.");
        }
    }

    @SuppressWarnings("unchecked")
    private void discardLeader(){
        try{
            int leaderCardIndex = (int)input.readObject();
            HashMap<String, Object> choices = (HashMap<String, Object>)input.readObject();
            serverCommunicationInterface.discardLeader(leaderCardIndex, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while discarding the leader.");
        }
    }

    private void notifySupportForTheChurch(){
        try{
            boolean choice = (boolean)input.readObject();
            serverCommunicationInterface.notifySupportForTheChurch(choice);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while notifying your support for the church choice.");
        }
    }

    public void notifyEndGame(ServerPlayer[] ranking){
        try{
            output.reset();
            output.writeObject(SharedCostants.GAME_END);
            output.writeObject(ranking);
            output.flush();
        } catch (IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while communicating final ranking.");
        }
    }

    public void endTurn(){
        serverCommunicationInterface.endTurn();
    }

}