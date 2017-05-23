package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketPlayer extends AbstractPlayer implements Runnable, SocketCommunicationRulesInterface{

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
    }

    /**
     * Listen input stream.
     */
    public void run(){
        try{
            while(true) {
                Object object = objectInputStream.readObject();
                //gestire object
            }
        }catch(IOException e){
            // da gestire
        }catch(ClassNotFoundException e){
            // da gestire
        }finally{
            closeConnection(objectOutputStream);
            closeConnection(objectInputStream);
            closeConnection(socketClient);
        }
    }

    /**
     * Method to close output stream
     * @param objectOutputStream
     */
    private void closeConnection(ObjectOutputStream objectOutputStream){
        try {
            objectOutputStream.close();
        }catch(IOException e){
            System.out.println("Error occours while closing output stream");
        }
    }

    /**
     * Method to close input stream
     * @param objectInputStream
     */
    private void closeConnection(ObjectInputStream objectInputStream){
        try {
            objectInputStream.close();
        }catch(IOException e){
            System.out.println("Error occours while closing input stream");
        }
    }

    /**
     * Method to close client socket
     * @param socket
     */
    private void closeConnection(Socket socket){
        try {
            socket.close();
        }catch(IOException e){
            System.out.println("Error occours while closing socket stream");
        }
    }

    /**
     * Method to handle user login.
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) throws LoginException {
        serverInterface.login(username, password, this);
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
