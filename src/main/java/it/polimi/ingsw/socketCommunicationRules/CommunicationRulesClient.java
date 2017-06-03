package it.polimi.ingsw.socketCommunicationRules;

import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.CommunicationException;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class CommunicationRulesClient {

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private ClientInterface clientInterface;

    private HashMap<Object, ResponseHandler> responseTable;

    public CommunicationRulesClient(ObjectInputStream input, ObjectOutputStream output, ClientInterface clientInterface) {
        objectInputStream = input;
        objectOutputStream = output;
        this.clientInterface = clientInterface;
        responseTable = new HashMap<>();
        setupResponsesTable();
    }

    private void setupResponsesTable() {

    }

    public void loginPlayer(String username, String password) throws CommunicationException, LoginException{
        int code = 0;
        try{
            objectOutputStream.writeObject(CommunicationConstants.LOGIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            code = objectInputStream.readInt();
        }catch(IOException e){
            throw new CommunicationException();
        }
        if(code == CommunicationConstants.CODE_LOGIN_FAILED)
            throw new LoginException();
    }

    public void signin(String username, String password) throws CommunicationException, LoginException{
        int code = 0;
        try{
            objectOutputStream.writeObject(CommunicationConstants.SIGNIN_REQUEST);
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            objectOutputStream.flush();
            code = objectInputStream.readInt();
        }catch(IOException e){
            throw new CommunicationException();
        }
        if(code == CommunicationConstants.CODE_ALREADY_EXISTS)
            throw new LoginException();
    }

    public void handleResponse(Object object) {
        ResponseHandler handler = responseTable.get(object);
        if (handler != null) {
            handler.handle();
        }
    }

    @FunctionalInterface
    private interface ResponseHandler {
        void handle();
    }
}
