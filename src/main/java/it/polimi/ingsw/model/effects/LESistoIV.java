package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.Map;

public class LESistoIV extends LeaderEffect{

    private PointsAndResources valuableEarned;

    public LESistoIV(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void setValuableEarned(PointsAndResources valuable){
        this.valuableEarned = valuable;
    }

    public PointsAndResources getValuableEarned(){
        return this.valuableEarned;
    }

    /**
     * This method gives you a bonus when you sustain the vatican at the end of each era
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuableEarned.getResources().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuableEarned.getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "Resources earned when you support the church: " + valuableEarned.toString();
    }
}
