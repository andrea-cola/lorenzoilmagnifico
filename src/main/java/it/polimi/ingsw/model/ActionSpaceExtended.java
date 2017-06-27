package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;

import java.io.Serializable;
import java.util.ArrayList;
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
    public ActionSpaceExtended(ActionType actionSpaceType, int diceValueMalus, EffectHarvestProductionSimple effect){
        this.actionSpaceType = actionSpaceType;
        this.diceValueMalus = diceValueMalus;
        this.effect = effect;
        this.accessible = true;
    }

    public void setNotAccessible(){
        this.accessible = false;
    }

    public boolean isAccessible(){
        return this.accessible;
    }

    /**
     * Get the action type of the action area.
     * @return action type.
     */
    public ActionType getActionSpaceType(){
        return this.actionSpaceType;
    }

    /**
     * Get the malus value on the dice.
     * @return value of malus.
     */
    public int getDiceValueMalus(){
        return this.diceValueMalus;
    }

    /**
     * Add a family member to the space
     * @param familyMember
     */
    public void addFamilyMember(Player player, FamilyMemberColor familyMember){
        this.familyMemberMap.put(player.getUsername(), familyMember);
    }

    /**
     * Get action space effect.
     * @return effect of the space.
     */
    public EffectHarvestProductionSimple getEffect(){
        return this.effect;
    }

    public void checkAccessibility(Player player, FamilyMemberColor familyMemberColor) throws GameException{
        if(!familyMemberColor.equals(FamilyMemberColor.NEUTRAL))
            if(familyMemberMap.containsKey(player.getUsername()) && !familyMemberMap.get(player.getUsername()).equals(FamilyMemberColor.NEUTRAL))
                throw new GameException();
    }

    public void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor, int servants) throws GameException{

        //check that the family member used has not been already used
        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed())
            if (familyMemberColor.equals(color))
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);

        //check that the family member value is greater or equal than the minFamilyMemberDiceValue requested
        int familyMemberValueTot = player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) + servants;
        if (familyMemberValueTot < (this.effect.getDiceActionValue() + diceValueMalus))
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        //if the family member can be placed, add it to the family members used and set this zone as not empty
        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);
        player.getPersonalBoard().getValuables().decrease(ResourceType.SERVANT, servants);
        familyMemberMap.put(player.getUsername(), familyMemberColor);
    }

    public void reset(){
        this.familyMemberMap = new HashMap<>();
    }

}
