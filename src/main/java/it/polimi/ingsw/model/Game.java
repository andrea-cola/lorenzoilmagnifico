package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.*;
import it.polimi.ingsw.server.ServerPlayer;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.exceptions.GameErrorType.FAMILY_MEMBER_DICE_VALUE;

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

    //TODO questo dovrebbe andare in Game manager
    /**
     * This method build a new main board object.
     * @param mainBoardConfiguration configuration.
     */
    private void buildMainBoard(MainBoard mainBoardConfiguration){
        this.mainBoard = new MainBoard(mainBoardConfiguration);
    }

    //TODO questo dovrebbe andare in Game manager
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
                    this.mainBoard.getMarket().getMarketCell(i).setNotEmpty();
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
    public void pickupDevelopmentCardFromTower(Player player, FamilyMemberColor familyMemberColor, int servants, int indexTower, int indexCell, InformationCallback informationCallback) throws GameException{
        Tower tower = this.mainBoard.getTower(indexTower);
        TowerCell cell = tower.getTowerCell(indexCell);

        //get family member and update his value with servants number provided
        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        LeaderCard leaderCardBrunelleschi = player.getPersonalBoard().getLeaderCardWithName("Filippo Brunelleschi");

        //check if the cell is empty
        if (cell.getPlayerNicknameInTheCell() == null){

            //check if the family member has not been already used and if the player has not already placed a family member inside the tower
            tower.familyMemberCanBePlaced(player, familyMemberColor);

            //change the value of the family member based on servants provided
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);

            try {
                //check if familyMember's value is enough to be placed inside the towerCell
                cell.familyMemberCanBePlaced(player, familyMemberColor);

                //if the tower is already occupied by someone, the player has to pay the coins more
                if (!tower.isFree() && (leaderCardBrunelleschi == null || !leaderCardBrunelleschi.getLeaderEffectActive())){
                    player.getPersonalBoard().getValuables().decrease(ResourceType.COIN, 3);
                }

                //check if the user has resources enough to buy the card
                cell.developmentCardCanBeBuyed(player, informationCallback);

                //add the card to the player's personal board
                DevelopmentCard card = cell.getDevelopmentCard();
                this.payValuablesToGetDevelopmentCard(player, card, informationCallback);

                player.getPersonalBoard().addCard(card);
                if(card.getImmediateEffect() != null)
                    card.getImmediateEffect().runEffect(player,informationCallback);
                if(card.getColor().equals(DevelopmentCardColor.BLUE) && card.getPermanentEffect() != null)
                    card.getPermanentEffect().runEffect(player, informationCallback);

                //get the tower cell immediate effect
                if(cell.getTowerCellImmediateEffect() != null && canRunTowerImmediateEffects(player, indexCell))
                    cell.getTowerCellImmediateEffect().runEffect(player, informationCallback);

                //set the family member as used
                player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);

                //set the cell as used by a particular player
                cell.setPlayerNicknameInTheCell(player.getUsername());
            } catch (GameException e){
                //get family member and update his value with servants number provided
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);

                //if the player has no more resources to buy the card, it gets back his money in case of tower already occupied
                if (!tower.isFree() && (leaderCardBrunelleschi == null || !leaderCardBrunelleschi.getLeaderEffectActive())){
                    player.getPersonalBoard().getValuables().increase(ResourceType.COIN, 3);
                }
            }
        } else {
            throw new GameException();
        }
    }

    private void payValuablesToGetDevelopmentCard(Player player, DevelopmentCard developmentCard, InformationCallback informationCallback){
        //leader effect gives you a discount
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCardWithName("Pico della Mirandola");
        int devCardCoinsCost = developmentCard.getCost().getResources().get(ResourceType.COIN);
        if (leaderCard != null && leaderCard.getLeaderEffectActive()) {
            //decrease card price
            if (devCardCoinsCost >= 3)
                developmentCard.getCost().decrease(ResourceType.COIN, 3);
            else
                developmentCard.getCost().decrease(ResourceType.COIN, devCardCoinsCost);
        }
        // pay card normally
        developmentCard.payCost(player, informationCallback);
    }



    /**
     * This method manages the simple harvest action
     * @param player
     * @param familyMemberColor
     */
    public void placeFamilyMemberInsideHarvestSimpleSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        ActionSpace actionSpace = this.mainBoard.getHarvest();

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the action space is empty
        if (actionSpace.isEmpty()){
            try {
                updateFamilyMemberValue(player, familyMemberColor, servantsValue);

                //check if familyMember is eligible to be placed inside the harvest zone
                actionSpace.familyMemberCanBePlaced(player, familyMemberColor);

                //personal board tile effect
                player.getPersonalBoard().getPersonalBoardTile().getHarvestEffect().runEffect(player, informationCallback);

                //permanent effect
                for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getCards(DevelopmentCardColor.GREEN)) {
                    card.getPermanentEffect().runEffect(player, informationCallback);
                }

                //action space is no more available
                actionSpace.setEmpty(false);
            }catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
                throw e;
            }
        }else {
            System.out.print("Action space has been already used");
        }
    }

    public void placeFamilyMemberInsideHarvestExtendedSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        ActionSpaceExtended actionSpaceExtended = this.mainBoard.getHarvestExtended();

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the action space is empty
        if (actionSpaceExtended.isAccessible()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                ///check if familyMember is eligible to be placed inside the harvest zone
                this.mainBoard.getHarvest().checkAccessibility(player, familyMemberColor);
                actionSpaceExtended.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);

                // run personal board tile effect
                player.getPersonalBoard().getPersonalBoardTile().getHarvestEffect().runEffect(player, informationCallback);

                //run permanent effect
                for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getCards(DevelopmentCardColor.GREEN)) {
                    if (card.getPermanentEffect() instanceof EffectHarvestProductionExchange)
                        ((EffectHarvestProductionExchange) card.getPermanentEffect()).runEffect(card, player, informationCallback);
                    card.getPermanentEffect().runEffect(player, informationCallback);
                }
            } catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
            }
        }else
            throw new GameException();
    }

    public void placeFamilyMemberInsideProductionSimpleSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        ActionSpace actionSpace = this.mainBoard.getProduction();

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the action space is empty
        if (actionSpace.isEmpty()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                ///check if familyMember is eligible to be placed inside the harvest zone
                this.mainBoard.getProductionExtended().checkAccessibility(player, familyMemberColor);
                actionSpace.familyMemberCanBePlaced(player, familyMemberColor);

                player.getPersonalBoard().getPersonalBoardTile().getProductionEffect().runEffect(player, informationCallback);

                //run permanent effect
                for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getCards(DevelopmentCardColor.YELLOW)) {
                    if(card.getPermanentEffect() instanceof EffectHarvestProductionExchange)
                        ((EffectHarvestProductionExchange) card.getPermanentEffect()).runEffect(card, player, informationCallback);
                    else
                        card.getPermanentEffect().runEffect(player, informationCallback);
                }

                //action space is no more available
                actionSpace.setEmpty(false);
            } catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
            }
        }else
            throw new GameException();
    }

    public void placeFamilyMemberInsideProductionExtendedSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        ActionSpaceExtended actionSpaceExtended = this.mainBoard.getProductionExtended();

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the action space is empty
        if (actionSpaceExtended.isAccessible()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                ///check if familyMember is eligible to be placed inside the harvest zone
                this.mainBoard.getProduction().checkAccessibility(player, familyMemberColor);
                actionSpaceExtended.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);

                player.getPersonalBoard().getPersonalBoardTile().getProductionEffect().runEffect(player, informationCallback);

                //run permanent effect
                for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getCards(DevelopmentCardColor.YELLOW)) {
                    if(card.getPermanentEffect() instanceof EffectHarvestProductionExchange)
                        ((EffectHarvestProductionExchange) card.getPermanentEffect()).runEffect(card, player, informationCallback);
                    else
                        card.getPermanentEffect().runEffect(player, informationCallback);
                }
            } catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
            }
        }else
            throw new GameException();
    }


    /**
     * This method manages the council palace events
     */
    public void placeFamilyMemberInsideCouncilPalace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        CouncilPalace councilPalace = this.mainBoard.getCouncilPalace();

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();
        updateFamilyMemberValue(player, familyMemberColor, servantsValue);
        try {
            //check if familyMember is eligible to be placed inside the council palace
            councilPalace.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);

            councilPalace.fifoAddPlayer(player);
            councilPalace.getImmediateEffect().runEffect(player, informationCallback);
        }catch (GameException e){
            restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
            System.out.print(e.getError());
        }
    }


    /**
     * This method manages the market events
     */
    public void placeFamilyMemberInsideMarket(Player player, FamilyMemberColor familyMemberColor, int servants, int indexMarket, InformationCallback informationCallback) throws GameException{
        Market market = this.mainBoard.getMarket();
        MarketCell cell = market.getMarketCell(indexMarket);

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();
        if(cell.isEmpty()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                //check if familyMember is eligible to be placed inside the market
                cell.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);

                cell.getMarketCellImmediateEffect().runEffect(player, informationCallback);
            }catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
            }
        }else{
            System.out.print("This market cell is occupied");
        }
    }


    /**
     * This method changes in active the state of a Leader card if the player has the right requisites and runs the immediate effects
     */
    public void activateLeaderCard(Player player, int leaderCardAtIndex, InformationCallback informationCallback) throws GameException{
        //get the leader card
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCards().get(leaderCardAtIndex);

        //check if the player has the requisites to activate the leader card
        leaderCard.checkRequisites(player);
        //if the leader card has an immediate effect, run it immediately when you activate the card
        if (leaderCard.getEffect().getClass().equals(LESimple.class) ||
                leaderCard.getEffect().getClass().equals(LEDiceBonus.class) ||
                leaderCard.getEffect().getClass().equals(LEDiceValueSet.class) ||
                leaderCard.getEffect().getClass().equals(LENeutralBonus.class) ||
                leaderCard.getEffect().getClass().equals(LECesareBorgia.class) ||
                leaderCard.getEffect().getClass().equals(LEHarvestProductionSimple.class)){
            leaderCard.getEffect().runEffect(player, informationCallback);
        }
    }

    public void discardLeaderCard(Player player, int leaderCardAtIndex, InformationCallback informationCallback){
        player.getPersonalBoard().getLeaderCards().remove(leaderCardAtIndex);
        CouncilPrivilege councilPrivilege = new CouncilPrivilege(1);
        councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
    }

    private void updateFamilyMemberValue(Player player, FamilyMemberColor familyMemberColor, int servantsValue){
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(familyMemberColor, servantsValue);
        player.getPersonalBoard().getValuables().decrease(ResourceType.SERVANT, servantsValue);
    }

    private void restoreFamilyMemberValue(Player player, FamilyMemberColor familyMemberColor, int servantsValue){
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(familyMemberColor, servantsValue);
        player.getPersonalBoard().getValuables().increase(ResourceType.SERVANT, servantsValue);
    }

    private boolean canRunTowerImmediateEffects(Player player, int floor){
        List<DevelopmentCard> developmentCards = player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE);
        for(DevelopmentCard developmentCard : developmentCards)
            if (developmentCard.getPermanentEffect() instanceof EffectNoBonus){
                EffectNoBonus effectNoBonus = (EffectNoBonus) developmentCard.getPermanentEffect();
                for (Integer towerFloor : effectNoBonus.getFloors())
                    if (towerFloor == floor)
                        return true;

            }
        return false;
    }
}