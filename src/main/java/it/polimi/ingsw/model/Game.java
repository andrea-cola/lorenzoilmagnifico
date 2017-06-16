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
    protected Map<String, Player> playersMap;

    /**
     * Development card deck.
     */

    /**
     * Class constructor.
     */
    protected Game(){
        playersMap = new LinkedHashMap<>();
    }

    /**
     * Get a specific player.
     * @param username of the player.
     * @return a player.
     */
    public Player getPlayer(String username){
        return playersMap.get(username);
    }

    /**
     * Get a list of all players.
     * @return a players list.
     */
    public ArrayList<Player> getAllPlayers(){
        ArrayList players = new ArrayList();
        players.addAll(playersMap.values());
        return players;
    }

}