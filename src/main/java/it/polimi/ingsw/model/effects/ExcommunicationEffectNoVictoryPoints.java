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
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setDevelopmentCardGetFinalPoints(this.developmentCardColor, false);
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String devCard = "Development card: \n" + this.developmentCardColor;
        return new StringBuilder(header).append(devCard).toString();
    }
}
