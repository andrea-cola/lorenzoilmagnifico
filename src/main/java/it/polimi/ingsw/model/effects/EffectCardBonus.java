package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

/**
 * This class represent the effect that allow the user to pick up another card with a defined max dice value.
 */
public class EffectCardBonus extends Effect{

    /**
     * Color of card that has the dice value bonus.
     */
    private DevelopmentCardColor[] colors;

    /**
     * Dice value bonus.
     */
    private int diceValueBonus;

    /**
     * Pick up discount in points and resources.
     */
    private PointsAndResources[] pickUpDiscounts;

    public EffectCardBonus(){
        super.effectType = this.getClass().getSimpleName();
    }

    /**
     * Set development card colors that can be chosen.
     * @param developmentCardColors
     */
    public void setColors(DevelopmentCardColor[] developmentCardColors){
        this.colors = developmentCardColors;
    }

    /**
     * Get development card colors that can be chosen.
     * @return development card colors.
     */
    public DevelopmentCardColor[] getColors(){
        return this.colors;
    }

    /**
     * Set dice value bonus.
     * @param value of the dice.
     */
    public void setDiceValueBonus(int value){
        this.diceValueBonus = value;
    }

    public int getDiceValueBonus(){
        return this.diceValueBonus;
    }

    public void setPickUpDiscounts(PointsAndResources[] pickUpDiscounts){
        this.pickUpDiscounts = pickUpDiscounts;
    }

    public PointsAndResources[] getPickUpDiscounts(){
        return this.pickUpDiscounts;
    }

    /**
     * Method to execute the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player) {

    }
}
