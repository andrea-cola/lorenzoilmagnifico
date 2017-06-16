package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable{

    /**
     * Mainboard reference.
     */
    protected MainBoard mainBoard;

    /**
     * Map of all players.
     */
    private Map<String, Player> playersMap;

    /**
     * Development card deck.
     */

    /**
     * Class constructor.
     */
    public Game(){
        this.playersMap = new LinkedHashMap<>();
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
     * Get a list of all players.
     * @return a players list.
     */
    public ArrayList<Player> getAllPlayers(){
        ArrayList players = new ArrayList();
        players.addAll(this.playersMap.values());
        return players;
    }

    public Map<String, Player> getPlayersMap(){
        return this.playersMap;
    }

}