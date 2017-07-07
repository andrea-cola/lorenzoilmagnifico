package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.EffectHarvestProductionExchange;
import it.polimi.ingsw.model.effects.EffectNoBonus;
import it.polimi.ingsw.model.effects.LECesareBorgia;
import it.polimi.ingsw.model.effects.LEDiceBonus;
import it.polimi.ingsw.model.effects.LEDiceValueSet;
import it.polimi.ingsw.model.effects.LEFamilyMemberBonus;
import it.polimi.ingsw.model.effects.LEHarvestProductionSimple;
import it.polimi.ingsw.model.effects.LENeutralBonus;
import it.polimi.ingsw.model.effects.LEPicoDellaMirandola;
import it.polimi.ingsw.model.effects.LESimple;
import it.polimi.ingsw.server.ServerPlayer;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable{

    /**
     * Main board reference.
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
     * Game age.
     */
    private int age;

    /**
     * Game turn.
     */
    private int turn;

    /**
     * Class constructor
     */
    public Game(MainBoard mainBoard, List<ServerPlayer> players){
        buildMainBoard(mainBoard);
        closeAreas(players.size());
        this.dices = new Dice();
        this.players = new LinkedHashMap<>();
        this.age = 1;
        this.turn = 1;
    }

    public int getAge(){
        return age;
    }

    public int getTurn(){
        return turn;
    }

    public void setTurn(int turn){
        this.turn = turn;
    }

    public void setAge(int age){
        this.age = age;
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
    public void pickupDevelopmentCardFromTower(Player player, FamilyMemberColor familyMemberColor, int servants, int indexTower, int indexCell, InformationCallback informationCallback) throws GameException{
        Tower tower = this.mainBoard.getTower(indexTower);
        TowerCell cell = tower.getTowerCell(indexCell);

        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        //get family member and update his value with servants number provided
        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        LeaderCard leaderCardBrunelleschi = player.getPersonalBoard().getLeaderCardWithName("Filippo Brunelleschi");

        //check if the cell is empty or the player has the leader effect to place family member inside already occupied action spaces
        if (cell.getPlayerNicknameInTheCell() == null || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace()){

            //check if the family member has not been already used and if the player has not already placed a family member inside the tower
            tower.familyMemberCanBePlaced(player, familyMemberColor);

            //change the value of the family member based on servants provided
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);

            try {
                //check if familyMember's value is enough to be placed inside the towerCell
                cell.familyMemberCanBePlaced(player, familyMemberColor);

                //if the tower is already occupied by someone, the player has to pay the coins more
                if (!tower.isFree() && (leaderCardBrunelleschi == null || !leaderCardBrunelleschi.getLeaderEffectActive())) {
                    if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.COIN) >= 3)
                        player.getPersonalBoard().getValuables().decrease(ResourceType.COIN, 3);
                    else
                        throw new GameException(GameErrorType.TOWER_COST);
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
                if (!tower.isFree() && (leaderCardBrunelleschi == null || !leaderCardBrunelleschi.getLeaderEffectActive()) && !e.getError().equals(GameErrorType.TOWER_COST))
                    player.getPersonalBoard().getValuables().increase(ResourceType.COIN, 3);
                throw e;
            }
        } else {
            throw new GameException(GameErrorType.TOWER_CELL_BUSY);
        }
    }

    private void payValuablesToGetDevelopmentCard(Player player, DevelopmentCard developmentCard, InformationCallback informationCallback) throws GameException{
        //leader effect gives you a discount
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCardWithName("Pico della Mirandola");
        int devCardCoinsCost = developmentCard.getCost().getResources().get(ResourceType.COIN);
        if (leaderCard != null && leaderCard.getLeaderEffectActive()) {
            //decrease card price
            if (devCardCoinsCost >= 3)
                developmentCard.getCost().decrease(ResourceType.COIN, ((LEPicoDellaMirandola)leaderCard.getEffect()).getMoneyDiscount());
            else
                developmentCard.getCost().decrease(ResourceType.COIN, devCardCoinsCost);
        }
        // pay card normally
        developmentCard.payCost(player, informationCallback);
    }

    /**
     * This method calls the simple harvest action
     * @param player
     * @param familyMemberColor
     * @param servants
     * @param informationCallback
     * @throws GameException
     */
    public void placeFamilyMemberInsideHarvestSimpleSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionSimple(player, this.mainBoard.getHarvest(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method calls the simple production action
     * @param player
     * @param familyMemberColor
     * @param servants
     * @param informationCallback
     * @throws GameException
     */
    public void placeFamilyMemberInsideProductionSimpleSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionSimple(player, this.mainBoard.getProduction(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method manages the harvest/production simple behavior
     * @param player
     * @param actionSpace
     * @param familyMemberColor
     * @param servants
     * @param informationCallback
     * @throws GameException
     */
    private void performHarvestProductionSimple(Player player, ActionSpace actionSpace, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the action space is empty or the player has the leader effect to place family member inside already occupied action spaces
        if (actionSpace.isEmpty() || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                ///check if familyMember is eligible to be placed inside the harvest zone
                this.mainBoard.getProductionExtended().checkAccessibility(player, familyMemberColor);
                actionSpace.familyMemberCanBePlaced(player, familyMemberColor);

                //run permanent effect
                if (actionSpace.getActionSpaceType().equals(ActionType.HARVEST)){
                    player.getPersonalBoard().getPersonalBoardTile().getHarvestEffect().runEffect(player, informationCallback);
                    performHarvest(player, informationCallback);
                }

                if (actionSpace.getActionSpaceType().equals(ActionType.PRODUCTION)){
                    player.getPersonalBoard().getPersonalBoardTile().getProductionEffect().runEffect(player, informationCallback);
                    performProduction(player, informationCallback);
                }

                //action space is no more available
                actionSpace.setEmpty(false);
            } catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
                throw e;
            }
        }else
            throw new GameException(GameErrorType.ACTIONS_SPACE_NOT_ACCESSIBLE);
    }

    /**
     * This method calls the extended harvest area
     * @param player
     * @param familyMemberColor
     * @param servants
     * @param informationCallback
     * @throws GameException
     */
    public void placeFamilyMemberInsideHarvestExtendedSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionExtended(player, this.mainBoard.getHarvestExtended(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method calls the extended production area
     * @param player
     * @param familyMemberColor
     * @param servants
     * @param informationCallback
     * @throws GameException
     */
    public void placeFamilyMemberInsideProductionExtendedSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionExtended(player, this.mainBoard.getProductionExtended(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method manages the harvest/production extended behavior
     * @param player
     * @param actionSpaceExtended
     * @param familyMemberColor
     * @param servants
     * @param informationCallback
     * @throws GameException
     */
    private void performHarvestProductionExtended(Player player, ActionSpaceExtended actionSpaceExtended, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the action space is empty or the player has the leader effect to place family member inside already occupied action spaces
        if (actionSpaceExtended.isAccessible() || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                ///check if familyMember is eligible to be placed inside the harvest zone
                this.mainBoard.getProduction().checkAccessibility(player, familyMemberColor);
                actionSpaceExtended.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);

                player.getPersonalBoard().getPersonalBoardTile().getProductionEffect().runEffect(player, informationCallback);

                //run permanent effect
                if (actionSpaceExtended.getActionSpaceType().equals(ActionType.HARVEST)){
                    performHarvest(player, informationCallback);
                }

                if (actionSpaceExtended.getActionSpaceType().equals(ActionType.PRODUCTION)){
                    performProduction(player, informationCallback);
                }

            } catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
                throw e;
            }
        }else
            throw new GameException(GameErrorType.ACTIONS_SPACE_NOT_ACCESSIBLE);
    }

    /**
     * Method that manages harvest behavior
     * @param player
     * @param informationCallback
     */
    private void performHarvest(Player player, InformationCallback informationCallback){
        for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getCards(DevelopmentCardColor.GREEN)) {
            card.getPermanentEffect().runEffect(player, informationCallback);
        }
    }

    /**
     * Method that manages the production behavior
     * @param player
     * @param informationCallback
     */
    private void performProduction(Player player, InformationCallback informationCallback){
        for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getCards(DevelopmentCardColor.YELLOW)) {
            if (card.getPermanentEffect() instanceof EffectHarvestProductionExchange)
                ((EffectHarvestProductionExchange) card.getPermanentEffect()).runEffect(card, player, informationCallback);
            else
                card.getPermanentEffect().runEffect(player, informationCallback);
        }
    }

    /**
     * This method manages the council palace events
     */
    public void placeFamilyMemberInsideCouncilPalace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        CouncilPalace councilPalace = this.mainBoard.getCouncilPalace();

        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();
        updateFamilyMemberValue(player, familyMemberColor, servantsValue);
        try {
            //check if familyMember is eligible to be placed inside the council palace
            councilPalace.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);
            councilPalace.fifoAddPlayer(player);
            councilPalace.getImmediateEffect().runEffect(player, informationCallback);
        }catch (GameException e){
            restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
            throw e;
        }
    }

    /**
     * This method manages the market events
     */
    public void placeFamilyMemberInsideMarket(Player player, FamilyMemberColor familyMemberColor, int servants, int indexMarket, InformationCallback informationCallback) throws GameException{
        Market market = this.mainBoard.getMarket();
        MarketCell cell = market.getMarketCell(indexMarket);

        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the cell is empty or the player has the leader effect to place family member inside already occupied action spaces
        if( (cell.isEmpty() || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace() ) &&
                player.getPersonalBoard().getExcommunicationValues().getMarketIsAvailable()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                //check if familyMember is eligible to be placed inside the market
                cell.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);

                cell.getMarketCellImmediateEffect().runEffect(player, informationCallback);
            }catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);
                throw e;
            }
        }else{
            throw new GameException(GameErrorType.MARKET_CELL_BUSY);
        }
    }

    /**
     * This method changes in active the state of a Leader card if the player has the right requisites and runs the immediate effects
     */
    public void activateLeaderCard(Player player, int leaderCardAtIndex, int servants, InformationCallback informationCallback) throws GameException {
        //get the leader card
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCards().get(leaderCardAtIndex);

        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        int servantsValue = servants / player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        //check if the player has the requisites to activate the leader card
        leaderCard.checkRequisites(player);

        //if the leader card has an immediate effect, run it immediately when you activate the card
        if (leaderCard.getEffect() instanceof LESimple ||
                leaderCard.getEffect() instanceof LEDiceBonus ||
                leaderCard.getEffect() instanceof LEDiceValueSet ||
                leaderCard.getEffect() instanceof LENeutralBonus ||
                leaderCard.getEffect() instanceof LECesareBorgia ||
                leaderCard.getEffect() instanceof LEFamilyMemberBonus) {
            //run effect
            leaderCard.getEffect().runEffect(player, informationCallback);
        } else if (leaderCard.getEffect() instanceof LEHarvestProductionSimple) {
            //get the last family member used and change its value
            List<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
            FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);
            player.getPersonalBoard().getFamilyMember().setFamilyMemberValue(familyMemberColor, servantsValue);

            //run effect
            leaderCard.getEffect().runEffect(player, informationCallback);
        }
    }

    public void discardLeaderCard(Player player, int leaderCardAtIndex, InformationCallback informationCallback){
        player.getPersonalBoard().getLeaderCards().remove(leaderCardAtIndex);
        CouncilPrivilege councilPrivilege = new CouncilPrivilege(1);
        councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
    }

    /**
     * This method updates the family member value with servants and decrease the number of servants in resources
     * @param player
     * @param familyMemberColor
     * @param servantsValue
     */
    private void updateFamilyMemberValue(Player player, FamilyMemberColor familyMemberColor, int servantsValue) throws GameException{
        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) >= servantsValue) {
            player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(familyMemberColor, servantsValue);
            player.getPersonalBoard().getValuables().decrease(ResourceType.SERVANT, servantsValue);
        } else
            throw new GameException(GameErrorType.NOT_ENOUGH_SERVANT_TO_DECREASE);
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

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/////GAME STATUS/////\n");
        stringBuilder.append("[Dices]\n");
        Iterator it = dices.getValues().entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            stringBuilder.append(pair.getKey() + " " + pair.getValue() + " ");
        }
        stringBuilder.append("\n");
        stringBuilder.append(mainBoard.toString());
        return stringBuilder.toString();
    }
}