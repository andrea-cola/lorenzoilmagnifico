package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.gameserver.Room;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
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
     * Class constructor.
     */
    protected ServerPlayer(){

    }

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

    public abstract void sendGameInfo(Game game) throws NetworkException;

    public void ping() throws RemoteException{

    }

    public abstract void sendPersonalTile(List<PersonalBoardTile> personalBoardTiles) throws NetworkException;

    public abstract void sendLeaderCards(List<LeaderCard> leaderCards) throws NetworkException;

    public abstract void notifyTurnStarted(String username, long seconds) throws NetworkException;

    public abstract void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException;

    public abstract void supportForTheChurch(boolean flag) throws NetworkException;

}

