package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

public class LESistoIV extends LeaderEffect{

    private PointsAndResources valuableEarned;

    public LESistoIV(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setValuableEarned(PointsAndResources valuable){
        this.valuableEarned = valuable;
    }

    public PointsAndResources getValuableEarned(){
        return this.valuableEarned;
    }

    /**
     * Method to run the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player) {

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        String header = this.effectType + "\n";
        String resources2 = "Resources earned:\n" + valuableEarned.toString();
        return new StringBuilder(header).append(resources2).append(resources2).toString();
    }
}
