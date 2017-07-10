package it.polimi.ingsw.socketclient;

import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Printer;
import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.protocol.ClientCommunication;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * This class implements socket client communication. Extends {@link AbstractClient}.
 */
public class SocketClient extends AbstractClient{

    /**
     * Client socket object.
     */
    private Socket socket;

    /**
     * Input stream.
     */
    private ObjectInputStream objectInputStream;

    /**
     * Output stream.
     */
    private ObjectOutputStream objectOutputStream;

    /**
     * Client protocol to manage the communication with the server.
     */
    private ClientCommunication clientCommunication;

    /**
     * Class constructor.
     * @param clientInterface controller of the client.
     * @param address of the server.
     * @param port of the server.
     */
    public SocketClient(ClientInterface clientInterface, String address, int port){
        super(clientInterface, address, port);
    }


    /**
     * Method to initialise a new socket
     * @throws ConnectionException if errors occur during connection
     */
    @Override
    public void connectToServer() throws ConnectionException {
        try{
            socket = new Socket(getAddress(), getPort());
            objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutputStream.flush();
            clientCommunication = new ClientCommunication(objectInputStream, objectOutputStream, getClient());
        }catch (IOException e){
            throw new ConnectionException(e);
        }
    }

    /**
     * Start server response handler thread.
     */
    private void startServerResponseManager(){
        new ResponseManager().start();
    }

    /**
     * Method to loginPlayer user on a server.
     * @param username for the login.
     * @param password for the login.
     * @throws NetworkException if errors occur during login.
     */
    @Override
    public void loginPlayer(String username, String password) throws NetworkException {
        clientCommunication.playerLogin(username, password);
    }

    /**
     * Method to sign in a user on a server.
     * @param username for the sign in.
     * @param password for the sign in.
     * @throws NetworkException if errors occur during sign in.
     */
    @Override
    public void signInPlayer(String username, String password) throws NetworkException {
        clientCommunication.playerSignIn(username, password);
    }

    /**
     * Method to join the room
     * @throws NetworkException if error occurs during network communication
     * @throws RoomException if errors occur during joining room
     */
    @Override
    public void joinRoom() throws NetworkException, RoomException {
        clientCommunication.playerJoinRoom();
        startServerResponseManager();
    }

    /**
     * Method for creating a new room
     * @param maxPlayersNumber the number of players that can reach the room
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void createNewRoom(int maxPlayersNumber) throws NetworkException{
        clientCommunication.createNewRoom(maxPlayersNumber);
        startServerResponseManager();
    }

    /**
     * Method for notifying to the server the personal board tile chosen
     * @param personalBoardTile the personal board tile chosen
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) throws NetworkException {
        clientCommunication.notifyPersonalBoardTileChoice(personalBoardTile);
    }

    /**
     * Method for notifying to the server the leader card chosen
     * @param leaderCard the leader card choosen
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyLeaderCardChoice(LeaderCard leaderCard) throws NetworkException {
        clientCommunication.notifyLeaderCardChoice(leaderCard);
    }

    /**
     * Method for notifying to the server to set a family member inside a tower
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to perform the action
     * @param towerIndex the index of the tower where the player wants to place the family member
     * @param cellIndex the index of the cell where the player wants to place the family member
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex, playerChoices);
    }

    /**
     * Method for notifying to the server to set a family member inside the council
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to perform the action
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInCouncil(familyMemberColor, servants, playerChoices);
    }

    /**
     * Method for notifying to the server to set a family member inside the market
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to perform the action
     * @param marketIndex the index of the market cell in which the player wants to place the family member
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInMarket(familyMemberColor, servants, marketIndex, playerChoices);
    }

    /**
     * Method for notifying to the server to set a family member inside harvest simple action space
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to perform the action
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInHarvestSimple(familyMemberColor, servants, playerChoices);
    }

    /**
     * Method for notifying to the server to set a family member inside production simple action space
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to perform the action
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInProductionSimple(familyMemberColor, servants, playerChoices);
    }

    /**
     * Method for notifying to the server to set a family member inside harvest extended action space
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to perform the action
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInHarvestExtended(familyMemberColor, servants, playerChoices);
    }

    /**
     * Method for notifying to the server to set a family member inside production extended action space
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to perform the action
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInProductionExtended(familyMemberColor, servants, playerChoices);
    }

    /**
     * Method for notifying to the server to activate a leader card
     * @param leaderCardIndex the index of the leader card inside the personal leader card's deck of the player
     * @param servants the number of servants to perform the action
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyActivateLeader(int leaderCardIndex, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.activateLeader(leaderCardIndex, servants, playerChoices);
    }

    /**
     * Method for notifying to the server to discard a leader card
     * @param leaderCardIndex the index of the leader card inside the personal leader card's deck of the player
     * @param playerChoices to communicate to the server the player's choices
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyDiscardLeader(int leaderCardIndex, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.discardLeader(leaderCardIndex, playerChoices);
    }

    /**
     * Method for notifying to the server the choice for the support for the church
     * @param choice this value is about the choice of the player to support the church or not
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifySupportForTheChurch(boolean choice) throws NetworkException {
        clientCommunication.notifySupportForTheChurch(choice);
    }

    /**
     * Method for notifying to the server the end of the turn
     */
    @Override
    public void endTurn() {
        clientCommunication.endTurn();
    }

    /**
     * Thread of the server response handler.
     */
    private class ResponseManager extends Thread{
        boolean flag = true;

        @Override
        public void run(){
            while(flag){
                try{
                    Object object = objectInputStream.readObject();
                    clientCommunication.handleResponse(object);
                } catch (IOException | ClassNotFoundException e){
                    flag = false;
                    Printer.printDebugMessage(this.getClass().getSimpleName(), "Errors occur while reading server response. Connection is close and game is over.");
                }
            }
            closeConnections(objectInputStream, objectOutputStream, socket);
        }

        private void closeConnections(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socketClient){
            closeConnection(objectInputStream);
            closeConnection(objectOutputStream);
            closeConnection(socketClient);
        }

        private void closeConnection(Closeable connection){
            try {
                connection.close();
            }catch(IOException e){
                Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while closing connections.");
            }
        }
    }
}
