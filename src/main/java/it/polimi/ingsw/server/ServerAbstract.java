package it.polimi.ingsw.server;

import java.io.IOException;

/**
 * This class is the abstraction of communication server.
 * This class will be extended to obatin common method to communicate.
 */
public abstract class ServerAbstract {

    /**
     * Server controller.
     */
    private final ServerInterface server;

    /**
     * Class constructor.
     */
    public ServerAbstract(ServerInterface server){
        this.server = server;
    }

    /**
     * Method to return server interface.
     * @return the server interface
     */
    protected ServerInterface getServer(){
        return server;
    }

    /**
     * Method to start server.
     * @param port the number of the port
     * @throws IOException
     */
    public abstract void startServer(int port) throws IOException;

}
