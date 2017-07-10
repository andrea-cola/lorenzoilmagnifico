package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.gameserver.Room;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the abstraction of the remote player.
 */
public abstract class ServerPlayer extends Player{

    /**
     * Room where player is playing.
     */
    private transient Room room;

    /**
     * Method to get player room.
     * @return player room.
     */
    public Room getRoom(){
        return this.room;
    }

    /**
     * Method to set player room.
     * @param room of the player.
     */
    public void setRoom(Room room){
        this.room = room;
    }

    /**
     * Method to send Game Info
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void sendGameInfo(Game game) throws NetworkException;

    public void ping() throws RemoteException{ }

    /**
     * Method to send Personal tile
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException;

    /**
     * Method to send the leader cards
     * @param leaderCards the leader cards deck
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException;

    /**
     * Method to notify that a new turn has started
     * @param username the username of the player that is performing the turn
     * @param seconds the time available for the player to perform the turn
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifyTurnStarted(String username, long seconds) throws NetworkException;

    /**
     * Method to send game model updates
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException;

    /**
     * Method to manage the support for the church process
     * @param flag this flag is used to check if the player supports the church or not
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void supportForTheChurch(boolean flag) throws NetworkException;

    /**
     * Method to notify the end of the game
     * @param ranking the players final ranking
     * @throws NetworkException if error occurs during network communication
     */
    public abstract void notifyEndGame(ServerPlayer[] ranking) throws NetworkException;

}

