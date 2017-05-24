package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerInterface;

import java.rmi.ServerException;

public abstract class AbstractServer {

    /**
     * Server controller.
     */
    private final ServerInterface server;

    /**
     * Class constructor.
     * @param server
     */
    public AbstractServer(ServerInterface server){
        this.server = server;
    }

    /**
     * Method to return server interface.
     * @return
     */
    protected ServerInterface getServer(){
        return server;
    }

    /**
     * Method to start server.
     * @param port
     * @throws ServerException
     */
    public abstract void startServer(int port) throws ServerException;

}
