package it.polimi.ingsw.controller.client;

/**
 * Created by andrea on 22/05/17.
 */
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

    protected String getAddress(){
        return this.address;
    }

    protected int getPort(){
        return this.port;
    }

    protected

}
