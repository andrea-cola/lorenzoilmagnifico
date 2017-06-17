package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable{

    /**
     * Mainboard reference.
     */
    protected MainBoard mainBoard;

    /**
     * Dice
     */
    private Dice dices;

    /**
     * Map of all players.
     */
    private Map<String, Player> playersMap;

    /**
     * Class constructor
     */
    public Game(MainBoard mainBoard){
        this.mainBoard = mainBoard;
        this.dices = new Dice();
        this.playersMap = new LinkedHashMap<>();
    }

    /**
     * Get the mainBoard
     * @return
     */
    public MainBoard getMainBoard(){
        return this.mainBoard;
    }

    /**
     * Get the dices
     * @return
     */
    public Dice getDices(){
        return this.dices;
    }

    /**
     * Get a specific player.
     * @param username of the player.
     * @return a player.
     */
    public Player getPlayer(String username){
        return this.playersMap.get(username);
    }

    /**
     * Get all the players
     * @return
     */
    public Map<String, Player> getPlayersMap(){
        return this.playersMap;
    }

}