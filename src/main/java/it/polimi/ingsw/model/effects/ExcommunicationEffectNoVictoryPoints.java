package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;


public class ExcommunicationEffectNoVictoryPoints extends ExcommunicationEffect {

    private DevelopmentCardColor developmentCardColor;

    public void setDevelopmentCardColor(DevelopmentCardColor color){
        this.developmentCardColor = color;
    }

    public DevelopmentCardColor getDevelopmentCardColor(){
        return this.developmentCardColor;
    }

    public ExcommunicationEffectNoVictoryPoints(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setDevelopmentCardGetFinalPoints(this.developmentCardColor, false);
    }

    public String getDescription(){
        return "Card of the following colors won't assign victory points: ( " + this.developmentCardColor + " )";
    }
}
