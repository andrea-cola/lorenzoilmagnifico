package it.polimi.ingsw.socketCommunicationRules;

import it.polimi.ingsw.cli.CLIOutputWriter;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ServerCommunication {

    /**
     * SocketClient interface to communicate with Abstract SocketPlayer.
     */
    private final ServerCommunicationInterface serverCommunicationInterface;

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
     * @param serverCommunicationInterface
     */
    public ServerCommunication(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, ServerCommunicationInterface serverCommunicationInterface){
        this.input = objectInputStream;
        this.output = objectOutputStream;
        this.serverCommunicationInterface = serverCommunicationInterface;
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
    public synchronized void clientRequestHandler(Object object){
        Handler handler = requestsTable.get(object);
        if (handler != null) {
            handler.handle();
        }
    }

    /**
     * Method to get username and password from client and call the signin method on the server interface.
     */
    private void signinPlayer(){
        int code;
        try{
            String username = (String)input.readObject();
            String password = (String)input.readObject();
            try{
                serverCommunicationInterface.signin(username, password);
                code = CommunicationConstants.CODE_OK;
            }catch(LoginException e){
                CLIOutputWriter.printDebugMessage("[ServerCommunication.java] : Error while sigin request.", e);
                code = CommunicationConstants.CODE_ALREADY_EXISTS;
            }
            output.writeObject(code);
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            CLIOutputWriter.printDebugMessage("[ServerCommunication.java] : Error while handling sigin request.", e);
        }
    }

    /**
     * Method to get username and password from client and call the login method on the server interface.
     */
    private void loginPlayer(){
        int code;
        try{
            String username = (String)input.readObject();
            String password = (String)input.readObject();
            try{
                serverCommunicationInterface.login(username, password);
                code = CommunicationConstants.CODE_OK;
            }catch(LoginException e){
                CLIOutputWriter.printDebugMessage("[ServerCommunication.java] : Error while loggin in the user.", e);
                code = CommunicationConstants.CODE_LOGIN_FAILED;
            }
            output.writeObject(code);
            output.flush();
        } catch(IOException | ClassCastException | ClassNotFoundException e){
            CLIOutputWriter.printDebugMessage("[ServerCommunication.java] : Error while handling login request.", e);
        }
    }

}