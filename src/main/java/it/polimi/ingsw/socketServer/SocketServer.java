package it.polimi.ingsw.socketServer;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.exceptions.ServerException;
import it.polimi.ingsw.server.AbstractServer;
import it.polimi.ingsw.server.ServerInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class extends {@link AbstractServer} to implements socket server architecture.
 */
public class SocketServer extends AbstractServer {

    /**
     * SocketClient server.
     */
    private ServerSocket serverSocket;

    /**
     * Class constructor.
     * @param serverInterface to communicate with the server.
     */
    public SocketServer(ServerInterface serverInterface){
        super(serverInterface);
    }

    /**
     * Method to initialize new ServerSocket and new RequestManager.
     * @param port used to communicate.
     */
    @Override
    public void startServer(int port) throws ServerException{
        RequestManager requestManager;
        try{
            serverSocket = new ServerSocket(port);
            requestManager = new RequestManager();
            requestManager.start();
        }catch(IOException e){
            throw new ServerException("I/O stream error during initialization.", e);
        }
    }

    /**
     * Class used to handle the communication with clients.
     */
    private class RequestManager extends Thread{
        /**
         * Listener method.
         */
        @Override
        public void run(){
            while(true){
                try {
                    Socket socket = serverSocket.accept();
                    SocketPlayer socketPlayer = new SocketPlayer(socket, getServer());
                    new Thread(socketPlayer).start();
                    Debugger.printStandardMessage("New player @ " + socket.getInetAddress());
                } catch (IOException e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Problem while socket accepting.");
                    break;
                }
            }
        }

    }

}

