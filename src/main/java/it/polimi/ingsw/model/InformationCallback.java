package it.polimi.ingsw.model;

import java.util.List;

/**
 * This class is an interface to manage actions that requires multiple interactions with the user
 */
public interface InformationCallback {

    /**
     * Method to choose the council privilege to get
     */
    List<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege);

    /**
     * Method to choose how to pay a card
     */
    int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded);

    /**
     * Method to choose what type of conversion run in ExchangeEffect
     */
    int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned);

    /**
     * Method to choose the discount
     */
    int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts);

    /**
     * Method to choose a new card
     */
    DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount);

    /**
     * Method to copy the effect of another leader card (LELorenzoDeMedici effect)
     */
    LeaderCard copyAnotherLeaderCard(String reason);

    /**
     * Method to choose which family member value increase
     */
    FamilyMemberColor choiceLeaderDice(String reason);

}
