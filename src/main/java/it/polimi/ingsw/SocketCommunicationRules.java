package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class SocketCommunicationRules {

    /**
     * Socket interface to communicate with Abstract Player.
     */
    private final SocketCommunicationRulesInterface socketCommunicationRulesInterface;

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
     * @param socketCommunicationRulesInterface
     */
    public SocketCommunicationRules(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, SocketCommunicationRulesInterface socketCommunicationRulesInterface){
        this.input = objectInputStream;
        this.output = objectOutputStream;
        this.socketCommunicationRulesInterface = socketCommunicationRulesInterface;
        requestsTable = new HashMap<Object, Handler>();
        setupTypeRequests();
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
    private void setupTypeRequests(){
        requestsTable.put(CommunicationConstants.JOIN_ROOM_REQUEST, this::loginPlayer);
        requestsTable.put(CommunicationConstants.END_TURN, this::endTurn);
    }

    /**
     * Method to handle client request.
     * @param object request from the client.
     */
    public synchronized void clientRequestHandler(Object object){
        Handler handler = requestsTable.get(object);
        if (handler != null) {
            handler.handle();
        }
    }

    private void loginPlayer(){
        try{
            String username = (String)input.readObject();
            String password = (String)input.readObject();
            try{
                socketCommunicationRulesInterface.login(username, password);
                output.writeObject("200");
            }catch(LoginException e){
                //gestire con un response code
                output.writeObject("400");
            }
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            //gestire exception.
        }
    }

    private void endTurn(){
        System.out.println("Fine turno.");
    }



}

/**
 * Class to list all client requests.
 */
class CommunicationConstants{

    static final String LOGIN_REQUEST = "loginRequest";
    static final String JOIN_ROOM_REQUEST = "joinRequest";
    static final String CREATE_ROOM_REQUEST = "createRoomRequest";
    static final String APPLY_CONFIGURATION_REQUEST = "applyConfigurationRequest";
    static final String END_TURN = "endTurn";

}