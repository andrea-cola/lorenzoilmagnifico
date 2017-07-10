package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

/**
 * This class represents the leader effect that provides a discount on development cards
 */
public class LEFilippoBrunelleschi extends LeaderEffect{

    /**
     * Money discount on the card.
     */
    private int moneyDiscount;

    /**
     * Class constructor.
     */
    public LEFilippoBrunelleschi(){
        super.setEffectType(this.getClass().getSimpleName());
        moneyDiscount = 0;
    }

    /**
     * Method to run the effect of the card.
     * @param player is gaining benefit from the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        player.getPersonalBoard().getValuables().increase(ResourceType.COIN, moneyDiscount);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "You do not have to spend " + moneyDiscount + " extra coins when you place a family in a tower already occupied.";
    }
}
