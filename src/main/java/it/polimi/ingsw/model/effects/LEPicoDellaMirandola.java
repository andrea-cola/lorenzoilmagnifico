package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class LEPicoDellaMirandola extends LeaderEffect {

    private int moneyDiscount;

    public LEPicoDellaMirandola(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setMoneyDiscount(int value){
        this.moneyDiscount = value;
    }

    public int getMoneyDiscount(){
        return this.moneyDiscount;
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
        return "When you buy development leaderCards, you get a 3-coin discount on their cost (if they cost coins).";
    }
}
