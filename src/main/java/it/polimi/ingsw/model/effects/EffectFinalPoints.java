package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointType;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.InformationCallback;

import java.util.Map;

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
     * Class constructor.
     */
    public EffectFinalPoints(){
        super.setEffectType(this.getClass().getSimpleName());
    }

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

    /**
     * Method to run the effect.
     * @param player that run the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        for (Map.Entry<PointType, Integer> entry: this.finalVictoryPoints.getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Earned final points: ( " + finalVictoryPoints.toString() + " )");
        return stringBuilder.toString();
    }

}
