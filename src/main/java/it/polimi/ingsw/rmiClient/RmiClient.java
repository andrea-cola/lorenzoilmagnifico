package it.polimi.ingsw.rmiClient;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.rmiServer.RmiServerInterface;
import it.polimi.ingsw.server.AbstractPlayer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends AbstractPlayer implements RmiClientInterface {

    /**
     * Registry for published object.
     */
    private Registry registry;

    /**
     * Server RMI interface.
     */
    private RmiServerInterface server;

    /**
     * SocketPlayer unique identifier on the server.
     */
    private String myID;

    /**
     * Class constructor.
     */
    public RmiClient(){
        super();
    }

    /**
     * Method to connect client to server.
     */
    public void connect(){
        try {
            registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            server = (RmiServerInterface) registry.lookup("RmiServerInterface");
            UnicastRemoteObject.exportObject(this, 0);
            registry.rebind("RmiClientInterface", this);
            //server.clientConnected();
        }catch(RemoteException | NotBoundException e){
            // throw Client Exception
        }
    }

    /**
     * Login player using RMI Server interface method login().
     * @param username
     * @param password
     */
    public void login(String username, String password){
        try {
            myID = server.login(username, password, this);
        }catch(LoginException e){
            // da gestire
        }
    }

    /**
     * Signin player using RMI Server interface method signin().
     * @param username
     * @param password
     */
    public void signin(String username, String password){
        try {
            server.signin(username, password);
        } catch(LoginException e){
            // da gestire
        }
    }

}
