package it.polimi.ingsw.client;

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

    protected ClientInterface getController() {
        return clientInterface;
    }

    public abstract void connect();

    public abstract void login(String username, String password);

    public abstract void signin(String username, String password);

}
