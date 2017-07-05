package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.InformationCallback;

import java.util.List;
import java.util.Map;

/**
 * This class represent a simple effect. This type of effect allows
 * the user to get resources and points.
 */
public class EffectHarvestProductionSimple extends Effect{

    /**
     * Dice value to run the permanent effect.
     */
    private int diceActionValue;

    /**
     * Type of the action that activate the effect.
     */
    private ActionType actionType;

    /**
     * Resources and points earned by the player.
     */
    private PointsAndResources valuable;

    /**
     * Council privilege earned by the player.
     */
    private int numberOfCouncilPrivileges;

    /**
     * Class constructor.
     */
    public EffectHarvestProductionSimple(){
        super.setEffectType(this.getClass().getSimpleName());
        this.valuable = new PointsAndResources();
    }

    /**
     * Set the dice value to activate the action
     * of the permanent effect.
     * @param value of the dice.
     */
    public void setDiceActionValue(int value){
        this.diceActionValue = value;
    }

    /**
     * Get the dice value to active the action
     * of the permanent effect.
     * @return value of the dice.
     */
    public int getDiceActionValue(){
        return this.diceActionValue;
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
     * Set resources and points of the permanent effect.
     * @param valuable resources and points.
     */
    public void setValuable(PointsAndResources valuable){
        this.valuable = valuable;
    }

    /**
     * Get resources and points of the permanent effect.
     * @return resources and points.
     */
    public PointsAndResources getValuable(){
        return this.valuable;
    }

    /**
     * Set council privilege.
     * @param councilPrivilege to set.
     */
    public void setCouncilPrivilege(int councilPrivilege){
        this.numberOfCouncilPrivileges = councilPrivilege;
    }

    /**
     * Get council privilege of the effect.
     * @return council privilege.
     */
    public int getCouncilPrivilege(){
        return this.numberOfCouncilPrivileges;
    }

    /**
     * Run the effect.
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        //get the family member used to run this effect
        List<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
        FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);

        //set action value
        int actionValue = player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor)
                + player.getPersonalBoard().getHarvestProductionDiceValueBonus().get(this.actionType)
                - player.getPersonalBoard().getExcommunicationValues().getHarvestProductionDiceMalus().get(this.actionType);

        if (actionValue >= this.diceActionValue) {
            //updates player's resources
            for (Map.Entry<ResourceType, Integer> entry : this.valuable.getResources().entrySet()) {
                player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                //excommunication effect
                player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
            }

            //updates player's points
            for (Map.Entry<PointType, Integer> entry : this.valuable.getPoints().entrySet()) {
                player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                //excommunication effect
                player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
            }
        }

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(actionType.toString().toLowerCase() + " with dice value = " + diceActionValue);
        stringBuilder.append(". Earn " + valuable.toString() + "and " + numberOfCouncilPrivileges + " council privileges");
        return stringBuilder.toString();
    }

}
