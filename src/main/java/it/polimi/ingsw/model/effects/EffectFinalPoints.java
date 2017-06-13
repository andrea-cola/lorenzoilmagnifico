package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

/**
 * This class represent the permanent effect that is activated at the end of the game.
 * This effect gives an amount of victory points.
 */
public class EffectFinalPoints extends Effect {

    /**
     * Amount of final victory points.
     */
    private PointsAndResources finalVictoryPoints;

    /**
     * Set the amount of victory points.
     * @param points amount.
     */
    public void setFinalVictoryPoints(PointsAndResources points){
        this.finalVictoryPoints = points;
    }

    /**
     * Get victory points amount.
     * @return victory points amount.
     */
    public PointsAndResources getFinalVictoryPoints(){
        return this.finalVictoryPoints;
    }

    public EffectFinalPoints(){
        super.effectType = this.getClass().getSimpleName();
    }

    /**
     * Method to run the effect.
     * @param player that run the effect.
     */
    @Override
    public void runEffect(Player player){

    }

}
