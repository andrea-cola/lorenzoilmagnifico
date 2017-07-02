package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.InformationCallback;

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
        super.setEffectType(this.getClass().getSimpleName());
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
        if(developmentCard != null) {
            player.getPersonalBoard().addCard(developmentCard);
            if (developmentCard.getImmediateEffect() != null)
                developmentCard.getImmediateEffect().runEffect(player, informationCallback);
        }
    }


    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dice: " + diceValue + " choose a new card with one of the following colors ( ");
        for(DevelopmentCardColor developmentCardColor : developmentCardColors)
            stringBuilder.append(developmentCardColor + " ");
        stringBuilder.append(" ) ");
        stringBuilder.append("DISCOUNTS:( " + pointsAndResources.toString() + ") ");
        stringBuilder.append("RESOURCES EARNED:( " + pointsAndResources.toString() + "COUNCIL PRIVILEGES: " + numberOfCouncilPrivileges + " )");
        return stringBuilder.toString();
    }
}
