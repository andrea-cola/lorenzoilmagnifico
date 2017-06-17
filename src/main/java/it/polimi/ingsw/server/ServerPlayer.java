package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.gameServer.Room;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;

import java.rmi.RemoteException;

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

}
