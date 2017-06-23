package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;

import java.io.Serializable;
import java.util.ArrayList;

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
     * Array of all family members that have joined the space.
     */
    private ArrayList<FamilyMember> familyMembers;

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
    public void addFamilyMember(FamilyMember familyMember){
        this.familyMembers.add(familyMember);
    }

    /**
     * Get action space effect.
     * @return effect of the space.
     */
    public EffectHarvestProductionSimple getEffect(){
        return this.effect;
    }

}
