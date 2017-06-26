package it.polimi.ingsw.model;

import java.io.Serializable;

public class ClientUpdatePacket implements Serializable{

    private String messsage;

    private Game game;

    public ClientUpdatePacket(Game game, String message){
        this.game = game;
        this.messsage = message;
    }

    public Game getGame(){
        return this.game;
    }

    public String getMesssage(){
        return this.messsage;
    }

}
