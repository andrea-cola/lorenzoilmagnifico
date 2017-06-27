package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

import java.awt.*;

public class ExcommunicationEffectDevCardLoseVictoryPoints extends ExcommunicationEffect {

    private DevelopmentCardColor developmentCardColor;

    private PointsAndResources valuablesToLoseIndex;

    private PointsAndResources valuablesLost;

    public void setDevelopmentCardColor(DevelopmentCardColor cardColor){
        this.developmentCardColor = cardColor;
    }

    public DevelopmentCardColor getDevelopmentCardColor(){
        return this.developmentCardColor;
    }

    public void setValuablesToLoseIndex(PointsAndResources valuablesToLoseIndex){
        this.valuablesToLoseIndex = valuablesToLoseIndex;
    }

    public PointsAndResources getValuablesToLoseIndex(){
        return this.valuablesToLoseIndex;
    }

    public void setValuablesLost(PointsAndResources valuablesLost){
        this.valuablesLost = valuablesLost;
    }

    public PointsAndResources getValuablesLost(){
        return this.valuablesLost;
    }

    public ExcommunicationEffectDevCardLoseVictoryPoints(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){

    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String devCardColor = "Development card color:\n" + this.developmentCardColor;
        String valuablesToLooseIndex = "Valuables to loose index:\n" + this.valuablesToLoseIndex;
        String valuablesLost = "Valuables lost:\n" + this.valuablesLost;
        return new StringBuilder(header).append(devCardColor).append(valuablesToLooseIndex).append(valuablesLost).toString();
    }

}
