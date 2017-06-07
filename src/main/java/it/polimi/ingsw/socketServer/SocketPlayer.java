package it.polimi.ingsw.socketServer;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.server.AbstractPlayer;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.socketCommunicationProtocol.ServerCommunicationProtocol;
import it.polimi.ingsw.socketCommunicationProtocol.ServerCommunicationProtocolInterface;

import java.io.*;
import java.net.Socket;

public class SocketPlayer extends AbstractPlayer implements Runnable, ServerCommunicationProtocolInterface {

    /**
     * Remote socket client.
     */
    private final transient Socket socketClient;

    /**
     * Server interface allow to access server methods.
     */
    private final transient ServerInterface serverInterface;

    /**
     * Output stream toward client.
     */
    private final transient ObjectOutputStream objectOutputStream;

    /**
     * Input stream from client.
     */
    private final transient ObjectInputStream objectInputStream;

    /**
     * Server protocol.
     */
    private final transient ServerCommunicationProtocol socketCommunicationProtocol;

    /**
     * Class constructor that initialize input and output streams.
     * @param socketClient obtained from the server accept.
     * @param serverInterface to communicate with the server.
     */
    /* package-local */SocketPlayer(Socket socketClient, ServerInterface serverInterface) throws IOException{
        this.socketClient = socketClient;
        this.serverInterface = serverInterface;
        objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
        socketCommunicationProtocol = new ServerCommunicationProtocol(objectInputStream, objectOutputStream, this);
    }

    /**
     * Listen input stream.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run(){
        try{
            while(true) {
                Object input = objectInputStream.readObject();
                socketCommunicationProtocol.clientRequestHandler(input);
            }
        }catch(IOException | ClassNotFoundException e){
            Debugger.printDebugMessage("[" + this.getClass().getName() + "] : communication error. Connection is down.");
        }finally{
            closeConnections(objectInputStream, objectOutputStream, socketClient);
        }
    }

    @Override
    public void loginPlayer(String username, String password) throws LoginException {
        serverInterface.loginPlayer(this, username, password);
    }

    @Override
    public void signInPlayer(String username, String password) throws LoginException {
        serverInterface.signInPlayer(username, password);
    }

    private void closeConnections(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socketClient){
        closeConnection(objectInputStream);
        closeConnection(objectOutputStream);
        closeConnection(socketClient);
    }

    private void closeConnection(Closeable connection){
        try {
            connection.close();
        }catch(IOException e){
            Debugger.printDebugMessage("[" + this.getClass().getName() + "] : Error while closing connections.", e);
        }
    }
}
