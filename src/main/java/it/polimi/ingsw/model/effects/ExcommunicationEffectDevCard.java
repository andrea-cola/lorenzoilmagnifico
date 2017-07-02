package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;

public class ExcommunicationEffectDevCard extends ExcommunicationEffect {

    private DevelopmentCardColor developmentCardColor;

    private int diceMalus;

    public void setDevelopmentCardColor(DevelopmentCardColor color){
        this.developmentCardColor = color;
    }

    public DevelopmentCardColor getDevelopmentCardColor(){
        return this.developmentCardColor;
    }

    public void setDiceMalus(int value){
        this.diceMalus = value;
    }

    public int getDiceMalus(){
        return this.diceMalus;
    }

    public ExcommunicationEffectDevCard(){
        super.setEffectType(this.getClass().getSimpleName());
    }


    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setDevelopmentCardDiceMalus(this.developmentCardColor, this.diceMalus);
    }

    public String getDescription() {
        return "Dice malus for all card with following color ( " + this.developmentCardColor + ", " + diceMalus + " )";
    }

}
