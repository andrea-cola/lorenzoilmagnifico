package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class EffectNoBonus extends Effect{

    int[] floors;

    public EffectNoBonus(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setFloors(int[] floors){
        this.floors = floors;
    }

    public int[] getFloors(){
        return this.floors;
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
        String description = this.effectType + "\n";
        description.concat("Resources to pay: da finire di implementare.\n");
        return description;
    }
}
