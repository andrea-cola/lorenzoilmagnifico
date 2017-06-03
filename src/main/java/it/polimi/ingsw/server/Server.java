package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginEnum;
import it.polimi.ingsw.socketServer.SocketServer;
import it.polimi.ingsw.gameServer.Room;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.rmiServer.RmiServer;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;


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
     * Mutex object to handle concurrency between users during login.
     */
    private static final Object LOGIN_MUTEX = new Object();

    /**
     * Mutex object to handle concurrency during room create.
     */
    private static final Object ROOM_MUTEX = new Object();

    /**
     * SQL database url
     */
    private static final String SQL_SERVER_URL = "jdbc:mysql://217.112.92.105:3306/lorenzo";

    /**
     * SQL database username
     */
    private static final String SQL_USERNAME = "lorenzo";

    /**
     * SQL database password
     */
    private static final String SQL_PASSWORD = "lorenzo900";

    /**
     * RMI server.
     */
    private RmiServer rmiServer;

    /**
     * SocketClient server.
     */
    private SocketServer socketServer;

    /**
     * MySQL server.
     */
    private MySQLServer mySQLServer;

    /**
     * Map of all logged in players
     */
    private HashMap<String, AbstractPlayer> players;

    /**
     * Room list.
     */
    private ArrayList<Room> rooms;

    /**
     * SQL connection object
     */
    private Connection connection;

    /**
     * Create a new instance of Server class.
     */
    public Server(){
        rmiServer = new RmiServer(this);
        socketServer = new SocketServer(this);
        players = new HashMap<String, AbstractPlayer>();
        rooms = new ArrayList<Room>();
        mySQLServer = new MySQLServer();
        getAndLoadConfiguration();
    }

    /**
     * Main method to launch server side Game
     * @param args
     */
    public static void main(String[] args){
        Server server = new Server();
        server.startServer(RMI_PORT, SOCKET_PORT);
    }

    /**
     * Start RMI server and SocketClient Server.
     * @param rmiPort
     * @param socketPort
     */
    private void startServer(int rmiPort, int socketPort){
        rmiServer.startServer(rmiPort);
        socketServer.startServer(socketPort);
    }

    /**
     * Method to signup a new user.
     * @param username
     * @param password
     */
    public void signin(String username, String password) throws LoginException{
        synchronized (LOGIN_MUTEX) {
            mySQLServer.connectAndCreate();
            mySQLServer.signin(username, password);
            mySQLServer.closeSafely();
        }
    }

    /**
     * Method to login users. It validate credentials and log in.
     * @param username
     * @param password
     * @param player
     */
    public void login(String username, String password, AbstractPlayer player) throws LoginException{
        synchronized (LOGIN_MUTEX) {
            mySQLServer.connectAndCreate();
            mySQLServer.login(username, password);
            player.setNickname(username);
            players.put(username, player);
            mySQLServer.closeSafely();
        }
    }

    /**
     * Return the abstract player related to given username.
     * @param username
     * @return
     */
    public AbstractPlayer getUser(String username){
        return players.get(username);
    }

    /**
     * Method to load configurations from file path.
     */
    private void getAndLoadConfiguration(){
        // TODO
    }


}
