package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;

import java.io.Serializable;

/**
 * This class represent the personal board tile astraction.
 */
public class PersonalBoardTile implements Serializable{

    /**
     * Personal board identifier.
     */
    private int personalBoardID;

    /**
     * Production immediate effect.
     */
    private Effect productionEffect;

    /**
     * Harvest immediate effect.
     */
    private Effect harvestEffect;

    /**
     * Get production immediate effect.
     * @return immediate effect.
     */
    public Effect getProductionEffect(){
        return this.productionEffect;
    }

    /**
     * Get harvest immediate effect.
     * @return immediate effect.
     */
    public Effect getHarvestEffect(){
        return this.harvestEffect;
    }

    public int getPersonalBoardID(){
        return this.personalBoardID;
    }

}
