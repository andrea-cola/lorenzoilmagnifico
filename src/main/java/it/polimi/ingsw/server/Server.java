package it.polimi.ingsw.server;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.socketServer.SocketServer;
import it.polimi.ingsw.gameServer.Room;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.rmiServer.RMIServer;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Main server class that extends {@link ServerInterface}.
 * This class contains the main method to launch the server.
 * It represent the game server.
 */
public class Server implements ServerInterface{

    /**
     * SocketClient port.
     */
    private static final int SOCKET_PORT = 3031;

    /**
     * RMI port.
     */
    private static final int RMI_PORT = 3032;

    /**
     * Mutex object to handle concurrency between users during loginPlayer.
     */
    private static final Object LOGIN_MUTEX = new Object();

    /**
     * Mutex object to handle concurrency during room create.
     */
    private static final Object ROOM_MUTEX = new Object();

    /**
     * RMI server.
     */
    private RMIServer rmiServer;

    /**
     * SocketClient server.
     */
    private SocketServer socketServer;

    /**
     * MySQL server.
     */
    private DBServer dbServer;

    /**
     * Map of all logged in players
     */
    private HashMap<String, ServerPlayer> players;

    /**
     * Room list.
     */
    private ArrayList<Room> rooms;

    /**
     * SQL connection object
     */
    private Connection connection;

    /**
     * Class constructor.
     */
    public Server(){
        rmiServer = new RMIServer(this);
        socketServer = new SocketServer(this);
        players = new HashMap<String, ServerPlayer>();
        rooms = new ArrayList<Room>();
        dbServer = new DBServer();
    }

    /**
     * Main method to launch the server.
     * @param args passed to server.
     */
    public static void main(String[] args){
        Server server = new Server();
        try {
            server.startSocketRMIServer(SOCKET_PORT, RMI_PORT);
            server.startDatabase();
            Debugger.printStandardMessage("Socket server ready.\nRMI server ready.\nSQL server ready.");
        } catch (IOException e){
            Debugger.printDebugMessage("Server.java" , "Error while starting communication server.", e);
        } catch (SQLException e){
            Debugger.printDebugMessage("Server.java", "Error while starting database server.", e);
        }
    }

    /**
     * Method to initialize and start socket server and RMI server.
     * @param socketPort of socket server.
     * @param rmiPort of RMI server.
     * @throws IOException if errors occur during initialization.
     */
    private void startSocketRMIServer(int socketPort, int rmiPort) throws IOException{
        socketServer.startServer(socketPort);
        rmiServer.startServer(rmiPort);
    }

    /**
     * Method to initialize and start database server.
     * @throws SQLException if errors occur during initialization.
     */
    private void startDatabase() throws SQLException{
        dbServer.connectToDatabase();
    }

    /**
     * Sign in the player to server.
     * @param username of the player is trying to sign in.
     * @param password of the player is trying to sign in.
     * @throws LoginException if errors occur during sign in.
     */
    @Override
    public void signInPlayer(String username, String password) throws LoginException{
        synchronized (LOGIN_MUTEX) {
            if(!players.containsKey(username))
                dbServer.signInPlayer(username, password);
            else
                throw new LoginException(LoginErrorType.USER_ALREADY_EXISTS);
        }
    }

    /**
     * Login the player to server then put username and remote player reference in the user cache (Hashmap).
     * @param player is trying to login.
     * @param username of the player is trying to login.
     * @param password of the player is trying to login.
     * @throws LoginException if errors occur during login.
     */
    @Override
    public void loginPlayer(ServerPlayer player, String username, String password) throws LoginException{
        synchronized (LOGIN_MUTEX) {
            if(!players.containsKey(username)) {
                dbServer.loginPlayer(username, password);
                player.setNickname(username);
                players.put(username, player);
            }
            else
                throw new LoginException(LoginErrorType.USER_ALREADY_LOGGEDIN);

        }
    }

    /**
     * Method to get remote player reference from the user cache.
     * @param username of the remote player.
     * @return remote player that corresponds to username provided.
     */
    @Override
    public ServerPlayer getUser(String username){
        return players.get(username);
    }


}
