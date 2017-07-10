package it.polimi.ingsw.model;

import java.util.List;

/**
 * This class is an interface to manage actions that requires multiple interactions with the user
 */
public interface InformationCallback {

    /**
     * Method to choose the council privilege to get
     * @param reason
     * @param councilPrivilege
     * @return
     */
    List<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege);

    /**
     * Method to choose how to pay a card
     * @param pointsAndResources
     * @param militaryPointsGiven
     * @param militaryPointsNeeded
     * @return
     */
    int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded);

    /**
     * Method to choose what type of conversion run in ExchangeEffect
     * @param card
     * @param valuableToPay
     * @param valuableEarned
     * @return
     */
    int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned);

    /**
     * Method to choose the discount
     * @param reason
     * @param discounts
     * @return
     */
    int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts);

    /**
     * Method to choose a new card
     * @param reason
     * @param developmentCardColors
     * @param diceValue
     * @param discount
     * @return
     */
    DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount);

    /**
     * Method to copy the effect of another leader card (LELorenzoDeMedici effect)
     * @param reason
     * @return
     */
    LeaderCard copyAnotherLeaderCard(String reason);

    /**
     * Method to choose which family member value increase
     * @param reason
     * @return
     */
    FamilyMemberColor choiceLeaderDice(String reason);

}
