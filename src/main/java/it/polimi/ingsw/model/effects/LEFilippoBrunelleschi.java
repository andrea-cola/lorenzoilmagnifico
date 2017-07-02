package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

import javax.sound.sampled.Line;

public class LEFilippoBrunelleschi extends LeaderEffect{

    private int moneyDiscount;

    public LEFilippoBrunelleschi(){
        super.setEffectType(this.getClass().getSimpleName());
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
