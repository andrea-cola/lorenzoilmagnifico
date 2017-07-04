package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.InformationCallback;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represent the effect that allow the user to pick up another card with a defined max dice value.
 */
public class EffectCardBonus extends Effect{

    /**
     * Dice value bonus.
     */
    private int diceValueBonus;

    /**
     * Color of card that has the dice value bonus.
     */
    private DevelopmentCardColor[] colors;

    /**
     * Pick up discount in points and resources.
     */
    private PointsAndResources[] pickUpDiscounts;

    /**
     * Constructor
     */
    public EffectCardBonus(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Set development card colors that can be chosen.
     * @param colors
     */
    public void setColors(DevelopmentCardColor[] colors){
        this.colors = colors;
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

    /**
     * Get the bonus value which will affect the dice value
     * @return
     */
    public int getDiceValueBonus(){
        return this.diceValueBonus;
    }

    /**
     * Set the discount on the card
     * @param pickUpDiscounts
     */
    public void setPickUpDiscounts(PointsAndResources[] pickUpDiscounts){
        this.pickUpDiscounts = pickUpDiscounts;
    }

    /**
     *
     * @return
     */
    public PointsAndResources[] getPickUpDiscounts(){
        return this.pickUpDiscounts;
    }

    /**
     * Method to run the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        for (DevelopmentCardColor color : this.colors){
            player.getPersonalBoard().setDevelopmentCardColorDiceValueBonus(color, diceValueBonus);

            ArrayList<PointsAndResources> discounts = new ArrayList<>();
            Collections.addAll(discounts, pickUpDiscounts);

            player.getPersonalBoard().setCostDiscountForDevelopmentCard(color, discounts);
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Permanent bonus on dice value = " + diceValueBonus + " to get ");
        for(DevelopmentCardColor developmentCardColor : colors)
            stringBuilder.append(developmentCardColor.toString().toLowerCase() + " ");
        stringBuilder.append("cards with a discount: ");
        for(PointsAndResources pointsAndResources : pickUpDiscounts)
            stringBuilder.append(pointsAndResources.toString() + " ");
        return stringBuilder.toString();
    }
}