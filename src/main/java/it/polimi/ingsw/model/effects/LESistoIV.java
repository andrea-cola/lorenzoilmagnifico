package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointType;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;

public class LESistoIV extends LeaderEffect{

    /**
     * Valuables to earn.
     */
    private PointsAndResources valuableEarned;

    /**
     * Class constructor.
     */
    public LESistoIV(){
        super.setEffectType(this.getClass().getSimpleName());
        valuableEarned = new PointsAndResources();
    }

    /**
     * This method gives you a bonus when you sustain the vatican at the end of each era
     * @param player is gaining benefit of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        for (Map.Entry<ResourceType, Integer> entry: this.valuableEarned.getResources().entrySet())
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());

        for (Map.Entry<PointType, Integer> entry: this.valuableEarned.getPoints().entrySet())
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "Resources earned when you support the church: " + valuableEarned.toString();
    }
}
