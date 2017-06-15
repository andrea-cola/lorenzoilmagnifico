package it.polimi.ingsw.socketCommunicationProtocol;

import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.utility.Configuration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * This class is used to define the communication socket protocol. Used by the client to interact
 * with {@link ServerCommunicationProtocol}.
 */
public class ClientCommunicationProtocol {

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
    public ClientCommunicationProtocol(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, ClientInterface clientInterface) {
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
            objectOutputStream.writeObject(CommunicationProtocolConstants.SIGNIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            response = (int)objectInputStream.readObject();
        }catch(IOException | ClassNotFoundException e){
            throw new NetworkException();
        }
        if(response == CommunicationProtocolConstants.USER_FAIL_GENERIC)
            throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
        if(response == CommunicationProtocolConstants.USER_ALREADY_EXISTS)
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
            objectOutputStream.writeObject(CommunicationProtocolConstants.LOGIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            System.out.println("Ciao");
            response = (int)objectInputStream.readObject();
            System.out.println(response);
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            throw new NetworkException(e);
        }
        if(response == CommunicationProtocolConstants.USER_ALREADY_LOGGEDIN)
            throw new LoginException(LoginErrorType.USER_ALREADY_LOGGEDIN);
        if(response == CommunicationProtocolConstants.USER_NOT_EXISTS)
            throw new LoginException(LoginErrorType.USER_NOT_EXISTS);
        if(response == CommunicationProtocolConstants.USER_LOGIN_WRONG_PASSWORD || response == CommunicationProtocolConstants.USER_FAIL_GENERIC)
            throw new LoginException(LoginErrorType.USER_WRONG_PASSWORD);
    }

    /**
     * Manage the request to the server or join user in the first game room
     * @throws RoomException if there are no game room available
     * @throws NetworkException if the server doesn't work or something happened
     */
    public void playerJoinRoom() throws NetworkException, RoomException{
        int responseCode;
        try {
            objectOutputStream.writeObject(CommunicationProtocolConstants.JOIN_ROOM_REQUEST);
            objectOutputStream.flush();
            responseCode = (int) objectInputStream.readObject();
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            throw new NetworkException(e);
        }
        if (responseCode == CommunicationProtocolConstants.NO_ROOM_AVAILABLE)
            throw new RoomException();
    }

    /**
     * Requires the server to create a new room.
     * @param maxPlayersNumber allowed in the room.
     * @return configuration bundle.
     * @throws NetworkException if errors occur during room creation or network communication.
     */
    public Configuration createNewRoom(int maxPlayersNumber) throws NetworkException{
        Configuration response;
        try{
            objectOutputStream.writeObject(CommunicationProtocolConstants.CREATE_ROOM_REQUEST);
            objectOutputStream.writeObject(maxPlayersNumber);
            objectOutputStream.flush();
            response = (Configuration)objectInputStream.readObject();
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            throw new NetworkException(e);
        }
        return response;
    }

    /**
     * Handle server response and execute the associated method.
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