package it.polimi.ingsw.socketserver;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.Printer;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.protocol.ServerCommunication;
import it.polimi.ingsw.protocol.ServerCommunicationInterface;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class extends Server player for socket communication.
 */
public class SocketServerPlayer extends ServerPlayer implements Runnable, ServerCommunicationInterface {

    /**
     * Remote socket client.
     */
    private final transient Socket socketClient;

    /**
     * Server interface allow to access server methods.
     */
    private final transient ServerInterface serverInterface;

    /**
     * Output stream toward client.
     */
    private final transient ObjectOutputStream objectOutputStream;

    /**
     * Input stream from client.
     */
    private final transient ObjectInputStream objectInputStream;

    /**
     * Server protocol.
     */
    private final transient ServerCommunication socketCommunicationProtocol;

    /**
     * Class constructor that initialize input/output streams and communication server side protocol.
     * @param socketClient obtained from the server accept.
     * @param serverInterface to communicate with the server.
     */
    /* package-local */SocketServerPlayer(Socket socketClient, ServerInterface serverInterface) throws IOException{
        this.socketClient = socketClient;
        this.serverInterface = serverInterface;
        objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
        socketCommunicationProtocol = new ServerCommunication(objectInputStream, objectOutputStream, this);
    }

    /**
     * Listen input stream from client.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run(){
        boolean flag = true;
        try{
            while(flag) {
                Object input = objectInputStream.readObject();
                if(input == null)
                    flag = false;
                socketCommunicationProtocol.clientRequestHandler(input);
            }
        }catch(IOException | ClassNotFoundException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Connection with the client is down.");
            serverInterface.disableUser(this);
        }finally{
            closeConnections(objectInputStream, objectOutputStream, socketClient);
        }
    }

    /**
     * Method to handle user loginPlayer request.
     * @param username provided by the client.
     * @param password provided by the client.
     * @throws LoginException if loginPlayer error occurs.
     */
    @Override
    public void loginPlayer(String username, String password) throws LoginException {
        serverInterface.loginPlayer(this, username, password);
    }

    /**
     * Method to handle user sign in request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if signInPlayer error occurs.
     */
    @Override
    public void signInPlayer(String username, String password) throws LoginException {
        serverInterface.signInPlayer(username, password);
    }

    /**
     * Try to join a room.
     * @throws RoomException if errors occur during the access.
     */
    @Override
    public void joinRoom() throws RoomException {
        serverInterface.joinRoom(this);
    }

    /**
     * Create a new room and return a configuration bundle.
     * @param maxPlayersNumber allowed in the room.
     * @return configuration bundle.
     */
    @Override
    public void createNewRoom(int maxPlayersNumber) throws RoomException{
        serverInterface.createNewRoom(this, maxPlayersNumber);
    }


    /**
     * Communicates to the room the personal board tile chosen
     * @param personalBoardTile the personal board tile chosen
     */
    @Override
    public void notifyPlayerPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) {
        this.getPersonalBoard().setPersonalBoardTile(personalBoardTile);
        this.getRoom().onPersonalTilesChosen();
    }

    /**
     * Communicates to the room the leader card chosen
     * @param leaderCard the leader card selected by the user
     */
    @Override
    public void notifyPlayerLeaderCardChoice(LeaderCard leaderCard) {
        this.getPersonalBoard().setLeaderCard(leaderCard);
        this.getRoom().onLeaderCardChosen();
    }

    /**
     * Communicates to the room the family member placed inside a tower
     * @param familyMemberColor the color of the family member placed
     * @param servants the number of servants used to perform the action
     * @param towerIndex the index of the tower
     * @param cellIndex the index of the cell
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void setFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, HashMap<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInTower(this, familyMemberColor, servants, towerIndex, cellIndex, playerChoices);
    }

    /**
     * Communicates to the room the family member placed inside the council
     * @param familyMemberColor the color of the family member placed
     * @param servants the number of servants used to perform the action
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void setFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInCouncil(this, familyMemberColor, servants, playerChoices);
    }

    /**
     * Communicates to the room the family member placed inside the council
     * @param familyMemberColor the color of the family member placed
     * @param servants the number of servants used to perform the action
     * @param marketIndex the index of the market
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void setFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex, HashMap<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInMarket(this, familyMemberColor, servants, marketIndex, playerChoices);
    }

    /**
     * Communicates to the room the family member placed inside the harvest simple
     * @param familyMemberColor the color of the family member placed
     * @param servants the number of servants used to perform the action
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void setFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInHarvestSimple(this, familyMemberColor, servants, playerChoices);
    }

    /**
     * Communicates to the room the family member placed inside the harvest extended
     * @param familyMemberColor the color of the family member placed
     * @param servants the number of servants used to perform the action
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void setFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInHarvestExtended(this, familyMemberColor, servants, playerChoices);
    }

    /**
     * Communicates to the room the family member placed inside the production simple
     * @param familyMemberColor the color of the family member placed
     * @param servants the number of servants used to perform the action
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void setFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInProductionSimple(this, familyMemberColor, servants, playerChoices);
    }

    /**
     * Communicates to the room the family member placed inside the production extended
     * @param familyMemberColor the color of the family member placed
     * @param servants the number of servants used to perform the action
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void setFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInProductionExtended(this, familyMemberColor, servants, playerChoices);
    }

    /**
     * Communicates to the room the leader card activated by the user
     * @param leaderCardIndex the index of the leader card chosen inside the player's personal deck
     * @param servants the number of servants used to perform the action
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void activateLeaderCard(int leaderCardIndex, int servants, HashMap<String, Object> playerChoices) {
        this.getRoom().activateLeader(this, leaderCardIndex, servants, playerChoices);
    }

    /**
     * Communicates to the room the leader card discarded by the user
     * @param leaderCardIndex the index of the leader card chosen inside the player's personal deck
     * @param playerChoices to communicate to the server the player's choices
     */
    @Override
    public void discardLeader(int leaderCardIndex, HashMap<String, Object> playerChoices) {
        this.getRoom().discardLeader(this, leaderCardIndex, playerChoices);
    }

    /**
     * Communicates to the room if the player has supported the church or not
     * @param flag falg used to check the answer of the player
     */
    @Override
    public void notifySupportForTheChurch(boolean flag) {
        this.getRoom().onSupportToTheChurchChoice(this, flag);
    }

    /**
     * Communicates to the server the game info
     * @param game the game
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendGameInfo(Game game) throws NetworkException {
        socketCommunicationProtocol.sendGameInfo(game);
    }

    /**
     * Communicates to the server the personal board tiles
     * @param personalBoardTiles the personal board tiles
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException {
        socketCommunicationProtocol.sendPersonalBoardTile(personalBoardTiles);
    }

    /**
     * Communicate to the server the leader cards
     * @param leaderCards the leader cards deck
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {
        socketCommunicationProtocol.sendLeaderCards(leaderCards);
    }

    /**
     * Communicate to the server that a new turn started
     * @param username the username of the player that is performing the turn
     * @param seconds the time available for the player to perform the turn
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyTurnStarted(String username, long seconds) throws NetworkException {
        socketCommunicationProtocol.notifyTurnStarted(username, seconds);
    }

    /**
     * Communicate to the server the game model updates
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {
        socketCommunicationProtocol.sendGameModelUpdate(clientUpdatePacket);
    }

    /**
     * Communicate to the server the support for the church
     * @param flag this flag is used to check if the player supports the church or not
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void supportForTheChurch(boolean flag) throws NetworkException {
        socketCommunicationProtocol.supportForTheChurch(flag);
    }

    /**
     * Communicates to the server the end of the game
     * @param ranking the players final ranking
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {
        socketCommunicationProtocol.notifyEndGame(ranking);
    }

    @Override
    public void endTurn() {
        this.getRoom().endTurn(this);
    }

    /**
     * Close input/output streams and socket.
     * @param objectInputStream input stream.
     * @param objectOutputStream output stream.
     * @param socketClient client socket.
     */
    private void closeConnections(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socketClient){
        closeConnection(objectInputStream);
        closeConnection(objectOutputStream);
        closeConnection(socketClient);
    }

    /**
     * Method to close the closeable passed as argument.
     * @param connection to close.
     */
    private void closeConnection(Closeable connection){
        try {
            connection.close();
        }catch(IOException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while closing connections.");
        }
    }
}
