package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent an extension of action space. More family members can join this space and the effect has a malus to be activated.
 */
public class ActionSpaceExtended implements Serializable{

    /**
     * Type of the area.
     */
    private ActionType actionSpaceType;

    /**
     * Action space immediate effect.
     */
    private EffectHarvestProductionSimple effect;

    /**
     * Malus value of the dice.
     */
    private int diceValueMalus;

    /**
     * Map of players username and family member.
     */
    private Map<String, FamilyMemberColor> familyMemberMap;

    /**
     * Specifies if the space is active or not. True -> active, False -> not active.
     */
    private boolean accessible;

    /**
     * Class constructor.
     * @param actionSpaceType of the action space.
     * @param diceValueMalus of the action space.
     * @param effect of the action space.
     */
    /*package-local*/ ActionSpaceExtended(ActionType actionSpaceType, int diceValueMalus, EffectHarvestProductionSimple effect){
        this.actionSpaceType = actionSpaceType;
        this.diceValueMalus = diceValueMalus;
        this.effect = effect;
        this.familyMemberMap = new HashMap<>();
        this.accessible = true;
    }

    /**
     * This method sets the action space to not accessible
     */
    /*package-local*/ void setNotAccessible(){
        this.accessible = false;
    }

    /**
     * This method is used to check if the action space is accessible
     * @return
     */
    /*package-local*/ boolean isAccessible(){
        return this.accessible;
    }

    /**
     * Get the action type of the action area.
     * @return action type.
     */
    /*package-local*/ ActionType getActionSpaceType(){
        return this.actionSpaceType;
    }

    /**
     * Get the malus value on the dice.
     * @return value of malus.
     */
    /*package-local*/ int getDiceValueMalus(){
        return this.diceValueMalus;
    }


    /**
     * Get action space effect.
     * @return effect of the space.
     */
    public EffectHarvestProductionSimple getEffect(){
        return this.effect;
    }

    /**
     * This method checks if the action space is accessible
     * @param player
     * @param familyMemberColor
     * @throws GameException
     */
    /*package-local*/ void checkAccessibility(Player player, FamilyMemberColor familyMemberColor) throws GameException{
        if(!familyMemberColor.equals(FamilyMemberColor.NEUTRAL) && familyMemberMap.containsKey(player.getUsername())
                && !familyMemberMap.get(player.getUsername()).equals(FamilyMemberColor.NEUTRAL))
                throw new GameException();
    }

    /**
     * This method checks if the family member can be placed inside this action space
     * @param player
     * @param familyMemberColor
     * @param servants
     * @throws GameException
     */
    /*package-local*/ void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor, int servants) throws GameException{

        //check that the family member used has not been already used
        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed())
            if (familyMemberColor.equals(color))
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);

        //check that the family member value is greater or equal than the minFamilyMemberDiceValue requested
        int familyMemberValueTot = player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor)
                + player.getPersonalBoard().getHarvestProductionDiceValueBonus().get(actionSpaceType)
                - player.getPersonalBoard().getExcommunicationValues().getHarvestProductionDiceMalus().get(actionSpaceType);

        if (familyMemberValueTot < (this.effect.getDiceActionValue() + diceValueMalus))
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        //if the family member can be placed, add it to the family members used and set this zone as not empty
        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);
        player.getPersonalBoard().getValuables().decrease(ResourceType.SERVANT, servants);
        familyMemberMap.put(player.getUsername(), familyMemberColor);
    }

    /**
     * This method resets the action space state
     */
    public void reset(){
        this.familyMemberMap = new HashMap<>();
    }

    public String toString(){
        return new StringBuilder("Dice : " + effect.getDiceActionValue() + "\nMalus on dice: " + diceValueMalus).toString();
    }

}
