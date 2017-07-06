package it.polimi.ingsw.socketserver;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.socketprotocol.ServerCommunicationProtocol;
import it.polimi.ingsw.socketprotocol.ServerCommunicationProtocolInterface;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * This class extends Server player for socket communication.
 */
public class SocketPlayer extends ServerPlayer implements Runnable, ServerCommunicationProtocolInterface {

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
    private final transient ServerCommunicationProtocol socketCommunicationProtocol;

    /**
     * Class constructor that initialize input/output streams and communicaiton server side protocol.
     * @param socketClient obtained from the server accept.
     * @param serverInterface to communicate with the server.
     */
    /* package-local */SocketPlayer(Socket socketClient, ServerInterface serverInterface) throws IOException{
        this.socketClient = socketClient;
        this.serverInterface = serverInterface;
        objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
        socketCommunicationProtocol = new ServerCommunicationProtocol(objectInputStream, objectOutputStream, this);
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
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Connection with the client is down.");
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

    @Override
    public void notifyPlayerPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) {
        this.getPersonalBoard().setPersonalBoardTile(personalBoardTile);
        this.getRoom().onPersonalTilesChosen();
    }

    @Override
    public void notifyPlayerLeaderCardChoice(LeaderCard leaderCard) {
        this.getPersonalBoard().setLeaderCard(leaderCard);
        this.getRoom().onLeaderCardChosen();
    }

    @Override
    public void setFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, Map<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInTower(this, familyMemberColor, servants, towerIndex, cellIndex, playerChoices);
    }

    @Override
    public void setFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInCouncil(this, familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex, Map<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInMarket(this, familyMemberColor, servants, marketIndex, playerChoices);
    }

    @Override
    public void setFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInHarvestSimple(this, familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInHarvestExtended(this, familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInProductionSimple(this, familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) {
        this.getRoom().setFamilyMemberInProductionExtended(this, familyMemberColor, servants, playerChoices);
    }

    @Override
    public void activateLeaderCard(int leaderCardIndex, int servants, Map<String, Object> playerChoices) {
        this.getRoom().activateLeader(this, leaderCardIndex, servants, playerChoices);
    }

    @Override
    public void discardLeader(int leaderCardIndex, Map<String, Object> playerChoices) {
        this.getRoom().discardLeader(this, leaderCardIndex, playerChoices);
    }

    @Override
    public void notifySupportForTheChurch(boolean flag) {
        this.getRoom().onSupportToTheChurchChoice(this, flag);
    }

    @Override
    public void sendGameInfo(Game game) throws NetworkException {
        socketCommunicationProtocol.sendGameInfo(game);
    }

    @Override
    public void sendPersonalTile(List<PersonalBoardTile> personalBoardTiles) throws NetworkException {
        socketCommunicationProtocol.sendPersonalBoardTile(personalBoardTiles);
    }

    @Override
    public void sendLeaderCards(List<LeaderCard> leaderCards) throws NetworkException {
        socketCommunicationProtocol.sendLeaderCards(leaderCards);
    }

    @Override
    public void notifyTurnStarted(String username, long seconds) throws NetworkException {
        socketCommunicationProtocol.notifyTurnStarted(username, seconds);
    }

    @Override
    public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {
        socketCommunicationProtocol.sendGameModelUpdate(clientUpdatePacket);
    }

    @Override
    public void supportForTheChurch(boolean flag) throws NetworkException {
        socketCommunicationProtocol.supportForTheChurch(flag);
    }

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
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while closing connections.");
        }
    }
}
