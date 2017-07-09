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
     * Abstract method to loginPlayer user on a server.
     * @param username for the login.
     * @param password for the login.
     * @throws NetworkException if errors occur during login.
     */
    @Override
    public void loginPlayer(String username, String password) throws NetworkException {
        clientCommunication.playerLogin(username, password);
    }

    /**
     * Abstract method to sign in a user on a server.
     * @param username for the sign in.
     * @param password for the sign in.
     * @throws NetworkException if errors occur during sign in.
     */
    @Override
    public void signInPlayer(String username, String password) throws NetworkException {
        clientCommunication.playerSignIn(username, password);
    }

    @Override
    public void joinRoom() throws NetworkException, RoomException {
        clientCommunication.playerJoinRoom();
        startServerResponseManager();
    }

    @Override
    public void createNewRoom(int maxPlayersNumber) throws NetworkException{
        clientCommunication.createNewRoom(maxPlayersNumber);
        startServerResponseManager();
    }

    @Override
    public void notifyPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) throws NetworkException {
        clientCommunication.notifyPersonalBoardTileChoice(personalBoardTile);
    }

    @Override
    public void notifyLeaderCardChoice(LeaderCard leaderCard) throws NetworkException {
        clientCommunication.notifyLeaderCardChoice(leaderCard);
    }

    @Override
    public void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex, playerChoices);
    }

    @Override
    public void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInCouncil(familyMemberColor, servants, playerChoices);
    }

    @Override
    public void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInMarket(familyMemberColor, servants, marketIndex, playerChoices);
    }

    @Override
    public void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInHarvestSimple(familyMemberColor, servants, playerChoices);
    }

    @Override
    public void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInProductionSimple(familyMemberColor, servants, playerChoices);
    }

    @Override
    public void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInHarvestExtended(familyMemberColor, servants, playerChoices);
    }

    @Override
    public void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.notifySetFamilyMemberInProductionExtended(familyMemberColor, servants, playerChoices);
    }

    @Override
    public void notifyActivateLeader(int leaderCardIndex, int servants, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.activateLeader(leaderCardIndex, servants, playerChoices);
    }

    @Override
    public void notifyDiscardLeader(int leaderCardIndex, HashMap<String, Object> playerChoices) throws NetworkException {
        clientCommunication.discardLeader(leaderCardIndex, playerChoices);
    }

    @Override
    public void notifySupportForTheChurch(boolean choice) throws NetworkException {
        clientCommunication.notifySupportForTheChurch(choice);
    }

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
