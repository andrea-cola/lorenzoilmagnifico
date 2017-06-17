package it.polimi.ingsw.model;

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

    public void setActionSpaceEffect(Effect effect){
        this.actionSpaceEffect = effect;
    }

    public void setActionSpaceType(ActionType actionSpaceType){
        this.actionSpaceType = actionSpaceType;
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

}
