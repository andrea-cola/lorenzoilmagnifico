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
     * Map of all players. Each player is identified by its username (String)
     */
    private Map<String, Player> players;

    /**
     * Class constructor
     */
    public Game(){
        this.mainBoard = new MainBoard();
        this.dices = new Dice();
        this.players = new LinkedHashMap<>();
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
        return this.players.get(username);
    }

    /**
     * Get all the players
     * @return
     */
    public Map<String, Player> getPlayersMap(){
        return this.players;
    }

    /**
     * This method gets a DevelopmentCard from TowerCell and add it to PersonalBoard
     * @param player
     * @param indexTower
     * @param indexCell
     * @return
     */
    public void pickupDevelopmentCardFromTower(Player player, FamilyMemberColor familyMemberColor, int indexTower, int indexCell){
        Tower tower = this.mainBoard.getTower(indexTower);
        TowerCell cell = tower.getTowerCell(indexCell);

        //check if the cell is empty
        if (cell.getEmpty()){
            //check if familyMember's value is enough to be placed inside the towerCell
            if (cell.familyMemberCanBePlaced(player, familyMemberColor)){
                //check if the user has resources enough to buy the card
                if (cell.developmentCardCanBeBuyedBy(player)){
                    DevelopmentCard card = cell.getDevelopmentCard();
                    this.players.get(player.getNickname()).getPersonalBoard().addCard(card);
                    //tower cell no more available
                    cell.setEmpty(false);
                }else{
                    //comunica che il giocatore non ha risorse a sufficienza per comprare la carta
                }
            }else{
                //comunica che il famigliare ha un valore inferiore al dado della cella
            }
        }else {
            //comunica che la cella non è libera
        }
    }
}