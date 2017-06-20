package it.polimi.ingsw.socketCommunicationProtocol;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

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
     * Mutex object.
     */
    private final Object object = new Object();

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
        requestsTable.put(CommunicationProtocolConstants.JOIN_ROOM_REQUEST, this::joinRoom);
        requestsTable.put(CommunicationProtocolConstants.CREATE_ROOM_REQUEST, this::createNewRoom);
        requestsTable.put(CommunicationProtocolConstants.PERSONAL_TILES, this::setPlayerPersonalBoardTile);
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

    /**
     * Try to join the last room on the server.
     * If fails send a bad response.
     */
    public void joinRoom(){
        int response;
        try {
            try {
                serverCommunicationProtocolInterface.joinRoom();
                response = CommunicationProtocolConstants.ROOM_JOINED;
            } catch (RoomException e) {
                response = CommunicationProtocolConstants.NO_ROOM_AVAILABLE;
            }
            output.writeObject(response);
            output.flush();
        } catch (IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while joining room.");
        }
    }

    /**
     * Create a new room calling server method.
     * Response is a Configuration object.
     */
    private void createNewRoom(){
        int maxPlayersNumber;
        try{
            maxPlayersNumber = (int)input.readObject();
            serverCommunicationProtocolInterface.createNewRoom(maxPlayersNumber);
        } catch (ClassNotFoundException | ClassCastException | RoomException | IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error in creation room proceedings.");
        }
    }

    /**
     * Send to client the game bundle.
     * @param game to send.
     * @throws NetworkException if errors occur during communication.
     */
    public void sendGameInfo(Game game) throws NetworkException{
        synchronized (object){
            try{
                output.writeObject(CommunicationProtocolConstants.GAME_MODEL);
                output.writeObject(game);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    public void sendPersonalBoardTile(List<PersonalBoardTile> personalBoardTileList) throws NetworkException{
        synchronized (object){
            try{
                output.writeObject(CommunicationProtocolConstants.PERSONAL_TILES);
                output.writeObject(personalBoardTileList);
                output.flush();
            } catch (IOException e){
                throw new NetworkException();
            }
        }
    }

    public void setPlayerPersonalBoardTile(){
        try {
            PersonalBoardTile personalBoardTile = (PersonalBoardTile)input.readObject();
            serverCommunicationProtocolInterface.setPlayerPersonalBoardTile(personalBoardTile);
        } catch (ClassNotFoundException | ClassCastException | IOException e){

        }
    }

}