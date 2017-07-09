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
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCardWithName("Santa Rita");
        if (leaderCard != null && leaderCard.getLeaderEffectActive())
            updateResources(player, 2);
        else
            updateResources(player, 1);
        updatePoints(player);

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
     * This method updates player's resources
     * @param player
     * @param multiplicatorValue
     */
    private void updateResources(Player player, int multiplicatorValue){
        for (Map.Entry<ResourceType, Integer> entry: this.pointsAndResources.getResources().entrySet()) {
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue() * multiplicatorValue);
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
        }
    }

    /**
     * This method updates player's points
     * @param player
     */
    private void updatePoints(Player player){
        for (Map.Entry<PointType, Integer> entry: this.pointsAndResources.getPoints().entrySet()) {
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dice = " + diceValue + " to choose a new card with one of the following colors ");
        for(DevelopmentCardColor developmentCardColor : developmentCardColors)
            stringBuilder.append(developmentCardColor + " ");
        stringBuilder.append("discount: " + pointsAndResources.toString() + " ");
        stringBuilder.append("resourced to earn immediatly: " + pointsAndResources.toString());
        if(numberOfCouncilPrivileges > 0)
            stringBuilder.append("and " + numberOfCouncilPrivileges + " council privilege");
        return stringBuilder.toString();
    }
}
