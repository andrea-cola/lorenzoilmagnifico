package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientUpdatePacket implements Serializable{

    private ArrayList<String> messsage;

    private Game game;

    public ClientUpdatePacket(Game game){
        this.game = game;
        this.messsage = new ArrayList<>();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public Game getGame(){
        return this.game;
    }

    public void setMesssage(String message){
        this.messsage.add(message);
    }

    public ArrayList<String> getMesssage(){
        return this.messsage;
    }

}
