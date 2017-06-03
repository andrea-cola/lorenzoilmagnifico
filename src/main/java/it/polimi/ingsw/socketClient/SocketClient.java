package it.polimi.ingsw.socketClient;


import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.CommunicationException;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.socketCommunicationRules.ClientCommunication;

import java.io.*;
import java.net.Socket;

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
    private ClientCommunication clientCommunication;

    /**
     * Server response handler object.
     */
    private SocketClientRequestHandler socketClientRequestHandler;

    /**
     * Class constructor.
     * @param clientInterface
     * @param address
     * @param port
     */
    public SocketClient(ClientInterface clientInterface, String address, int port){
        super(clientInterface, address, port);
    }

    /**
     * Method to initialize all class objects. It also starts the listener thread.
     * @throws ConnectionException
     */
    @Override
    public void connectToServer() throws ConnectionException {
        try{
            socket = new Socket(getAddress(), getPort());
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            clientCommunication = new ClientCommunication(objectInputStream, objectOutputStream, getController());
            startListener();
        }catch (IOException e){
            throw new ConnectionException(e);
        }
    }

    /**
     * Method to run a response listener thread.
     */
    private void startListener(){
        socketClientRequestHandler = new SocketClientRequestHandler(objectInputStream);
        socketClientRequestHandler.start();
    }

    /**
     * Method to login on the server.
     * @param username
     * @param password
     * @throws CommunicationException
     */
    @Override
    public void login(String username, String password) throws CommunicationException {
        clientCommunication.loginPlayer(username, password);
    }

    /**
     * Method to signin a user on the server.
     * @param username
     * @param password
     * @throws CommunicationException
     */
    @Override
    public void signin(String username, String password) throws CommunicationException {
        clientCommunication.loginPlayer(username, password);
    }
}
