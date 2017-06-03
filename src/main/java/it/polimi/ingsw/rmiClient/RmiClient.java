package it.polimi.ingsw.rmiClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.rmiServer.RmiServerInterface;

public class RmiClient extends AbstractClient implements RmiClientInterface {

    /**
     * RMI registry.
     */
    private Registry registry;

    /**
     * RMI server interface.
     */
    private RmiServerInterface server;

    /**
     * Unique player id.
     */
    private String playerID;

    /**
     * Class constructor.
     * @param clientInterface
     * @param address
     * @param port
     */
    public RmiClient(ClientInterface clientInterface, String address, int port){
        super(clientInterface, address, port);
    }

    /**
     * Method to obtain the rmi server interface reference from the registry.
     * @throws RemoteException
     */
    @Override
    public void connectToServer() throws ConnectionException{
        try {
            registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            server = (RmiServerInterface) registry.lookup("RmiServerInterface");
            UnicastRemoteObject.exportObject(this, 0);
            registry.rebind("RmiClientInterface", this);
        }catch(RemoteException | NotBoundException e){
            throw new ConnectionException("Error while connecting RMI Server.", e);
        }
    }

    /**
     * Method to login user on server.
     * @param username
     * @param password
     * @throws LoginException
     */
    @Override
    public void login(String username, String password) throws LoginException{
        playerID = server.login(username, password, this);
    }

    /**
     * Method to sign in user on server.
     * @param username
     * @param password
     * @throws LoginException
     */
    @Override
    public void signin(String username, String password) throws LoginException{
        server.signin(username, password);
    }
}
