package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.gameServer.Configurator;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.socketServer.SocketServer;
import it.polimi.ingsw.gameServer.Room;
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
    private static final Object LOGIN_SIGNIN_MUTEX = new Object();

    /**
     * Mutex object to handle concurrency during room create.
     */
    private static final Object JOIN_ROOM_MUTEX = new Object();

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
     * Map of all logged in players.
     */
    private HashMap<String, ServerPlayer> players;

    /**
     * Map of all logged and active players.
     */
    private HashMap<String, Boolean> activePlayer;

    /**
     * Room list.
     */
    private ArrayList<Room> rooms;


    /**
     * Class constructor.
     */
    public Server() throws ServerException{
        rmiServer = new RMIServer(this);
        socketServer = new SocketServer(this);
        players = new HashMap<String, ServerPlayer>();
        activePlayer = new HashMap<String, Boolean>();
        rooms = new ArrayList<Room>();
        dbServer = new DBServer();
        configure();
    }

    /**
     * Main method to launch the server.
     * @param args passed to server.
     */
    public static void main(String[] args){
        try {
            Server server = new Server();
            server.startSocketRMIServer(SOCKET_PORT, RMI_PORT);
            server.startDatabase();
            Debugger.printStandardMessage("Socket server ready.");
            Debugger.printStandardMessage("RMI server ready.");
            Debugger.printStandardMessage("SQL server ready.");
        } catch(ServerException | SQLException e){
            Debugger.printDebugMessage("Server.java", "Error while starting the server.", e);
        }
    }

    /**
     * Load and set configurations from file.
     */
    private void configure() throws ServerException{
        try{
            Configurator.loadConfigurations();
        }catch(ConfigurationException e){
            throw new ServerException("Error in game configuration and parsing proceedings.", e);
        }
    }

    /**
     * Method to initialize and start socket server and RMI server.
     * @param socketPort of socket server.
     * @param rmiPort of RMI server.
     * @throws IOException if errors occur during initialization.
     */
    private void startSocketRMIServer(int socketPort, int rmiPort) throws ServerException{
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
        synchronized (LOGIN_SIGNIN_MUTEX) {
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
        synchronized (LOGIN_SIGNIN_MUTEX) {
            if(!players.containsKey(username) || (players.containsKey(username) && !activePlayer.get(username))) {
                dbServer.loginPlayer(username, password);
                player.setUsername(username);
                players.put(username, player);
                activePlayer.put(username, true);
            }
            else
                throw new LoginException(LoginErrorType.USER_ALREADY_LOGGEDIN);

        }
    }

    /**
     * This function disable the user when he goes down.
     * @param player that goes down.
     */
    @Override
    public void disableUser(ServerPlayer player){
        Debugger.printDebugMessage(this.getClass().getSimpleName(), player.getUsername() + " is disabled.");
        if(activePlayer.containsKey(player.getUsername()))
            this.activePlayer.put(player.getUsername(), false);
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

    /**
     * Method used to join a player into a room.
     * @param serverPlayer who would join in a room.
     * @throws RoomException if error occurs.
     */
    @Override
    public void joinRoom(ServerPlayer serverPlayer) throws RoomException {
        Room playerRoom = null;
        for (Room room : rooms){
            if (room.userAlreadyJoined(serverPlayer)) {
                playerRoom = room;
            }
        }
        synchronized (JOIN_ROOM_MUTEX){
            if(playerRoom != null) {
                playerRoom.rejoinRoom(serverPlayer);
                serverPlayer.setRoom(playerRoom);
                Debugger.printDebugMessage(serverPlayer.getUsername() + " rejoined in room #" + playerRoom.getRoomID());
            }
            else if(!rooms.isEmpty()) {
                playerRoom = rooms.get(rooms.size() - 1);
                playerRoom.joinRoom(serverPlayer);
                serverPlayer.setRoom(playerRoom);
                Debugger.printDebugMessage(serverPlayer.getUsername() + " joined in room #" + playerRoom.getRoomID());
            } else
                throw new RoomException("There are no rooms available!");
        }
    }

    /**
     * Create a new room.
     * @param serverPlayer is creating new room.
     * @param maxPlayers allowed in the room.
     * @return configuration object.
     */
    @Override
    public void createNewRoom(ServerPlayer serverPlayer, int maxPlayers) throws RoomException{
        synchronized (JOIN_ROOM_MUTEX){
            boolean flag = false;
            try{
                joinRoom(serverPlayer);
                flag = true;
            }
            catch(RoomException e) {
                Debugger.printStandardMessage(serverPlayer.getUsername() + " is creating a new room.");
            }
            if(!flag){
                Configuration configuration = Configurator.getConfiguration();
                Room room = new Room(rooms.size() + 1, serverPlayer, maxPlayers, configuration);
                rooms.add(room);
                serverPlayer.setRoom(room);
            }
            else {
                throw new RoomException();
            }
        }
    }

    /**
     * Set personal board tile to the server player who invoke this method. Then notifies
     * the choice to the room.
     * @param serverPlayer of the choice.
     * @param personalBoardTile to be set.
     */
    @Override
    public void setPlayerPersonalBoardTile(ServerPlayer serverPlayer, PersonalBoardTile personalBoardTile) {
        serverPlayer.getPersonalBoard().setPersonalBoardTile(personalBoardTile);
        serverPlayer.getRoom().onPersonalTilesChosen();
    }

    @Override
    public void setPlayerLeaderCard(ServerPlayer serverPlayer, LeaderCard leaderCard) {
        serverPlayer.getPersonalBoard().setLeaderCard(leaderCard);
        serverPlayer.getRoom().onLeaderCardChosen();
    }

}
