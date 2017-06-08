package it.polimi.ingsw.server;

import it.polimi.ingsw.gameServer.Room;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;

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
    public ServerPlayer(){

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

}
