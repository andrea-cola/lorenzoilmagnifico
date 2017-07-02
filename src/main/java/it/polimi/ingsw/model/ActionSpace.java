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

    /*package-local*/ ActionSpace(ActionType actionSpaceType, EffectHarvestProductionSimple effect){
        this.actionSpaceType = actionSpaceType;
        this.actionSpaceEffect = effect;
        empty = true;
    }

    /*package-local*/ ActionType getActionSpaceType(){
        return this.actionSpaceType;
    }

    /*package-local*/ EffectHarvestProductionSimple getActionSpaceEffect(){
        return this.actionSpaceEffect;
    }

    /*package-local*/ boolean isEmpty(){
        return this.empty;
    }

    /*package-local*/ void setEmpty(Boolean updatedValue){
        this.empty = updatedValue;
    }

    public FamilyMemberColor getFamilyMemberColor(){
        return this.familyMemberColor;
    }

    /*package-local*/ void checkAccessibility(Player player, FamilyMemberColor familyMemberColor) throws GameException{
        if(player.getUsername().equals(this.username))
            if(!familyMemberColor.equals(FamilyMemberColor.NEUTRAL) && !this.familyMemberColor.equals(FamilyMemberColor.NEUTRAL))
                throw new GameException();
    }

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
