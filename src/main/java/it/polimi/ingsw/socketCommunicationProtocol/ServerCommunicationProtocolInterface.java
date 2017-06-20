package it.polimi.ingsw.socketCommunicationProtocol;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Configuration;

import java.util.List;

/**
 * Interface used as callback from clients.
 */
public interface ServerCommunicationProtocolInterface {

    /**
     * Method to handle user loginPlayer request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if loginPlayer error occurs.
     */
    void loginPlayer(String username, String password) throws LoginException;

    /**
     * Method to handle user sign in request.
     * @param username provided by the client.
     * @param password provided by the cluent.
     * @throws LoginException if signInPlayer error occurs.
     */
    void signInPlayer(String username, String password) throws LoginException;

    /**
     * Try to join a room.
     * @throws RoomException if errors occur during the access.
     */
    void joinRoom() throws RoomException;

    /**
     * Create a new room and return a configuration bundle.
     * @param maxPlayersNumber allowed in the room.
     * @return configuration bundle.
     */
    void createNewRoom(int maxPlayersNumber) throws RoomException;

    void setPlayerPersonalBoardTile(PersonalBoardTile personalBoardTile);
}