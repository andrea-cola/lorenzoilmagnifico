package it.polimi.ingsw.controller.rmiServer;

import it.polimi.ingsw.controller.rmiClient.RmiClientInterface;
import it.polimi.ingsw.controller.server.AbstractServer;
import it.polimi.ingsw.controller.server.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
     * Class constructor.
     * @param serverInterface
     */
    public RmiServer(ServerInterface serverInterface){
        super(serverInterface);
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
    public void login(String username, String password, RmiPlayer rmiPlayer){
        getServer().login(username, password, rmiPlayer);
    }



}