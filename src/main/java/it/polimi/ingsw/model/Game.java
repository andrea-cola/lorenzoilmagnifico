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

/**
 * This class manages the logic of the game
 */
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
     * Number of move
     */
    private int move;

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

    /**
     * Returns the current age
     * @return the current age
     */
    public int getAge(){
        return age;
    }

    /**
     * Returns the current turn
     * @return the current turn
     */
    public int getTurn(){
        return turn;
    }

    /**
     * Returns the current number of the move
     * @return the current move
     */
    public int getMove(){
        return this.move;
    }

    /**
     * Sets the number of the turn
     * @param turn the current turn
     */
    public void setTurn(int turn){
        this.turn = turn;
    }

    /**
     * Sets the number of the age
     * @param age the current age
     */
    public void setAge(int age){
        this.age = age;
    }

    /**
     * Sets the number of the move
     * @param move the current move
     */
    public void setMove(int move){
        this.move = move;
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
     * @return the main board
     */
    public MainBoard getMainBoard(){
        return this.mainBoard;
    }

    /**
     * Get the dices
     * @return the dices
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
     * Returns the players' username
     * @return usernames
     */
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
     * @return the players
     */
    public Map<String, Player> getPlayersMap(){
        return this.players;
    }

    /**
     * This method gets a DevelopmentCard from TowerCell and adds it to the player's PersonalBoard
     * @param player the player that is performing the action
     * @param indexTower the index of the tower
     * @param indexCell the index of the cell
     * @return
     */
    public void pickupDevelopmentCardFromTower(Player player, FamilyMemberColor familyMemberColor, int servants, int indexTower, int indexCell, InformationCallback informationCallback) throws GameException{
        Tower tower = this.mainBoard.getTower(indexTower);
        TowerCell cell = tower.getTowerCell(indexCell);

        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        LeaderCard leaderCardBrunelleschi = player.getPersonalBoard().getLeaderCardWithName("Filippo Brunelleschi");

        if (cell.getPlayerNicknameInTheCell() == null || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace()){

            tower.familyMemberCanBePlaced(player, familyMemberColor);

            updateFamilyMemberValue(player, familyMemberColor, servantsValue);

            try {
                cell.familyMemberCanBePlaced(player, familyMemberColor);

                if (!tower.isFree() && (leaderCardBrunelleschi == null || !leaderCardBrunelleschi.getLeaderEffectActive())) {
                    if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.COIN) >= 3)
                        player.getPersonalBoard().getValuables().decrease(ResourceType.COIN, 3);
                    else
                        throw new GameException(GameErrorType.TOWER_COST);
                }

                cell.developmentCardCanBeBought(player, informationCallback);

                DevelopmentCard card = cell.getDevelopmentCard();
                this.payValuablesToGetDevelopmentCard(player, card, informationCallback);

                player.getPersonalBoard().addCard(card);
                if(card.getImmediateEffect() != null)
                    card.getImmediateEffect().runEffect(player,informationCallback);
                if(card.getColor().equals(DevelopmentCardColor.BLUE) && card.getPermanentEffect() != null)
                    card.getPermanentEffect().runEffect(player, informationCallback);

                if(cell.getTowerCellImmediateEffect() != null && canRunTowerImmediateEffects(player, indexCell))
                    cell.getTowerCellImmediateEffect().runEffect(player, informationCallback);

                player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);

                cell.setPlayerNicknameInTheCell(player.getUsername());
            } catch (GameException e){
                restoreFamilyMemberValue(player, familyMemberColor, servantsValue);

                if (!tower.isFree() && (leaderCardBrunelleschi == null || !leaderCardBrunelleschi.getLeaderEffectActive()) && !e.getError().equals(GameErrorType.TOWER_COST))
                    player.getPersonalBoard().getValuables().increase(ResourceType.COIN, 3);
                throw e;
            }
        } else {
            throw new GameException(GameErrorType.TOWER_CELL_BUSY);
        }
    }

    /**
     * This method is used to pay the cost of development cards
     * @param player the player that is performing the action
     * @param developmentCard the development card the player want to pay
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     */
    private void payValuablesToGetDevelopmentCard(Player player, DevelopmentCard developmentCard, InformationCallback informationCallback){
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCardWithName("Pico della Mirandola");
        int devCardCoinsCost = developmentCard.getCost().getResources().get(ResourceType.COIN);
        if (leaderCard != null && leaderCard.getLeaderEffectActive()) {
            if (devCardCoinsCost >= 3)
                developmentCard.getCost().decrease(ResourceType.COIN, ((LEPicoDellaMirandola)leaderCard.getEffect()).getMoneyDiscount());
            else
                developmentCard.getCost().decrease(ResourceType.COIN, devCardCoinsCost);
        }
        developmentCard.payCost(player, informationCallback);
    }

    /**
     * This method calls the simple harvest action
     * @param player the player that is performing the action
     * @param familyMemberColor
     * @param servants
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     * @throws GameException
     */
    public void placeFamilyMemberInsideHarvestSimpleSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionSimple(player, this.mainBoard.getHarvest(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method calls the simple production action
     * @param player the player that is performing the action
     * @param familyMemberColor
     * @param servants
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     * @throws GameException
     */
    public void placeFamilyMemberInsideProductionSimpleSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionSimple(player, this.mainBoard.getProduction(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method manages the harvest/production simple behavior
     * @param player the player that is performing the action
     * @param actionSpace
     * @param familyMemberColor
     * @param servants
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     * @throws GameException
     */
    private void performHarvestProductionSimple(Player player, ActionSpace actionSpace, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        if (actionSpace.isEmpty() || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                this.mainBoard.getProductionExtended().checkAccessibility(player, familyMemberColor);
                actionSpace.familyMemberCanBePlaced(player, familyMemberColor);

                if (actionSpace.getActionSpaceType().equals(ActionType.HARVEST)){
                    player.getPersonalBoard().getPersonalBoardTile().getHarvestEffect().runEffect(player, informationCallback);
                    performHarvest(player, informationCallback);
                }

                if (actionSpace.getActionSpaceType().equals(ActionType.PRODUCTION)){
                    player.getPersonalBoard().getPersonalBoardTile().getProductionEffect().runEffect(player, informationCallback);
                    performProduction(player, informationCallback);
                }

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
     * @param player the player that is performing the action
     * @param familyMemberColor
     * @param servants
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     * @throws GameException
     */
    public void placeFamilyMemberInsideHarvestExtendedSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionExtended(player, this.mainBoard.getHarvestExtended(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method calls the extended production area
     * @param player the player that is performing the action
     * @param familyMemberColor
     * @param servants
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     * @throws GameException
     */
    public void placeFamilyMemberInsideProductionExtendedSpace(Player player, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        performHarvestProductionExtended(player, this.mainBoard.getProductionExtended(), familyMemberColor, servants, informationCallback);
    }

    /**
     * This method manages the harvest/production extended behavior
     * @param player the player that is performing the action
     * @param actionSpaceExtended
     * @param familyMemberColor
     * @param servants
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     * @throws GameException
     */
    private void performHarvestProductionExtended(Player player, ActionSpaceExtended actionSpaceExtended, FamilyMemberColor familyMemberColor, int servants, InformationCallback informationCallback) throws GameException{
        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        int servantsValue = servants/player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        if (actionSpaceExtended.isAccessible() || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
                this.mainBoard.getProduction().checkAccessibility(player, familyMemberColor);
                actionSpaceExtended.familyMemberCanBePlaced(player, familyMemberColor, servantsValue);

                player.getPersonalBoard().getPersonalBoardTile().getProductionEffect().runEffect(player, informationCallback);

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
     * @param player the player that is performing the action
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     */
    private void performHarvest(Player player, InformationCallback informationCallback){
        for (DevelopmentCard card : this.players.get(player.getUsername()).getPersonalBoard().getCards(DevelopmentCardColor.GREEN)) {
            card.getPermanentEffect().runEffect(player, informationCallback);
        }
    }

    /**
     * Method that manages the production behavior
     * @param player the player that is performing the action
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
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

        if( (cell.isEmpty() || player.getPersonalBoard().getAlwaysPlaceFamilyMemberInsideActionSpace() ) &&
                player.getPersonalBoard().getExcommunicationValues().getMarketIsAvailable()){
            updateFamilyMemberValue(player, familyMemberColor, servantsValue);
            try {
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
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCards().get(leaderCardAtIndex);

        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) < servants)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        int servantsValue = servants / player.getPersonalBoard().getExcommunicationValues().getNumberOfSlaves();

        leaderCard.checkRequisites(player);

        if (leaderCard.getEffect() instanceof LESimple ||
                leaderCard.getEffect() instanceof LEDiceBonus ||
                leaderCard.getEffect() instanceof LEDiceValueSet ||
                leaderCard.getEffect() instanceof LENeutralBonus ||
                leaderCard.getEffect() instanceof LECesareBorgia ||
                leaderCard.getEffect() instanceof LEFamilyMemberBonus) {
            leaderCard.getEffect().runEffect(player, informationCallback);
        } else if (leaderCard.getEffect() instanceof LEHarvestProductionSimple) {

            List<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
            FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);
            player.getPersonalBoard().getFamilyMember().setFamilyMemberValue(familyMemberColor, servantsValue);

            leaderCard.getEffect().runEffect(player, informationCallback);
        }
    }

    /**
     * This method is used to discard a leader card and to provide a council privilege
     * @param player the player that is performing the action
     * @param leaderCardAtIndex the index of the leader card inside the personal leader card deck
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     */
    public void discardLeaderCard(Player player, int leaderCardAtIndex, InformationCallback informationCallback){
        player.getPersonalBoard().getLeaderCards().remove(leaderCardAtIndex);
        CouncilPrivilege councilPrivilege = new CouncilPrivilege(1);
        councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
    }


    /**
     * This method is used to discard a leader card and to update the player valuables
     * @param player the player that is performing the action
     * @param leaderCardAtIndex the index of the leader card inside the personal leader card deck
     * @param privilege the privilege given to the user
     */
    public void discardLeaderCard(Player player, int leaderCardAtIndex, Privilege privilege){
        player.getPersonalBoard().getLeaderCards().remove(leaderCardAtIndex);
        for (Map.Entry<ResourceType, Integer> entry: privilege.getValuables().getResources().entrySet()) {
            if(entry.getValue() > 0) {
                player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
            }
        }

        for (Map.Entry<PointType, Integer> entry: privilege.getValuables().getPoints().entrySet()) {
            if(entry.getValue() > 0) {
                player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
            }
        }
    }

    /**
     * This method updates the family member value with servants and decrease the number of servants in resources
     * @param player the player that is performing the action
     * @param familyMemberColor the color of the family member used to perform the action
     * @param servantsValue the number of servants used to perform the action
     */
    private void updateFamilyMemberValue(Player player, FamilyMemberColor familyMemberColor, int servantsValue) throws GameException{
        if(player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT) >= servantsValue) {
            player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(familyMemberColor, servantsValue);
            player.getPersonalBoard().getValuables().decrease(ResourceType.SERVANT, servantsValue);
        } else
            throw new GameException(GameErrorType.NOT_ENOUGH_SERVANT_TO_DECREASE);
    }

    /**
     * Thus method restores the original family member value and gives back the servants to the player
     * @param player the player that is performing the action
     * @param familyMemberColor the color of the family member used to perform the action
     * @param servantsValue the number of servants used to perform the action
     */
    private void restoreFamilyMemberValue(Player player, FamilyMemberColor familyMemberColor, int servantsValue){
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(familyMemberColor, servantsValue);
        player.getPersonalBoard().getValuables().increase(ResourceType.SERVANT, servantsValue);
    }

    /**
     * This method checks if the player can get the tower immediate effect
     * @param player
     * @param floor
     * @return
     */
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