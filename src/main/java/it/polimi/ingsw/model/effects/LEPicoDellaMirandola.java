package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

public class LEPicoDellaMirandola extends LeaderEffect {

    private int moneyDiscount;

    public LEPicoDellaMirandola(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void setMoneyDiscount(int value){
        this.moneyDiscount = value;
    }

    public int getMoneyDiscount(){
        return this.moneyDiscount;
    }

    /**
     * This method sets a cost discount for each development card inside the towers
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "When you buy development cards, you get a 3-coin discount on their cost (if they cost coins).";
    }
}
