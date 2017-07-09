package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;

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
    private EffectHarvestProductionSimple actionSpaceEffect;

    /**
     * Family member has occupied the space.
     */
    private FamilyMemberColor familyMemberColor;

    /**
     * Username of the players has occupied the space.
     */
    private String username;

    /**
     * Boolean value that checks if the action space is empty
     */
    private Boolean empty;

    /**
     * Class constructor
     * @param actionSpaceType
     * @param effect
     */
    /*package-local*/ ActionSpace(ActionType actionSpaceType, EffectHarvestProductionSimple effect){
        this.actionSpaceType = actionSpaceType;
        this.actionSpaceEffect = effect;
        empty = true;
    }

    /**
     * This method returns the action space type
     * @return
     */
    /*package-local*/ ActionType getActionSpaceType(){
        return this.actionSpaceType;
    }

    /**
     * This method returns the action space effect
     * @return
     */
    /*package-local*/ EffectHarvestProductionSimple getActionSpaceEffect(){
        return this.actionSpaceEffect;
    }

    /**
     * This method returns the state of the action space
     * @return
     */
    public boolean isEmpty(){
        return this.empty;
    }

    /**
     * This method is used to update te state of the action space
     * @param updatedValue
     */
    /*package-local*/ void setEmpty(Boolean updatedValue){
        this.empty = updatedValue;
    }

    /**
     * Method to get the color of the family member that has occupied the action space
     * @return
     */
    public FamilyMemberColor getFamilyMemberColor(){
        return this.familyMemberColor;
    }

    /**
     * This method checks if the action space is accessible
     * @param player
     * @param familyMemberColor
     * @throws GameException
     */
    /*package-local*/ void checkAccessibility(Player player, FamilyMemberColor familyMemberColor) throws GameException{
        if(player.getUsername().equals(this.username) && !familyMemberColor.equals(FamilyMemberColor.NEUTRAL) && !this.familyMemberColor.equals(FamilyMemberColor.NEUTRAL))
                throw new GameException();
    }

    /**
     * This method checks if the family member can be placed inside the action space
     * @param player
     * @param familyMemberColor
     * @throws GameException
     */
    /*package-local*/ void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor) throws GameException{

        //check that the family member used has not been already used
        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed()){
            if (familyMemberColor.equals(color)){
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);
            }
        }

        //check that the family member value is greater or equal than the minFamilyMemberDiceValue requested
        int familyMemberValueTot = player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor)
                + player.getPersonalBoard().getHarvestProductionDiceValueBonus().get(actionSpaceType)
                - player.getPersonalBoard().getExcommunicationValues().getHarvestProductionDiceMalus().get(actionSpaceType);
        if (familyMemberValueTot < this.actionSpaceEffect.getDiceActionValue()){
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        }

        //if the family member can be placed, add it to the family members used and set this zone as not empty
        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);
        this.username = player.getUsername();
        this.familyMemberColor = familyMemberColor;
        this.empty = false;
    }

    /**
     * This method resets the state of the action space
     */
    public void reset(){
        this.empty = true;
        this.username = null;
        this.familyMemberColor = null;
    }


    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Status: ");
        if(username != null)
            stringBuilder.append(username + "\n");
        else
            stringBuilder.append("free\n");
        stringBuilder.append("Dice: " + actionSpaceEffect.getDiceActionValue());
        return stringBuilder.toString();
    }
}
