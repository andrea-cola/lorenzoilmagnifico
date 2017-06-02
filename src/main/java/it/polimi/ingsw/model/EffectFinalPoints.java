package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 01/06/17.
 */
public class EffectFinalPoints extends Effect {

    private PointsAndResources finalVictoryPoints;

    public EffectFinalPoints(){
        this.finalVictoryPoints = new PointsAndResources();
    }

    public void setFinalVictoryPoints(PointsAndResources points){
        this.finalVictoryPoints = points;
    }

    public PointsAndResources getFinalVictoryPoints(){
        return this.finalVictoryPoints;
    }

    public void runEffect(Player player){

    }

}
