package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;

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
    public Game(MainBoard mainBoard){
        this.mainBoard = mainBoard;
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
     * This method gets a DevelopmentCard from TowerCell and adds it to the player's PersonalBoard
     * @param player
     * @param indexTower
     * @param indexCell
     * @return
     */
    public void pickupDevelopmentCardFromTower(Player player, FamilyMemberColor familyMemberColor, int indexTower, int indexCell){
        Tower tower = this.mainBoard.getTower(indexTower);
        TowerCell cell = tower.getTowerCell(indexCell);

        //check if the cell is empty
        if (cell.getPlayerNicknameInTheCell() == null){
            try {
                //check if the family member has not been already used and if the player has not already placed a family member inside the tower
                tower.familyMemberCanBePlaced(player, familyMemberColor);

                try {
                    //check if familyMember's value is enough to be placed inside the towerCell
                    cell.familyMemberCanBePlaced(player, familyMemberColor);

                    try {
                        //if the tower is already occupied by someone, the player has to pay the coins more
                        if (!tower.isFree()){
                            player.getPersonalBoard().getValuables().decrease(ResourceType.COIN, 3);
                        }

                        //check if the user has resources enough to buy the card
                        cell.developmentCardCanBeBuyedBy(player);

                        //add the card to the player's personal board
                        DevelopmentCard card = cell.getDevelopmentCard();
                        this.players.get(player.getNickname()).getPersonalBoard().addCard(card);

                        //get the tower cell immediate effect
                        cell.getTowerCellImmediateEffect().runEffect(player);

                        //set the family member as used
                        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);

                        //set the cell as used by a particular player
                        cell.setPlayerNicknameInTheCell(player.getNickname());
                    }catch (GameException e){
                        //if the player has no more resources to buy the card, it gets back his money in case of tower already occupied
                        if (!tower.isFree()){
                            player.getPersonalBoard().getValuables().increase(ResourceType.COIN, 3);
                        }

                        System.out.print(e.getError());
                    }
                }catch (GameException e){
                    System.out.print(e.getError());
                }

            }catch (GameException e){
                System.out.print(e.getError());
            }
        }else {
            System.out.print("Cell has been already used");
        }
    }


    /**
     * This method manages the simple harvest action
     * @param player
     * @param familyMemberColor
     */
    public void placeFamilyMemberInsideHarvestSimpleSpace(Player player, FamilyMemberColor familyMemberColor){
        ActionSpace actionSpace = this.mainBoard.getHarvest();

        //check if the action space is empty
        if (actionSpace.getEmpty()){
            try {
                ///check if familyMember is eligible to be placed inside the harvest zone
                actionSpace.familyMemberCanBePlaced(player, familyMemberColor);

                //permanent effect
                for (DevelopmentCard card : this.players.get(player.getNickname()).getPersonalBoard().getTerritoryCards()){
                    card.getPermanentEffect().runEffect(player);
                }

                //personal board tile

                //action space is no more available
                actionSpace.setEmpty(false);
            }catch (GameException e){
                System.out.print(e.getError());
            }
        }else {
            System.out.print("Action space has been already used");
        }
    }

    /**
     * This method manages the council palace events
     */
    public void placeFamilyMemberInsideCouncilPalace(Player player, FamilyMemberColor familyMemberColor){
        CouncilPalace councilPalace = this.mainBoard.getCouncilPalace();

        try {
            //check if familyMember is eligible to be placed inside the council palace
            councilPalace.familyMemberCanBePlaced(player, familyMemberColor);

            councilPalace.fifoAddPlayer(player);
            councilPalace.getImmediateEffect().runEffect(player);
        }catch (GameException e){
            System.out.print(e.getError());
        }
    }


    /**
     * This method manages the market events
     */
    public void placeFamilyMemberInsideMarket(Player player, FamilyMemberColor familyMemberColor, int indexMarket){
        Market market = this.mainBoard.getMarket();
        MarketCell cell = market.getMarketCell(indexMarket);

        if(cell.getEmpty()){
            try {
                //check if familyMember is eligible to be placed inside the market
                cell.familyMemberCanBePlaced(player, familyMemberColor);

                cell.getImmediateEffect().runEffect(player);
            }catch (GameException e){
                System.out.print(e.getError());
            }
        }else{
            System.out.print("This market cell is occupied");
        }
    }

}