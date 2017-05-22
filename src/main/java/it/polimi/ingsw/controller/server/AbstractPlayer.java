package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.gameServer.Room;
import it.polimi.ingsw.controller.model.Player;

public abstract class AbstractPlayer extends Player{

    private transient Room room;

    /**
     * Here we put all methods to communicate with game players
     */

    public AbstractPlayer(){

    }

    public Room getRoom(){
        return this.room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

}
