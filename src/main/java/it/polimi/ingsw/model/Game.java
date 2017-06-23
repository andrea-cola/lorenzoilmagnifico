package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.server.ServerPlayer;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable{

    /**
     * Mainboard reference.
     */
    private MainBoard mainBoard;

    /**
     * Dices.
     */
    private Dice dices;

    /**
     * Map of all players. Each player is identified by its username (String).
     */
    private Map<String, Player> players;

    /**
     * Class constructor
     */
    public Game(MainBoard mainBoard, List<ServerPlayer> players){
        buildMainBoard(mainBoard);
        closeAreas(players.size());
        this.dices = new Dice();
        this.players = new LinkedHashMap<>();
    }

    /**
     * This method build a new main board object.
     * @param mainBoardConfiguration configuration.
     */
    private void buildMainBoard(MainBoard mainBoardConfiguration){
        this.mainBoard = new MainBoard(mainBoardConfiguration);
    }

    /**
     * Close areas following game rules.
     * @param numberOfPlayers in the room.
     */
    private void closeAreas(int numberOfPlayers){
        if(numberOfPlayers < 3) {
            this.mainBoard.getHarvestExtended().setNotAccessible();
            this.mainBoard.getProductionExtended().setNotAccessible();
        }
        if(numberOfPlayers < 4){
            for(int i = 0; i < this.mainBoard.getMarket().getMarketCells().length; i++){
                if(i > 1)
                    this.mainBoard.getMarket().getMarketCell(i).setNotAccessible();
            }
        }
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

    public String[] getPlayersUsername(){
        String[] usernames = new String[players.size()];
        Iterator it = players.entrySet().iterator();
        int i = 0;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            usernames[i] = (String)pair.getKey();
            i++;
        }
        return usernames;
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
    public void pickupDevelopmentCardFromTower(Player player, FamilyMemberColor familyMemberColor, int indexTower, int indexCell, InformationCallback informationCallback) throws GameException{
        Tower tower = this.mainBoard.getTower(indexTower);
        TowerCell cell = tower.getTowerCell(indexCell);

        //check if the cell is empty
        if (cell.getPlayerNicknameInTheCell() == null){
            //check if the family member has not been already used and if the player has not already placed a family member inside the tower
            tower.familyMemberCanBePlaced(player, familyMemberColor);

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
                this.players.get(player.getUsername()).getPersonalBoard().addCard(card);

                //get the tower cell immediate effect
                cell.getTowerCellImmediateEffect().runEffect(player, informationCallback);

                //set the family member as used
                player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);

                //set the cell as used by a particular player
                cell.setPlayerNicknameInTheCell(player.getUsername());
            } catch (GameException e){
                //if the player has no more resources to buy the card, it gets back his money in case of tower already occupied
                if (!tower.isFree()){
                    player.getPersonalBoard().getValuables().increase(ResourceType.COIN, 3);
                }
            }
        } else {
            throw new GameException();
        }
    }


    /**
     * This method manages the simple harvest action
     * @param player
     * @param familyMemberColor
     */
    public void placeFamilyMemberInsideHarvestSimpleSpace(Player player, FamilyMemberColor familyMemberColor, InformationCallback informationCallback){
        ActionSpace actionSpace = this.mainBoard.getHarvest();

        //check if the action space is empty
        if (actionSpace.getEmpty()){
            try {
                ///check if familyMember is eligible to be placed inside the harvest zone
                actionSpace.familyMemberCanBePlaced(player, familyMemberColor);

                //permanent effect
                for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getTerritoryCards()){
                    card.getPermanentEffect().runEffect(player, informationCallback);
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
    public void placeFamilyMemberInsideCouncilPalace(Player player, FamilyMemberColor familyMemberColor, InformationCallback informationCallback) throws GameException{
        CouncilPalace councilPalace = this.mainBoard.getCouncilPalace();

        //check if familyMember is eligible to be placed inside the council palace
        councilPalace.familyMemberCanBePlaced(player, familyMemberColor);
        councilPalace.fifoAddPlayer(player);
        if(councilPalace.getImmediateEffect() == null){
            System.out.println("Nullo!");
        }
        councilPalace.getImmediateEffect().runEffect(player, informationCallback);
    }


    /**
     * This method manages the market events
     */
    public void placeFamilyMemberInsideMarket(Player player, FamilyMemberColor familyMemberColor, int indexMarket, InformationCallback informationCallback) throws GameException{
        Market market = this.mainBoard.getMarket();
        try {
            MarketCell cell = market.getMarketCell(indexMarket);
            if(cell.isEmpty()){
                try {
                    //check if familyMember is eligible to be placed inside the market
                    cell.familyMemberCanBePlaced(player, familyMemberColor);
                    cell.getMarketCellImmediateEffect().runEffect(player, informationCallback);
                }catch (GameException e){
                    System.out.print(e.getError());
                }
            }else{
                System.out.print("This market cell is occupied");
            }
        } catch (IndexOutOfBoundsException e){
            throw new GameException();
        }

    }

}