package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to update the client.
 */
public class ClientUpdatePacket implements Serializable{

    /**
     * List of moves done by previous player.
     */
    private List<String> message;

    /**
     * Game model updated.
     */
    private Game game;

    /**
     * Class constructor
     * @param game
     */
    public ClientUpdatePacket(Game game){
        this.game = game;
        this.message = new ArrayList<>();
    }

    /**
     * Get game model.
     * @return game model.
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * Get list of messages.
     * @return list of messages.
     */
    public List<String> getMessages(){
        return this.message;
    }

    /**
     * Set game in the packet.
     * @param game to set.
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * Set message in the list.
     * @param message to add.
     */
    public void setMessage(String message){
        this.message.add(message);
    }

    /**
     * Reset the list of messages.
     */
    public void messageReset(){
        this.message = new ArrayList<>();
    }

}
