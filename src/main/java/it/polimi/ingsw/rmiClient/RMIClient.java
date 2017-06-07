package it.polimi.ingsw.rmiClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.rmiServer.RMIServerInterface;
import sun.nio.ch.Net;

public class RMIClient extends AbstractClient implements RMIClientInterface {

    /**
     * RMI registry.
     */
    private Registry registry;

    /**
     * RMI server interface.
     */
    private RMIServerInterface server;

    /**
     * Unique player id.
     */
    private String playerID;

    private static final String RMIClientInterfaceName = "RMIClientInterface";

    private static final String RMIServerInterfaceName = "RMIServerInterface";

    /**
     * Class constructor.
     * @param clientInterface
     * @param address
     * @param port
     */
    public RMIClient(ClientInterface clientInterface, String address, int port){
        super(clientInterface, address, port);
    }

    /**
     * Method to obtain the rmi server interface reference from the registry.
     * @throws RemoteException
     */
    @Override
    public void connectToServer() throws ConnectionException{
        try {
            registry = LocateRegistry.getRegistry(getAddress(), getPort());
            server = (RMIServerInterface) registry.lookup("RMIServerInterface");
            UnicastRemoteObject.exportObject(this, 0);
            registry.rebind(RMIClientInterfaceName, this);
        }catch(RemoteException | NotBoundException e){
            throw new ConnectionException(e);
        }
    }

    /**
     * Method to loginPlayer user on server.
     * @param username
     * @param password
     * @throws LoginException
     */
    @Override
    public void loginPlayer(String username, String password) throws NetworkException{
        try {
            playerID = server.loginPlayer(username, password, this);
        } catch(LoginException e) {
            throw e;
        } catch(IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to sign in user on server.
     * @param username
     * @param password
     * @throws LoginException
     */
    @Override
    public void signInPlayer(String username, String password) throws NetworkException{
        try {
            server.signInPlayer(username, password);
        } catch(IOException e){
            throw new NetworkException();
        }
    }
}
