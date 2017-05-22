package it.polimi.ingsw.controller.socketServer;

import it.polimi.ingsw.controller.server.AbstractServer;
import it.polimi.ingsw.controller.server.ServerInterface;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer extends AbstractServer {

    /**
     * Socket server.
     */
    private ServerSocket server;

    /**
     * Method to handle socket client request. Create a new thread for each request.
     */
    private SocketRequestHandler socketRequest;

    /**
     * Class constructor
     * @param serverInterface
     */
    public SocketServer(ServerInterface serverInterface){
        super(serverInterface);
    }

    /**
     * Method to initialize new ServerSocket and new SocketRequestHandler
     * @param port
     */
    public void startServer(int port){
        try{
            server = new ServerSocket(port);
            socketRequest = new SocketRequestHandler(server, getServer());
            socketRequest.start();
        }catch(IOException e){
            System.out.println("Socket server error during initialization.");
        }
    }

}
