package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

/**
 * This class represent the effect that exchange resources and points with more options.
 */
public class EffectHarvestProductionExchange extends Effect{

    /**
     * Dice value to run the permanent effect.
     */
    private int diceActionValue;

    /**
     * Type of the action that activate the effect.
     */
    private ActionType actionType;

    /**
     * Resources and points to pay.
     */
    private PointsAndResources[] valuableToPay;

    /**
     * Resources and points earnead for each block of resources and points payed.
     */
    private PointsAndResources[] valuableEarned;

    /**
     * Council privilege earned.
     */
    private int numberOfCouncilPrivileges;

    /**
     * Class constructor.
     */
    public EffectHarvestProductionExchange(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Set the action type of the permanent effect.
     * @param type of action.
     */
    public void setActionType(ActionType type){
        this.actionType = type;
    }

    /**
     * Get the action type of the permanent effect.
     * @return type of the action.
     */
    public ActionType getActionType(){
        return this.actionType;
    }

    /**
     * Method to run the effect of the card.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback){
        int choice = 0;
        handleExchange(player, choice, informationCallback);
    }

    /**
     * Method to run the effect of the card
     * @param card
     * @param player
     * @param informationCallback
     */
    public void runEffect(DevelopmentCard card, Player player, InformationCallback informationCallback) {
        if(valuableToPay.length == 1){
            runEffect(player, informationCallback);
        } else {
            int choice = informationCallback.chooseExchangeEffect(card.getName(), valuableToPay, valuableEarned);
            handleExchange(player, choice, informationCallback);
        }
    }

    /**
     * This method manages the choose of the player to run the effect
     * @param player
     * @param choice
     * @param informationCallback
     */
    private void handleExchange(Player player, int choice, InformationCallback informationCallback){
        List<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
        FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);

        int familyMemberValue = player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor);
        int bonus = player.getPersonalBoard().getHarvestProductionDiceValueBonus().get(this.actionType);
        int malus = player.getPersonalBoard().getExcommunicationValues().getHarvestProductionDiceMalus().get(this.actionType);
        int actionValue = familyMemberValue + bonus - malus;

        if (actionValue >= this.diceActionValue) {

            if(player.getPersonalBoard().getValuables().checkDecrease(this.valuableToPay[choice]))
                player.getPersonalBoard().getValuables().decreaseAll(this.valuableToPay[choice]);
            else
                return;

            for (Map.Entry<ResourceType, Integer> entry : this.valuableEarned[choice].getResources().entrySet()) {
                player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
            }

            for (Map.Entry<PointType, Integer> entry : this.valuableEarned[choice].getPoints().entrySet()) {
                player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
            }

            if (this.numberOfCouncilPrivileges > 0){
                CouncilPrivilege councilPrivilege = new CouncilPrivilege(numberOfCouncilPrivileges);
                councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
            }
        }

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(actionType.toString().toLowerCase() + " with dice value = " + diceActionValue);
        stringBuilder.append(". You can exchange these resources: ");
        for(int i = 0; i < valuableToPay.length; i++)
            stringBuilder.append("[" + valuableToPay[i] + "=> " + valuableEarned[i] + "], ");
        stringBuilder.append("and " + numberOfCouncilPrivileges + " council privileges");
        return stringBuilder.toString();
    }

}
