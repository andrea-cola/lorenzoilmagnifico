package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.InformationCallback;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represent the effect that allows the player to choose another card.
 */
public class EffectChooseCard extends Effect{

    /**
     * Value of the dice to access the tower.
     */
    private int diceValue;

    /**
     * Colors of the card that can be pick up.
     */
    private DevelopmentCardColor[] developmentCardColors;

    /**
     * Resources and points earned when the effect is activated.
     */
    private PointsAndResources pointsAndResources;

    /**
     * Discount on the card picked up.
     */
    private PointsAndResources pickUpBonus;

    /**
     * Number of council privilege.
     */
    private int numberOfCouncilPrivileges;

    /**
     * Class constructor.
     */
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

    public void setCouncilPrivileges(int councilPrivilege){
        this.numberOfCouncilPrivileges = councilPrivilege;
    }

    public int getCouncilPrivileges(){
        return this.numberOfCouncilPrivileges;
    }

    /**
     * Method to run the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.pointsAndResources.getResources().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.pointsAndResources.getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

        //logica di gestione del privilegio del consiglio
        if (this.numberOfCouncilPrivileges > 0){
            CouncilPrivilege councilPrivilege = new CouncilPrivilege(numberOfCouncilPrivileges);
            councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
        }

        DevelopmentCard developmentCard = informationCallback.chooseNewCard("choose-new-card", developmentCardColors, diceValue, pickUpBonus);
        if(developmentCard.getImmediateEffect() != null)
            developmentCard.getImmediateEffect().runEffect(player, informationCallback);
    }


    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.effectType + ": ");
        stringBuilder.append("dice: " + diceValue + " ");
        for(DevelopmentCardColor developmentCardColor : developmentCardColors)
            stringBuilder.append(developmentCardColor + "+");
        stringBuilder.append("discounts: " + pointsAndResources.toString());
        stringBuilder.append("resources earned: " + pointsAndResources.toString() + "council privileges: " + numberOfCouncilPrivileges);
        return stringBuilder.toString();
    }
}
