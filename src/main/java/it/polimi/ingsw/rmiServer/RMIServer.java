package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.exceptions.ServerException;
import it.polimi.ingsw.rmiClient.RMIClientInterface;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.server.AbstractServer;
import it.polimi.ingsw.server.ServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class extends {@link AbstractServer} to create an RMI server.
 */
public class RMIServer extends AbstractServer implements RMIServerInterface {

    /**
     * Name of RMI server interface.
     */
    private static final String RMI_SERVER_INTERFACE_NAME = "RMIServerInterface";

    /**
     * RMI registry.
     */
    private Registry registry;

    /**
     * Client interface.
     */
    private RMIClientInterface RMIClientInterface;

    /**
     * Logged users cache.
     */
    private final HashMap<String, String> userCache;

    /**
     * Class constructor.
     * @param serverInterface controller of the server.
     */
    public RMIServer(ServerInterface serverInterface){
        super(serverInterface);
        userCache = new HashMap<>();
    }

    /**
     * Start the RMI Server.
     * @param port to use for the communication.
     * @throws IOException if errors occur during starting proceedings.
     */
    public void startServer(int port) throws ServerException{
        registry = createOrLoadRegistry(port);
        publishObject(port);
    }

    /**
     * Create a registry if it not already exists.
     * @param port to use.
     * @return a registry.
     * @throws ServerException if error occurs during create/load of the registry.
     */
    private Registry createOrLoadRegistry(int port) throws ServerException{
        try{
            return LocateRegistry.createRegistry(port);
        }catch(RemoteException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "RMI registry already exists.");
        }
        try{
            return LocateRegistry.getRegistry(port);
        }catch(RemoteException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "RMI registry cannot be loaded.");
        }
        throw new ServerException("[" + this.getClass().getSimpleName() + "] : RMI interface cannot be loaded.");
    }

    /**
     * Publish the RMI server interface on the registry.
     * @param port to use.
     * @throws IOException if errors occur.
     */
    private void publishObject(int port) throws ServerException{
        try {
            registry.rebind(RMI_SERVER_INTERFACE_NAME, this);
            UnicastRemoteObject.exportObject(this, port);
        }catch(RemoteException e){
            e.printStackTrace();
            throw new ServerException("[" + this.getClass().getSimpleName() + "] : Fail during server interface loading. RMI Server is not working.");
        }
    }

    /**
     * Method to get the player who has the identifier passed as parameter.
     * @param uniqueID of the player.
     * @return the player.
     */
    public ServerPlayer getPlayer(String uniqueID){
        return getServer().getUser(userCache.get(uniqueID));
    }

    /**
     * Player login method.
     * @param username provided by the client to login.
     * @param password provided by the client to login.
     * @param rmiPlayer is trying to login.
     * @return a unique identifier of the player.
     * @throws IOException if errors occur during login proceedings.
     */
    @Override
    public String loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException{
        getServer().loginPlayer(new RMIPlayer(rmiPlayer), username, password);
        String uniqueID = UUID.randomUUID().toString();
        userCache.put(uniqueID, username);
        return uniqueID;
    }

    /**
     * Player sign in method.
     * @param username provided by the client to sign in.
     * @param password provided by the client to sign in.
     * @throws IOException
     */
    @Override
    public void signInPlayer(String username, String password) throws IOException {
        getServer().signInPlayer(username, password);
    }

    /**
     * Remote method to join the player to the first game room
     * @param username which is making the request
     * @throws RoomException if the server is not reachable
     */
    @Override
    public void joinFirstRoom(String username) throws RoomException {
        getServer().joinRoom(getPlayer(username));
    }

    /**
     * Create a new room.
     * @param id to get the player from the cache.
     * @param maxPlayersNumber allowed in the room.
     * @return the configuration number.
     */
    @Override
    public Configuration createNewRoom(String id, int maxPlayersNumber){
        return getServer().createNewRoom(getPlayer(id), maxPlayersNumber);
    }

}