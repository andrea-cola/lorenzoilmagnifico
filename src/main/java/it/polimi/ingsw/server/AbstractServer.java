package it.polimi.ingsw.server;

import java.io.IOException;

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
     * @throws IOException
     */
    public abstract void startServer(int port) throws IOException;

}
