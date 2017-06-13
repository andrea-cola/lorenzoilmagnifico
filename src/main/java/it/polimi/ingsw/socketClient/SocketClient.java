package it.polimi.ingsw.socketClient;


import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.socketCommunicationProtocol.ClientCommunicationProtocol;

import java.io.*;
import java.net.Socket;

/**
 * This class implements socket client communication. Extends {@link AbstractClient}.
 */
public class SocketClient extends AbstractClient{

    /**
     * Client socket object.
     */
    private Socket socket;

    /**
     * Input stream.
     */
    private ObjectInputStream objectInputStream;

    /**
     * Output stream.
     */
    private ObjectOutputStream objectOutputStream;

    /**
     * Client protocol to manage the communication with the server.
     */
    private ClientCommunicationProtocol clientCommunicationProtocol;

    /**
     * Server response handler object.
     */
    private ResponseManager responseManager;

    /**
     * Class constructor.
     * @param clientInterface controller of the client.
     * @param address of the server.
     * @param port of the server.
     */
    public SocketClient(ClientInterface clientInterface, String address, int port){
        super(clientInterface, address, port);
    }

    @Override
    public void connectToServer() throws ConnectionException {
        try{
            socket = new Socket(getAddress(), getPort());
            objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutputStream.flush();
            clientCommunicationProtocol = new ClientCommunicationProtocol(objectInputStream, objectOutputStream, getController());
        }catch (IOException e){
            throw new ConnectionException(e);
        }
    }

    /**
     * Start server response handler thread.
     */
    private void startServerResponseManager(){
        responseManager = new ResponseManager();
        responseManager.start();
    }

    /**
     * Abstract method to loginPlayer user on a server.
     * @param username for the login.
     * @param password for the login.
     * @throws NetworkException if errors occur during login.
     */
    @Override
    public void loginPlayer(String username, String password) throws NetworkException {
        clientCommunicationProtocol.playerLogin(username, password);
    }

    /**
     * Abstract method to sign in a user on a server.
     * @param username for the sign in.
     * @param password for the sign in.
     * @throws NetworkException if errors occur during sign in.
     */
    @Override
    public void signInPlayer(String username, String password) throws NetworkException {
        clientCommunicationProtocol.playerSignIn(username, password);
    }

    /*
    public void joinRoom(){
        startServerResponseManager();
    }
    */

    /**
     * Thread of the server response handler.
     */
    private class ResponseManager extends Thread{
        boolean flag = true;

        @Override
        public void run(){
            while(flag){
                try{
                    Object object = objectInputStream.readObject();
                    clientCommunicationProtocol.handleResponse(object);
                } catch (IOException | ClassNotFoundException e){
                    flag = false;
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Errors occur while reading server response.", e);
                }
            }
            closeConnections(objectInputStream, objectOutputStream, socket);
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
                Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while closing connections.", e);
            }
        }
    }
}