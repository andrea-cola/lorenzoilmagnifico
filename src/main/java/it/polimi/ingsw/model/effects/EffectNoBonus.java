package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.InformationCallback;

public class EffectNoBonus extends Effect{

    int[] floors;

    public EffectNoBonus(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void setFloors(int[] floors){
        this.floors = floors;
    }

    public int[] getFloors(){
        return this.floors;
    }

    /**
     * Method to run the effect of the card.
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        // this method is empty because the effect is check in Game.java
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("You won't receive bonus at these tower floors: ");
        for(Integer floor : floors)
            stringBuilder.append(floor + " ");
        return stringBuilder.toString();
    }
}
