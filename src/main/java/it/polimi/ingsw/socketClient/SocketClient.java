package it.polimi.ingsw.socketClient;


import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.socketCommunicationRules.CommunicationRules;
import it.polimi.ingsw.socketCommunicationRules.CommunicationRulesClient;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient extends AbstractClient{

    private Socket socket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private CommunicationRulesClient communicationRulesClient;

    /**
     * Class constructor.
     * @param clientInterface
     * @param address
     * @param port
     */
    public SocketClient(ClientInterface clientInterface, String address, int port){
        super(clientInterface, address, port);
    }

    @Override
    public void connectToServer() throws ConnectionException {
        try{
            socket = new Socket(getAddress(), getPort());
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            communicationRulesClient = new CommunicationRulesClient(objectInputStream, objectOutputStream, getController());
        }catch (IOException e){
            throw new ConnectionException(e);
        }
    }

    @Override
    public void login(String username, String password) throws LoginException {

    }

    @Override
    public void signin(String username, String password) throws LoginException {

    }
}
