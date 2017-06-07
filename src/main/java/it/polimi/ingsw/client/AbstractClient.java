package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.ConnectionException;

public abstract class AbstractClient {

    /**
     * Client interface.
     */
    private final ClientInterface clientInterface;

    /**
     * Server address.
     */
    private final String address;

    /**
     * Server port.
     */
    private final int port;

    /**
     * Class constructor.
     * @param clientInterface
     * @param address
     * @param port
     */
    public AbstractClient(ClientInterface clientInterface, String address, int port){
        this.clientInterface = clientInterface;
        this.address = address;
        this.port = port;
    }

    /**
     * Method to get the server ip address.
     * @return
     */
    protected String getAddress(){
        return this.address;
    }

    /**
     * Method to get the server port.
     * @return
     */
    protected int getPort(){
        return this.port;
    }

    /**
     * Method to get the ClientInterface.
     * @return
     */
    protected ClientInterface getController() {
        return clientInterface;
    }

    /**
     * Abstract method to connect client to a server.
     * @throws ConnectionException
     */
    public abstract void connectToServer() throws ConnectionException;

    /**
     * Abstract method to loginPlayer user on a server.
     * @param username
     * @param password
     * @throws NetworkException
     */
    public abstract void loginPlayer(String username, String password) throws NetworkException;

    /**
     * Abstract method to sign in a user on a server.
     * @param username
     * @param password
     * @throws NetworkException
     */
    public abstract void signInPlayer(String username, String password) throws NetworkException;

}
