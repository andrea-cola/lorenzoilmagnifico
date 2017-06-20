package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class LEFilippoBrunelleschi extends LeaderEffect{

    private int moneyDiscount;

    public LEFilippoBrunelleschi(){
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
        return "You do not have to spend " + moneyDiscount + " extra coins when you place a family in a tower already occupied.";
    }
}
