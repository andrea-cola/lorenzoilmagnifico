package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class LENeutralBonus extends LeaderEffect{

    private int bonus;

    public LENeutralBonus(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setBonus(int value){
        this.bonus = value;
    }

    public int getBonus(){
        return this.bonus;
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
        return "Neutral family member value increased by " + bonus;
    }
}
