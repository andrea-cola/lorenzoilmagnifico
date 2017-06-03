package it.polimi.ingsw.socketCommunicationRules;

import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class CommunicationRules {

    /**
     * SocketClient interface to communicate with Abstract SocketPlayer.
     */
    private final CommunicationRulesInterface communicationRulesInterface;

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
     * Class constructor.
     * @param objectInputStream
     * @param objectOutputStream
     * @param communicationRulesInterface
     */
    public CommunicationRules(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, CommunicationRulesInterface communicationRulesInterface){
        this.input = objectInputStream;
        this.output = objectOutputStream;
        this.communicationRulesInterface = communicationRulesInterface;
        requestsTable = new HashMap<>();
        setupRequestsTable();
    }

    /**
     * Request handler.
     */
    @FunctionalInterface
    private interface Handler{
        void handle();
    }

    /**
     * Method to add all request type in cache.
     */
    private void setupRequestsTable(){
        requestsTable.put(CommunicationConstants.LOGIN_REQUEST, this::loginPlayer);
        requestsTable.put(CommunicationConstants.SIGNIN_REQUEST, this::signinPlayer);
    }

    /**
     * Method to handle client request.
     * @param object request from the client.
     */
    /*package-local*/ synchronized void clientRequestHandler(Object object){
        Handler handler = requestsTable.get(object);
        if (handler != null) {
            handler.handle();
        }
    }

    /**
     * Method to get username and password from client and call the signin method on the server interface.
     */
    private void signinPlayer(){
        try{
            String username = (String)input.readObject();
            String password = (String)input.readObject();
            try{
                communicationRulesInterface.signin(username, password);
                output.writeObject(CommunicationConstants.CODE_OK);
            }catch(LoginException e){
                // segnalare errore in locale, qua sul server.
                output.writeObject(CommunicationConstants.CODE_ALREADY_EXISTS);
            }
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            // segnalare errore in locale, qua sul server.
        }
    }

    /**
     * Method to get username and password from client and call the login method on the server interface.
     */
    private void loginPlayer(){
        try{
            String username = (String)input.readObject();
            String password = (String)input.readObject();
            try{
                communicationRulesInterface.login(username, password);
                output.writeObject(CommunicationConstants.CODE_OK);
            }catch(LoginException e){
                // segnalare errore in locale, qua sul server.
                output.writeObject(CommunicationConstants.CODE_LOGIN_FAILED);
            }
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            // segnalare errore in locale, qua sul server.
        }
    }

}