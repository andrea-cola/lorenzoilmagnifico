package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.Iterator;
import java.util.Map;

/**
 * This class represent the effect that allows the player to choose another card.
 */
public class EffectChooseCard extends Effect{

    /**
     * Colors of the card that can be pick up.
     */
    private DevelopmentCardColor[] developmentCardColors;

    /**
     * Value of the dice to access the tower.
     */
    private int diceValue;

    /**
     * Resources and points earned when the effect is activated.
     */
    private PointsAndResources pointsAndResources;

    /**
     * Number of council privileges
     */
    private PointsAndResources pickUpBonus;

    private int numberOfCouncilPrivileges;

    public EffectChooseCard(){
        super.effectType = this.getClass().getSimpleName();
    }


    public void setDevelopmentCardColors(DevelopmentCardColor[] colors){
        this.developmentCardColors = colors;
    }

    public DevelopmentCardColor[] getDevelopmentCardColors(){
        return this.developmentCardColors;
    }

    public void setDiceValue(int value){
        this.diceValue = value;
    }

    public int getDiceValue(){
        return this.diceValue;
    }

    public void setPointsAndResources(PointsAndResources pointsAndResources){
        this.pointsAndResources = pointsAndResources;
    }

    public PointsAndResources getPointsAndResources(){
        return this.pointsAndResources;
    }

    public void setPickUpBonus(PointsAndResources pickUpBonus){
        this.pickUpBonus = pickUpBonus;
    }

    public PointsAndResources getPickUpBonus(){
        return this.pickUpBonus;
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
        String description = this.effectType;
        description.concat("Dice value: " + diceValue + "\n");
        description.concat("\nColors:\n");
        for(DevelopmentCardColor developmentCardColor : developmentCardColors)
            description.concat(developmentCardColor + "\n");
        description.concat("Resources earnead:\n");
        pointsAndResources.toString();
        description.concat("Resources discount:\n");
        pickUpBonus.toString();
        return description;
    }
}
