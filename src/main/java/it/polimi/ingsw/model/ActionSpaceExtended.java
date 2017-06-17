package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;

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
    private Effect effect;

    /**
     * Malus value of the dice.
     */
    private int diceValueMalus;

    /**
     * Array of all family members that have joined the space.
     */
    private ArrayList<FamilyMember> familyMembers;

    /**
     * Set the effect of the action space
     * @param effect involved
     */
    public void setEffect(Effect effect){
        this.effect = effect;
    }

    /**
     * Set the value of malus which will affect the dice value
     * @param value of malus
     */
    public void setDiceValueMalus(int value){
        this.diceValueMalus = value;
    }

    /**
     * Add a family member to the space
     * @param familyMember
     */
    public void addFamilyMember(FamilyMember familyMember){
        this.familyMembers.add(familyMember);
    }

    /**
     * Set the action type to the space
     * @param actionSpaceType
     */
    public void setActionSpaceType(ActionType actionSpaceType){
        this.actionSpaceType = actionSpaceType;
    }

    /**
     * Get the action type of the action area.
     * @return
     */
    public ActionType getActionSpaceType(){
        return this.actionSpaceType;
    }

    /**
     * Get action space effect.
     * @return effect of the space.
     */
    public Effect getEffect(){
        return this.effect;
    }

    /**
     * Get the malus value on the dice.
     * @return value of malus.
     */
    public int getDiceValueMalus(){
        return this.diceValueMalus;
    }

}
