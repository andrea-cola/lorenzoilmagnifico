package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

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
     * This method sets a cost discount for each development card inside the towers
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, MainBoard mainBoard) {
        for (int i = 0; i < mainBoard.getNumberOfTowers(); i++){
            for (int j = 0; j < mainBoard.getNumberOfTowerCells(); j++){
                mainBoard.getTower(i).getTowerCell(j).getDevelopmentCard().getCost().decrease(ResourceType.COIN, moneyDiscount);
            }
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        return "When you buy development cards, you get a 3-coin discount on their cost (if they cost coins).";
    }
}
