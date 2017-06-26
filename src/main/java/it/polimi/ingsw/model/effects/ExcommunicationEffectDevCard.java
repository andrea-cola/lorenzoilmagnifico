package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;

import java.util.Map;


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
        super.effectType = this.getClass().getSimpleName();
    }


    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setDevelopmentCardDiceMalus(this.developmentCardColor, this.diceMalus);
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String color = "Development card color: \n" + this.developmentCardColor;
        String malus = "Card malus: \n" + this.diceMalus;
        return new StringBuilder(header).append(color).append(malus).toString();
    }



}
