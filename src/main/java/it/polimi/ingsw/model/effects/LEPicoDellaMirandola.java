package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

public class LEPicoDellaMirandola extends LeaderEffect {

    /**
     * Money discount on the card.
     */
    private int moneyDiscount;

    /**
     * Class constructor.
     */
    public LEPicoDellaMirandola(){
        super.setEffectType(this.getClass().getSimpleName());
        moneyDiscount = 0;
    }

    /**
     * Return money discount.
     * @return money discount.
     */
    public int getMoneyDiscount(){
        return this.moneyDiscount;
    }

    /**
     * This method sets a cost discount for each development card inside the towers.
     * @param player is gaining benefit of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        // this method is empty because the effect is checked in Game.java
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "When you buy development cards, you get a 3-coin discount on their cost (if they cost coins).";
    }
}
