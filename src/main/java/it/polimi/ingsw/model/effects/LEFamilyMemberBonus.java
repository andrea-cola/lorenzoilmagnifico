package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;

public class LEFamilyMemberBonus extends LeaderEffect{

    private int bonus;

    public LEFamilyMemberBonus(){
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
    public void runEffect(Player player, InformationCallback informationCallback) {

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        return "One family member value is increased by " + bonus;
    }
}
