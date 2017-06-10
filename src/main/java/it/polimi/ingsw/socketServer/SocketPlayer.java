package it.polimi.ingsw.socketServer;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.socketCommunicationProtocol.ServerCommunicationProtocol;
import it.polimi.ingsw.socketCommunicationProtocol.ServerCommunicationProtocolInterface;

import java.io.*;
import java.net.Socket;

/**
 * This class extends Server player for socket communication.
 */
public class SocketPlayer extends ServerPlayer implements Runnable, ServerCommunicationProtocolInterface {

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
     * Class constructor that initialize input/output streams and communicaiton server side protocol.
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
     * Listen input stream from client.
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
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Connection with the client is down.");
        }finally{
            closeConnections(objectInputStream, objectOutputStream, socketClient);
        }
    }

    /**
     * Method to handle user loginPlayer request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if loginPlayer error occurs.
     */
    @Override
    public void loginPlayer(String username, String password) throws LoginException {
        serverInterface.loginPlayer(this, username, password);
    }

    /**
     * Method to handle user sign in request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if signInPlayer error occurs.
     */
    @Override
    public void signInPlayer(String username, String password) throws LoginException {
        serverInterface.signInPlayer(username, password);
    }

    /**
     * Try to join a room.
     *
     * @throws RoomException if errors occur during the access.
     */
    @Override
    public void joinRoom() throws RoomException {

    }

    /**
     * Close input/output streams and socket.
     * @param objectInputStream input stream.
     * @param objectOutputStream output stream.
     * @param socketClient client socket.
     */
    private void closeConnections(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socketClient){
        closeConnection(objectInputStream);
        closeConnection(objectOutputStream);
        closeConnection(socketClient);
    }

    /**
     * Method to close the closeable passed as argument.
     * @param connection to close.
     */
    private void closeConnection(Closeable connection){
        try {
            connection.close();
        }catch(IOException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while closing connections.");
        }
    }
}
