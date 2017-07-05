package it.polimi.ingsw.socketprotocol;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to provide to the server all needed methods to communicate with clients.
 */
public class ServerCommunicationProtocol {

    /**
     * Mutex to synchronized output.
     */
    private static final Object OUTPUT_MUTEX = new Object();

    /**
     * SocketClient interface to communicate with Abstract SocketPlayer.
     */
    private final ServerCommunicationProtocolInterface serverCommunicationProtocolInterface;

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
     * @param serverCommunicationProtocolInterface callback interface.
     */
    public ServerCommunicationProtocol(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, ServerCommunicationProtocolInterface serverCommunicationProtocolInterface){
        this.input = objectInputStream;
        this.output = objectOutputStream;
        this.serverCommunicationProtocolInterface = serverCommunicationProtocolInterface;
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
        requestsTable.put(CommunicationProtocolConstants.LOGIN_REQUEST, this::loginPlayer);
        requestsTable.put(CommunicationProtocolConstants.SIGNIN_REQUEST, this::signInPlayer);
        requestsTable.put(CommunicationProtocolConstants.JOIN_ROOM_REQUEST, this::joinRoom);
        requestsTable.put(CommunicationProtocolConstants.CREATE_ROOM_REQUEST, this::createNewRoom);
        requestsTable.put(CommunicationProtocolConstants.PERSONAL_TILES, this::notifyPlayerPersonalBoardTileChoice);
        requestsTable.put(CommunicationProtocolConstants.LEADER_CARDS, this::notifyLeaderCardChoice);
        requestsTable.put(CommunicationProtocolConstants.FAMILIAR_IN_TOWER, this::setFamilyMemberInTower);
        requestsTable.put(CommunicationProtocolConstants.FAMILIAR_IN_COUNCIL, this::setFamilyMemberInCouncil);
        requestsTable.put(CommunicationProtocolConstants.FAMILIAR_IN_MARKET, this::setFamilyMemberInMarket);
        requestsTable.put(CommunicationProtocolConstants.FAMILIAR_IN_HARVEST_SIMPLE, this::setFamilyMemberInHarvestSimple);
        requestsTable.put(CommunicationProtocolConstants.FAMILIAR_IN_HARVEST_EXTENDED, this::setFamilyMemberInHarvestExtended);
        requestsTable.put(CommunicationProtocolConstants.FAMILIAR_IN_PRODUCTION_SIMPLE, this::setFamilyMemberInProductionSimple);
        requestsTable.put(CommunicationProtocolConstants.FAMILIAR_IN_PRODUCTION_EXTENDED, this::setFamilyMemberInProductionExtended);
        requestsTable.put(CommunicationProtocolConstants.ACTIVATE_LEADER_CARD, this::activateLeader);
        requestsTable.put(CommunicationProtocolConstants.DISCARD_LEADER_CARD, this::discardLeader);
        requestsTable.put(CommunicationProtocolConstants.SUPPORT_FOR_THE_CHURCH_CHOICE, this::notifySupportForTheChurch);
        requestsTable.put(CommunicationProtocolConstants.END_TURN, this::endTurn);
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
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while handling sign in player request.");
        }
    }

    private int handleSignIn(String username, String password){
        int response;
        try{
            serverCommunicationProtocolInterface.signInPlayer(username, password);
            response = CommunicationProtocolConstants.USER_LOGIN_SIGNIN_OK;
        }catch(LoginException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while signing in player request.");
            if(e.getError().equals(LoginErrorType.USER_ALREADY_EXISTS))
                response = CommunicationProtocolConstants.USER_ALREADY_EXISTS;
            else
                response = CommunicationProtocolConstants.USER_FAIL_GENERIC;
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
            Debugger.printDebugMessage(this.getClass().getSimpleName(),"Error while handling loginPlayer request.");
        }
    }

    private int handleLogin(String username, String password){
        int response;
        try{
            serverCommunicationProtocolInterface.loginPlayer(username, password);
            response = CommunicationProtocolConstants.USER_LOGIN_SIGNIN_OK;
        }catch(LoginException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while loginPlayer in the user: " + e.getError());
            if(e.getError().equals(LoginErrorType.USER_ALREADY_LOGGEDIN))
                response = CommunicationProtocolConstants.USER_ALREADY_LOGGEDIN;
            else if(e.getError().equals(LoginErrorType.USER_WRONG_PASSWORD))
                response = CommunicationProtocolConstants.USER_LOGIN_WRONG_PASSWORD;
            else if(e.getError().equals(LoginErrorType.USER_NOT_EXISTS))
                response = CommunicationProtocolConstants.USER_NOT_EXISTS;
            else
                response = CommunicationProtocolConstants.USER_FAIL_GENERIC;
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
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while joining room.");
        }
    }

    private int handleJoinRoom(){
        int response;
        try {
            serverCommunicationProtocolInterface.joinRoom();
            response = CommunicationProtocolConstants.ROOM_JOINED;
        } catch (RoomException e) {
            response = CommunicationProtocolConstants.NO_ROOM_AVAILABLE;
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
            serverCommunicationProtocolInterface.createNewRoom(maxPlayersNumber);
        } catch (ClassNotFoundException | ClassCastException | RoomException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error in creation room proceedings.");
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
                output.writeObject(CommunicationProtocolConstants.GAME_MODEL);
                output.writeObject(game);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public void sendPersonalBoardTile(List<PersonalBoardTile> personalBoardTileList) throws NetworkException{
        synchronized (object){
            try{
                output.reset();
                output.writeObject(CommunicationProtocolConstants.PERSONAL_TILES);
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
            serverCommunicationProtocolInterface.notifyPlayerPersonalBoardTileChoice(personalBoardTile);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error in sending player personal board.");
        }
    }

    @SuppressWarnings("Duplicates")
    public void sendLeaderCards(List<LeaderCard> leaderCards) throws NetworkException{
        synchronized (object){
            try {
                output.reset();
                output.writeObject(CommunicationProtocolConstants.LEADER_CARDS);
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
                output.writeObject(CommunicationProtocolConstants.MODEL_UPDATE);
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
            serverCommunicationProtocolInterface.notifyPlayerLeaderCardChoice(leaderCard);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while obtaining leader card choice.");
        }
    }

    public void notifyTurnStarted(String username, long seconds) throws NetworkException{
        synchronized (object){
            try{
                output.reset();
                output.writeObject(CommunicationProtocolConstants.TURN_STARTED);
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
                output.writeObject(CommunicationProtocolConstants.SUPPORT_FOR_THE_CHURCH);
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
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.setFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while setting tower as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInCouncil(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.setFamilyMemberInCouncil(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while setting council palace as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInMarket(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            int marketIndex = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.setFamilyMemberInMarket(familyMemberColor, servants, marketIndex, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while setting market palace as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInHarvestSimple(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.setFamilyMemberInHarvestSimple(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while setting harvest simple area as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInHarvestExtended(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.setFamilyMemberInHarvestExtended(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while setting harvest extended area as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInProductionSimple(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.setFamilyMemberInProductionSimple(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while setting production simple area as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void setFamilyMemberInProductionExtended(){
        try{
            FamilyMemberColor familyMemberColor = (FamilyMemberColor) input.readObject();
            int servants = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.setFamilyMemberInProductionExtended(familyMemberColor, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while setting production extended as client.");
        }
    }

    @SuppressWarnings("unchecked")
    private void activateLeader(){
        try{
            int leaderCardIndex = (int)input.readObject();
            int servants = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.activateLeaderCard(leaderCardIndex, servants, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while activating the leader.");
        }
    }

    @SuppressWarnings("unchecked")
    private void discardLeader(){
        try{
            int leaderCardIndex = (int)input.readObject();
            Map<String, Object> choices = (Map<String, Object>)input.readObject();
            serverCommunicationProtocolInterface.discardLeader(leaderCardIndex, choices);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while discarding the leader.");
        }
    }

    private void notifySupportForTheChurch(){
        try{
            boolean choice = (boolean)input.readObject();
            serverCommunicationProtocolInterface.notifySupportForTheChurch(choice);
        } catch (ClassNotFoundException | ClassCastException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while notifying your support for the church choice.");
        }
    }

    public void endTurn(){
        serverCommunicationProtocolInterface.endTurn();
    }

}