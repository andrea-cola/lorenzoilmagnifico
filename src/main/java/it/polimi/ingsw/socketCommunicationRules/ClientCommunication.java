package it.polimi.ingsw.socketCommunicationRules;

import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.CommunicationException;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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
     * Hashmap cointaining all responses codes from the server.
     */
    private HashMap<Object, ResponseHandler> responseTable;

    /**
     * Class constructor.
     * @param input
     * @param output
     * @param clientInterface
     */
    public ClientCommunication(ObjectInputStream input, ObjectOutputStream output, ClientInterface clientInterface) {
        objectInputStream = input;
        objectOutputStream = output;
        this.clientInterface = clientInterface;
        responseTable = new HashMap<>();
        setupResponsesTable();
    }

    /**
     * Method to load all responses code in the hashmap.
     */
    private void setupResponsesTable() {

    }

    /**
     * Method to login player on the server.
     * @param username
     * @param password
     * @throws CommunicationException
     * @throws LoginException
     */
    public void loginPlayer(String username, String password) throws CommunicationException{
        int code = 0;
        try{
            objectOutputStream.writeObject(CommunicationConstants.LOGIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            code = objectInputStream.readInt();
        }catch(IOException e){
            throw new CommunicationException();
        }
        if(code == CommunicationConstants.CODE_LOGIN_FAILED)
            throw new LoginException();
    }

    /**
     * Method to sign in the player on the server.
     * @param username
     * @param password
     * @throws CommunicationException
     * @throws LoginException
     */
    public void signin(String username, String password) throws CommunicationException{
        int code = 0;
        try{
            objectOutputStream.writeObject(CommunicationConstants.SIGNIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            code = objectInputStream.readInt();
        }catch(IOException e){
            throw new CommunicationException();
        }
        if(code == CommunicationConstants.CODE_ALREADY_EXISTS)
            throw new LoginException();
    }

    /**
     * Server response handler.
     * @param object
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
