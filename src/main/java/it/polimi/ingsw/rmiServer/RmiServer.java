package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.server.AbstractPlayer;
import it.polimi.ingsw.server.AbstractServer;
import it.polimi.ingsw.rmiClient.RmiClientInterface;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.exceptions.LoginException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;

public class RmiServer extends AbstractServer implements RmiServerInterface {

    /**
     * RMI registry.
     */
    private Registry registry;

    /**
     * Client interface.
     */
    private RmiClientInterface rmiClientInterface;

    /**
     * Logged in users cache.
     */
    private final HashMap<String, String> userCache;

    /**
     * Class constructor.
     * @param serverInterface
     */
    public RmiServer(ServerInterface serverInterface){
        super(serverInterface);
        userCache = new HashMap<>();
    }

    /**
     * Method to create or load rmi registry and publish the rmi server interface.
     * @param port
     */
    public void startServer(int port){
        createOrLoadRegistry(port);
        publishObject(port);
    }

    /**
     * Method to create or load registry.
     * @param port
     */
    private void createOrLoadRegistry(int port){
        try{
            registry = LocateRegistry.createRegistry(port);
        }catch(RemoteException e){
            System.out.println("Cannot create the registry, trying to load one");
        }
        try{
            registry = LocateRegistry.getRegistry(port);
        }catch(RemoteException e){
            System.out.println("Cannont load registry, server fail");
        }
        System.out.println("Server ready.");
    }

    /**
     * Method to publish the rmi server interface.
     * @param port
     */
    private void publishObject(int port){
        try {
            UnicastRemoteObject.exportObject(this, port);
            registry.rebind("RmiServerInterface", this);
        }catch(RemoteException e){
            System.out.println("Cannot publish the server object");
        }
    }

    /**
     * Method to login user, it calls server method that validate credentials and log in.
     * @param username
     * @param password
     * @param rmiPlayer
     */
    @Override
    public String login(String username, String password, RmiClientInterface rmiPlayer) throws LoginException{
        getServer().login(username, password, new RmiPlayer(rmiPlayer));
        String uniqueID = UUID.randomUUID().toString();
        userCache.put(uniqueID, username);
        return uniqueID;
    }

    /**
     * Method to get AbstractPlayer from the server.
     * @param uniqueID
     * @return
     */
    public AbstractPlayer getPlayer(String uniqueID){
        return getServer().getUser(userCache.get(uniqueID));
    }

    /**
     * Method to signin user.
     * @param username
     * @param password
     * @throws LoginException
     */
    @Override
    public void signin(String username, String password) throws LoginException {
        getServer().signin(username, password);
    }


}