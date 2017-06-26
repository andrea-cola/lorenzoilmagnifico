package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointType;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;


public class ExcommunicationEffectSimple extends ExcommunicationEffect{

    private PointsAndResources valuablesMalus;

    public void setValuablesMalus(PointsAndResources malus){
        this.valuablesMalus = malus;
    }

    public PointsAndResources getValuablesMalus(){
        return this.valuablesMalus;
    }


    public ExcommunicationEffectSimple(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        //update resources malus
        for (Map.Entry<ResourceType, Integer> entry: this.valuablesMalus.getResources().entrySet()) {
            player.getPersonalBoard().getExcommunicationValues().increaseNormalValuablesMalus(entry.getKey(), entry.getValue());
        }

        //update points malus
        for (Map.Entry<PointType, Integer> entry: this.valuablesMalus.getPoints().entrySet()) {
            player.getPersonalBoard().getExcommunicationValues().increaseNormalValuablesMalus(entry.getKey(), entry.getValue());
        }
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String resources = "Valuables malus:\n" + this.valuablesMalus.toString();
        return new StringBuilder(header).append(resources).toString();
    }
}
