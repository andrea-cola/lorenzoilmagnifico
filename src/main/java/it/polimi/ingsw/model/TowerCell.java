package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.Effect;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents tower cell abstraction.
 */
public class TowerCell implements Serializable{

    /**
     * Min dice value to place a family member in the cell.
     */
    private int minFamilyMemberValue;

    /**
     * Development card assigned to this cell.
     */
    private DevelopmentCard developmentCard;

    /**
     * Immediate effect of the cell.
     */
    private Effect towerCellImmediateEffect;

    /**
     * The nickname of the player whose one of his familiar is inside the cell
     */
    private String playerNicknameInTheCell;

    /**
     * Get immediate effect of the cell.
     * @return cell immediate effect.
     */
    public Effect getTowerCellImmediateEffect(){
        return this.towerCellImmediateEffect;
    }

    /**
     * Set development card in the cell.
     * @param card of the cell.
     */
    public void setDevelopmentCard(DevelopmentCard card){
        this.developmentCard = card;
    }

    /**
     * Return the development card assigned to the cell.
     * @return a development card.
     */
    public DevelopmentCard getDevelopmentCard(){
        return this.developmentCard;
    }

    /**
     * Return dice value of the cell.
     * @return an integer.
     */
    public int getMinFamilyMemberValue(){
        return this.minFamilyMemberValue;
    }

    /**
     * Assign the cell to the user that occupied it
     * @param playerNicknameInTheCell in the cell.
     */
    public void setPlayerNicknameInTheCell(String playerNicknameInTheCell){
        this.playerNicknameInTheCell = playerNicknameInTheCell;
    }


    /**
     * Get the username of the player inside a particular cell
     * @return player nickname in the cell.
     */
    public String getPlayerNicknameInTheCell(){
        return this.playerNicknameInTheCell;
    }

    /**
     * Class constructor.
     * @param minFamilyMemberValue to join the cell.
     */
    /*package-local*/ TowerCell(int minFamilyMemberValue){
        this.minFamilyMemberValue = minFamilyMemberValue;
    }

    /**
     * Set immediate effect of the cell.
     * @param effect of the cell.
     */
    /*package-local*/ void setTowerCellImmediateEffect(Effect effect){
        this.towerCellImmediateEffect = effect;
    }

    /**
     * Set the minimum value to let a family member access on the cell
     * @param value minimum value
     */
    /*package-local*/ void setMinFamilyMemberValue(Integer value){
        this.minFamilyMemberValue = value;
    }

    /**
     * Checks if the value is enough to place the family member inside the cell and if the user can pickup a territory card according to the military points required
     * @param player is performing the placement.
     * @param familyMemberColor of familiar.
     * @throws GameException if family member value is wrong to perform the action.
     */
    /*package-local*/ void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor) throws GameException{
        int familyMemberRealValue = player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor)
                + player.getPersonalBoard().getDevelopmentCardColorDiceValueBonus().get(developmentCard.getColor())
                - player.getPersonalBoard().getExcommunicationValues().getDevelopmentCardDiceMalus().get(developmentCard.getColor());

        if (familyMemberRealValue < this.minFamilyMemberValue)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
    }

    /**
     * Checks if the player can get the development card.
      * @param player is performing the action.
     * @throws GameException if the card can't be bought.
     */
    /*package-local*/ void developmentCardCanBeBought(Player player, InformationCallback informationCallback) throws GameException{
        checkCardLimit(player);
        PointsAndResources discount = setCardDiscount(player, informationCallback);
        checkResourcesToBuyTheCard(player, discount);
        checkMilitaryPointsToGetTheCard(player);
        giveDiscountResources(player, discount);
    }

    /**
     * This method check if the player has reached the maximum limit of cards
     * @param player is performing the action.
     * @throws GameException if the player has reached max card limit.
     */
    private void checkCardLimit(Player player) throws GameException{
        if (player.getPersonalBoard().getCards(this.developmentCard.getColor()).size() > 6)
            throw new GameException(GameErrorType.PERSONAL_BOARD_MAX_CARD_LIMIT_REACHED);
    }

    /**
     * This method sets the discount that the card can provide.
     * @param player is performing the action.
     * @param informationCallback to callback the user interface.
     * @return discount chosen.
     */
    private PointsAndResources setCardDiscount(Player player, InformationCallback informationCallback){
        //if there is just one choice, set discount immediately
        PointsAndResources discount = new PointsAndResources();
        if(player.getPersonalBoard().getCostDiscountForDevelopmentCard(developmentCard.getColor()).size() == 1) {
            discount = player.getPersonalBoard().getCostDiscountForDevelopmentCard(developmentCard.getColor()).get(0);
        }
        //if there is more than one choice, let the player to choose and then set the discount
        else if(player.getPersonalBoard().getCostDiscountForDevelopmentCard(developmentCard.getColor()).size() > 1){
            int choice = informationCallback.choosePickUpDiscounts("discount", player.getPersonalBoard().getCostDiscountForDevelopmentCard(developmentCard.getColor()));
            discount = player.getPersonalBoard().getCostDiscountForDevelopmentCard(developmentCard.getColor()).get(choice);
        }
        return discount;
    }

    /**
     * This method checks if the player has resources enough to buy the card.
     * @param player is performing the action.
     * @param discount of the card.
     * @throws GameException if the player has not resources enough.
     */
    private void checkResourcesToBuyTheCard(Player player, PointsAndResources discount) throws GameException{
        boolean flag  = false;
        if (this.developmentCard.getMultipleRequisiteSelectionEnabled()){
            if (player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY) >= this.developmentCard.getMilitaryPointsRequired()){
                flag = true;
            }
            if(!flag) {
                if(!checkNullResources()) {
                    for (Map.Entry<ResourceType, Integer> entry : this.developmentCard.getCost().getResources().entrySet()) {
                        if (this.developmentCard.getCost().getResources().get(entry.getKey()) - discount.getResources().get(entry.getKey()) > player.getPersonalBoard().getValuables().getResources().get(entry.getKey())) {
                            throw new GameException(GameErrorType.PLAYER_RESOURCES_ERROR);
                        }
                    }
                } else {
                    throw new GameException(GameErrorType.PLAYER_RESOURCES_ERROR);
                }

            }
        } else {
            for (Map.Entry<ResourceType, Integer> entry : this.developmentCard.getCost().getResources().entrySet()) {
                if (this.developmentCard.getCost().getResources().get(entry.getKey()) - discount.getResources().get(entry.getKey())
                        > player.getPersonalBoard().getValuables().getResources().get(entry.getKey())) {
                    throw new GameException(GameErrorType.PLAYER_RESOURCES_ERROR);
                }
            }
        }
    }

    /**
     * This method is used to check if there are no resources to pay for the card
     * @return
     */
    private boolean checkNullResources(){
        for(Map.Entry<ResourceType, Integer> entry : this.developmentCard.getCost().getResources().entrySet())
            if(entry.getValue() > 0)
                return false;
        for(Map.Entry<PointType, Integer> entry : this.developmentCard.getCost().getPoints().entrySet())
            if(entry.getValue() > 0)
                return false;
        return true;
    }

    /**
     * This method checks if the player has military points enough to buy the card.
     * @param player is performing the action.
     * @throws GameException if the player has reached the limit.
     */
    private void checkMilitaryPointsToGetTheCard(Player player) throws GameException{
        if (this.developmentCard.getColor().equals(DevelopmentCardColor.GREEN)){
            int amount = player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).size();
            int playerMilitaryPoints = player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY);
            int militaryPointsRequired = player.getPersonalBoard().getGreenCardsMilitaryPointsRequirements(amount);
            if (playerMilitaryPoints < militaryPointsRequired)
                throw new GameException(GameErrorType.MILITARY_POINTS_REQUIRED);
        }
    }

    /**
     * This method give the resources selected as a discount after the full payment of the development card
     * @param player is performing the action.
     * @param discount to apply.
     */
    private void giveDiscountResources(Player player, PointsAndResources discount){
        for(Map.Entry<ResourceType, Integer> entry : discount.getResources().entrySet())
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        if(playerNicknameInTheCell != null)
            stringBuilder.append("Occupied by: " + playerNicknameInTheCell + "\n");
        else {
            stringBuilder.append("\nDice: " + minFamilyMemberValue + "\n");
            if(towerCellImmediateEffect != null)
                stringBuilder.append("Cell effect: " + towerCellImmediateEffect.toString() + "\n");
            stringBuilder.append(developmentCard.toString());
        }
        return stringBuilder.toString();
    }
}
