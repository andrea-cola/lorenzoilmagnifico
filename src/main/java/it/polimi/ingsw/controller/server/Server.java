package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.rmiServer.RmiServer;
import it.polimi.ingsw.controller.socketServer.SocketServer;
import it.polimi.ingsw.controller.gameServer.Room;
import it.polimi.ingsw.controller.exceptions.LoginException;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class Server implements ServerInterface{

    /**
     * Socket port.
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
    private static final String SQL_SERVER_URL = "jdbc:mysql://217.112.92.105:3306";

    /**
     * SQL database username
     */
    private static final String SQL_USERNAME = "pollo";

    /**
     * SQL database password
     */
    private static final String SQL_PASSWORD = "1234";

    /**
     * RMI server.
     */
    private RmiServer rmiServer;

    /**
     * Socket server.
     */
    private SocketServer socketServer;

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
     * Main method to launch server side game
     * @param args
     */
    public static void main(String[] args){
        Server server = new Server();
        server.startServer(RMI_PORT, SOCKET_PORT);
    }

    /**
     * Create a new instance of Server class.
     */
    private Server(){
        rmiServer = new RmiServer(this);
        socketServer = new SocketServer(this);
        players = new HashMap<String, AbstractPlayer>();
        rooms = new ArrayList<Room>();
        getAndLoadConfiguration();
    }

    /**
     * Start RMI server and Socket Server
     * @param rmiPort
     * @param socketPort
     */
    private void startServer(int rmiPort, int socketPort){
        rmiServer.startServer(rmiPort);
        socketServer.startServer(socketPort);
    }

    /**
     * Method to load configurations from file path
     */
    private void getAndLoadConfiguration(){
        // TODO
    }

    /**
     * Method to signup a new user
     * @param username
     * @param password
     * @param player
     */
    public void signup(String username, String password, AbstractPlayer player){
        //inserire tutti i controlli relativi alla già presenza dello stesso username che diventa chiave primaria
        synchronized (LOGIN_MUTEX) {
            try {
                connection = DriverManager.getConnection(SQL_SERVER_URL, SQL_USERNAME, SQL_PASSWORD);
                String query = "INSERT INTO USERS VALUES ('" + username + "', '" + password + "')";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    // gestire
                } else {
                    // gestire eccezione
                }
                connection.close();
            } catch (SQLException e) {
                // ricordarsi di lanciare un'eccezione.
            }
        }
    }

    /**
     * Method to login users. It validate credetianls and log in.
     * @param username
     * @param password
     * @param player
     */
    public void login(String username, String password, AbstractPlayer player) throws LoginException{
        synchronized (LOGIN_MUTEX) {
            try {
                connection = DriverManager.getConnection(SQL_SERVER_URL, SQL_USERNAME, SQL_PASSWORD);
                String query = "SELECT COUNT(*) AS NUMBER FROM USERS WHERE USERNAME='" + username + "' AND PASSWORD='" + password + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next() && resultSet.getInt("NUMBER") == 1) {
                    player.setNickname(username);
                    players.put(username, player);
                } else {
                    throw new LoginException();
                }
                connection.close();
            } catch (SQLException e) {
                // da gestire, forse meglio fare un throw verso l'alto.
            }
        }
    }

    /**
     * Return the abstract player related to given username
     * @param username
     * @return
     */
    public AbstractPlayer getUser(String username){
        return players.get(username);
    }


}
