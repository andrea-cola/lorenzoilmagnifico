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
    private HashMap<String, AbstractPlayer> players;

    /**
     * Room list.
     */
    private ArrayList<Room> rooms;

    /**
     * SQL connection object
     */
    private Connection connection;

    public Server(){
        rmiServer = new RMIServer(this);
        socketServer = new SocketServer(this);
        players = new HashMap<String, AbstractPlayer>();
        rooms = new ArrayList<Room>();
        dbServer = new DBServer();
    }


    public static void main(String[] args){
        Server server = new Server();
        try {
            server.startSocketRMIServer(SOCKET_PORT, RMI_PORT);
            server.startDatabase();
            Debugger.printStandardMessage("[ing.polimi.ingsw.server.Server] : Socket server ready. RMI server ready. SQL server ready.");
        } catch (IOException e){
            Debugger.printDebugMessage("[ing.polimi.ingsw.server.Server] : Error while starting communication server.", e);
        } catch (SQLException e){
            Debugger.printDebugMessage("[ing.polimi.ingsw.server.Server] : Error while starting database server.", e);
        }
    }

    private void startSocketRMIServer(int socketPort, int rmiPort) throws IOException{
        socketServer.startServer(socketPort);
        rmiServer.startServer(rmiPort);
    }

    private void startDatabase() throws SQLException{
        dbServer.connectToDatabase();
    }

    public void signInPlayer(String username, String password) throws LoginException{
        synchronized (LOGIN_MUTEX) {
            if(!players.containsKey(username))
                dbServer.signInPlayer(username, password);
            else
                throw new LoginException(LoginErrorType.USER_ALREADY_EXISTS);
        }
    }

    public void loginPlayer(AbstractPlayer player, String username, String password) throws LoginException{
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

    public AbstractPlayer getUser(String username){
        return players.get(username);
    }


}
