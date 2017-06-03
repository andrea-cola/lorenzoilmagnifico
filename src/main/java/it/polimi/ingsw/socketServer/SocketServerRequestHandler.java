package it.polimi.ingsw.socketServer;

import it.polimi.ingsw.cli.CLIOutputWriter;
import it.polimi.ingsw.server.ServerInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* package-local */class SocketServerRequestHandler extends Thread{

    /**
     * SocketClient Server
     */
    private ServerSocket serverSocket;

    /**
     * Main Server interface
     */
    private ServerInterface serverInterface;

    /**
     * Class constructor
     * @param serverSocket
     * @param controller
     */
    SocketServerRequestHandler(ServerSocket serverSocket, ServerInterface controller){
        this.serverSocket = serverSocket;
        this.serverInterface = controller;
    }

    /**
     * Run Thread to listen and accept socket client requests,
     * then initialize a new SocketPlayer and start new thread to handle it.
     */
    @Override
    public void run(){
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                SocketPlayer socketSocketPlayer = new SocketPlayer(socket, serverInterface);
                new Thread(socketSocketPlayer).start();
            } catch (IOException e) {
                CLIOutputWriter.printDebugMessage("Error while listening for new clients.", e);
                break;
            }
        }
    }

}
