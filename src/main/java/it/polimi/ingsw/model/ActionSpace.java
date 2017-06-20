package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.Effect;

import java.io.Serializable;

/**
 * This class represent action space abstraction.
 */
public class ActionSpace implements Serializable{

    /**
     * Type of the area.
     */
    private ActionType actionSpaceType;

    /**
     * Action space immediate effect.
     */
    private Effect actionSpaceEffect;

    /**
     * Family member has occupied the space.
     */
    private FamilyMember familyMember;

    /**
     * Min dice value to place a family member in the action space.
     */
    private int minFamilyMemberValue;

    /**
     * Boolean value that checks if the action space is empty
     */
    private Boolean empty = true;


    public ActionSpace(ActionType actionSpaceType, int minFamilyMemberValue){
        this.actionSpaceType = actionSpaceType;
        this.minFamilyMemberValue = minFamilyMemberValue;
    }

    /**
     * Get the action type of the action area.
     * @return the action type
     */
    public ActionType getActionSpaceType(){
        return this.actionSpaceType;
    }

    /**
     * Get the effect of the action space.
     * @return effect of the action space.
     */
    public Effect getActionSpaceEffect(){
        return this.actionSpaceEffect;
    }

    public Boolean getEmpty(){
        return this.empty;
    }

    public void setEmpty(Boolean updatedValue){
        this.empty = updatedValue;
    }

    /**
     * Checks if the value is enough to place the family member inside the cell
     * @param player
     * @param familyMemberColor
     * @throws GameException
     */
    public void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor) throws GameException{

        //check that the family member used has not been already used
        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed()){
            if (familyMemberColor.equals(color)){
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);
            }
        }

        //check that the family member value is greater or equal than the minFamilyMemberDiceValue requested
        if (player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) < this.minFamilyMemberValue){
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        }


        //if the family member can be placed, add it to the family members used and set this zone as not empty
        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);
        this.empty = false;
    }

}
