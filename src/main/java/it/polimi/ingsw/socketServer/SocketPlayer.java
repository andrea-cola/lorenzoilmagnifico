package it.polimi.ingsw.socketServer;

import it.polimi.ingsw.server.AbstractPlayer;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.socketCommunicationRules.ServerCommunication;
import it.polimi.ingsw.socketCommunicationRules.ServerCommunicationInterface;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketPlayer extends AbstractPlayer implements Runnable, ServerCommunicationInterface {

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
    private final transient ServerCommunication socketCommunicationProtocol;

    /**
     * Class constructor that initialize input and output streams
     * @param socketClient : client socket obtained by server.accept()
     * @param serverInterface : server controller interface
     */
    /* package-local */SocketPlayer(Socket socketClient, ServerInterface serverInterface) throws IOException{
        this.socketClient = socketClient;
        this.serverInterface = serverInterface;
        objectInputStream = new ObjectInputStream(socketClient.getInputStream());
        objectOutputStream = new ObjectOutputStream(socketClient.getOutputStream());
        objectOutputStream.flush();
        socketCommunicationProtocol = new ServerCommunication(objectInputStream, objectOutputStream, this);
    }

    /**
     * Listen input stream.
     */
    public void run(){
        try{
            while(true) {
                Object input = objectInputStream.readObject();
                socketCommunicationProtocol.clientRequestHandler(input);
            }
        }catch(IOException e){
            // da gestire
        }catch(ClassNotFoundException e){
            // da gestire
        }finally{
            closeConnections(objectInputStream, objectOutputStream, socketClient);
        }
    }

    /**
     * It calls all method necessary to close streams passed as arguments.
     * @param objectInputStream
     * @param objectOutputStream
     * @param socketClient
     */
    private void closeConnections(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socketClient){
        closeConnection(objectInputStream);
        closeConnection(objectOutputStream);
        closeConnection(socketClient);
    }

    /**
     * Method to close the stream passed as argument.
     * @param connection
     */
    private void closeConnection(Closeable connection){
        try {
            connection.close();
        }catch(IOException e){
            // segnale errore nella chiusura dello streaming.
        }
    }

    /**
     * Method to handle user login request.
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) throws LoginException {
        serverInterface.login(username, password, this);
    }

    /**
     * Method to handle user sign in request.
     *
     * @param username
     * @param password
     * @throws LoginException
     */
    @Override
    public void signin(String username, String password) throws LoginException {
        serverInterface.signin(username, password);
    }

    /**
     * Method to join in a room.
     */
    @Override
    public void joinGame() {

    }

    /**
     * Place family member on the main board
     */
    @Override
    public void placeFamilyMember() {

    }

    /**
     * End turn.
     */
    @Override
    public void endTurn() {

    }
}
