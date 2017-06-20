package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.PersonalBoard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Configuration;

import java.io.IOException;

/**
 * This class the abstraction of the server.
 * It contains all methods shared between concrete communication classes.
 */
public abstract class AbstractClient {

    /**
     * Client interface.
     */
    private final ClientInterface clientInterface;

    /**
     * Server address.
     */
    private final String address;

    /**
     * Server port.
     */
    private final int port;

    /**
     * Class constructor.
     * @param clientInterface client controller.
     * @param address of the server.
     * @param port of the server.
     */
    public AbstractClient(ClientInterface clientInterface, String address, int port){
        this.clientInterface = clientInterface;
        this.address = address;
        this.port = port;
    }

    /**
     * Method to get the server ip address.
     * @return the server address.
     */
    protected String getAddress(){
        return this.address;
    }

    /**
     * Method to get the server port.
     * @return the server port.
     */
    protected int getPort(){
        return this.port;
    }

    /**
     * Method to get the ClientInterface.
     * @return the client controller.
     */
    protected ClientInterface getController() {
        return clientInterface;
    }

    /**
     * Abstract method to connect client to a server.
     * @throws ConnectionException if errors occur during connection to the server.
     */
    public abstract void connectToServer() throws ConnectionException;

    /**
     * Abstract method to loginPlayer user on a server.
     * @param username for the login.
     * @param password for the login.
     * @throws NetworkException if errors occur during login.
     */
    public abstract void loginPlayer(String username, String password) throws NetworkException;

    /**
     * Abstract method to sign in a user on a server.
     * @param username for the sign in.
     * @param password for the sign in.
     * @throws NetworkException if errors occur during sign in.
     */
    public abstract void signInPlayer(String username, String password) throws NetworkException;

    /**
     * Abstract method to join the first game room
     * @throws NetworkException
     */
    public abstract void joinRoom() throws NetworkException, RoomException;

    public abstract void createNewRoom(int maxPlayersNumber) throws NetworkException, RoomException;

    public abstract void sendPersonalBoardTileChoise(PersonalBoardTile personalBoardTile) throws NetworkException;

}
