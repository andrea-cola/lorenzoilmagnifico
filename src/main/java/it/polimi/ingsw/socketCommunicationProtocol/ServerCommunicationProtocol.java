package it.polimi.ingsw.socketCommunicationProtocol;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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
            System.out.println("eccociProtocol");
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
            output.writeObject(response);
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while handling sign in player request.");
        }
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
            output.writeObject(response);
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(),"Error while handling loginPlayer request.");
        }
    }

}