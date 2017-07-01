package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientUpdatePacket implements Serializable{

    private ArrayList<String> message;

    private Game game;

    public ClientUpdatePacket(Game game){
        this.game = game;
        this.message = new ArrayList<>();
    }

    public Game getGame(){
        return this.game;
    }

    public ArrayList<String> getMessage(){
        return this.message;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void setMessage(String message){
        this.message.add(message);
    }

}
