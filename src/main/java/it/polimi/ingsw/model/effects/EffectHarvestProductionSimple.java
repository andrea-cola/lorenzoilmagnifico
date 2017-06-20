package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
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
    private CouncilPrivilege councilPrivilege;

    /**
     * Class constructor.
     */
    public EffectHarvestProductionSimple(){
        this.valuable = new PointsAndResources();
        this.councilPrivilege = new CouncilPrivilege();

        super.effectType = this.getClass().getSimpleName();
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
    public void setCouncilPrivilege(CouncilPrivilege councilPrivilege){
        this.councilPrivilege = councilPrivilege;
    }

    /**
     * Get council privilege of the effect.
     * @return council privilege.
     */
    public CouncilPrivilege getCouncilPrivilege(){
        return this.councilPrivilege;
    }

    /**
     * Run the effect.
     * @param player
     */
    @Override
    public void runEffect(Player player){
        //get the family member used to run this effect
        ArrayList<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
        FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);

        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet()) {
            if (player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) >= this.diceActionValue){
                player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
            }
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

    }


}
