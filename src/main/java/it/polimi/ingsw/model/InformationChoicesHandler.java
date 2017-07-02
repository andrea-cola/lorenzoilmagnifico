package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformationChoicesHandler implements InformationCallback {

    private Map<String, Object> decisions;

    public InformationChoicesHandler(){
        decisions = new HashMap<>();
    }

    public void setDecisions(Map<String, Object> playerChoices){
        this.decisions = playerChoices;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        ArrayList<Privilege> privileges = (ArrayList<Privilege>)decisions.get(reason);
        ArrayList<Privilege> privilegesNeeded = new ArrayList<>(privileges.subList(0, councilPrivilege.getNumberOfCouncilPrivileges()));
        for(int i = 0; i < councilPrivilege.getNumberOfCouncilPrivileges(); i++)
            privileges.remove(i);
        if(privileges.size() == 0)
            decisions.remove(reason);
        else
            decisions.put(reason, privileges);
        return privilegesNeeded;
    }

    @Override
    public int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded) {
        return (int)decisions.get("double-cost");
    }

    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned) {
        return (int)decisions.get(card + ":double");
    }

    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts){
        return (int)decisions.get(reason);
    }

    @Override
    public DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        return (DevelopmentCard)decisions.get(reason);
    }

}