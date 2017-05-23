package it.polimi.ingsw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* package-local */class SocketRequestHandler extends Thread{

    /**
     * Socket Server
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
    SocketRequestHandler(ServerSocket serverSocket, ServerInterface controller){
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
                SocketPlayer socketPlayer = new SocketPlayer(socket, serverInterface);
                new Thread(socketPlayer).start();
            } catch (IOException e) {
                System.out.println("Error while waiting/accepting socket connection.");
            }
        }
    }

}
