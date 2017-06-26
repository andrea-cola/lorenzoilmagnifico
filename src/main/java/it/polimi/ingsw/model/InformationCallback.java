package it.polimi.ingsw.model;

import java.util.ArrayList;

public interface InformationCallback {

    ArrayList<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege);

    int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded);

    int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned);

    int choosePickUpDiscounts(String reason, PointsAndResources[] discounts);

    DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount);

}
