package it.polimi.ingsw.model;

import java.util.List;

public interface InformationCallback {

    List<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege);

    int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded);

    int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned);

    int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts);

    DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount);

    LeaderCard copyAnotherLeaderCard(String reason);

    FamilyMemberColor choiceLeaderDice(String reason);

}
