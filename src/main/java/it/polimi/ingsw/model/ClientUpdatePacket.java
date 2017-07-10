package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientUpdatePacket implements Serializable{

    private List<String> message;

    private Game game;

    /**
     * Class constructor
     * @param game
     */
    public ClientUpdatePacket(Game game){
        this.game = game;
        this.message = new ArrayList<>();
    }


    public Game getGame(){
        return this.game;
    }

    public List<String> getMessages(){
        return this.message;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void setMessage(String message){
        this.message.add(message);
    }

    public void messageReset(){
        this.message = new ArrayList<>();
    }

}
