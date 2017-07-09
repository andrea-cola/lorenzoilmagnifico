package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to make available on server every player choice.
 */
public class InformationChoicesHandler implements InformationCallback {

    /**
     * Map containg all user choice.
     */
    private Map<String, Object> decisions;

    /**
     * Class constructor.
     */
    public InformationChoicesHandler(){
        decisions = new HashMap<>();
    }

    /**
     * Set map of the choices.
     * @param playerChoices map.
     */
    public void setDecisions(Map<String, Object> playerChoices){
        this.decisions = playerChoices;
    }

    public Object getDecisions(String s){
        return decisions.get(s);
    }

    /**
     * Method to choose the council privilege to get
     * @param reason
     * @param councilPrivilege
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        ArrayList<Privilege> privileges = (ArrayList<Privilege>)decisions.get(reason);
        ArrayList<Privilege> privilegesNeeded = new ArrayList<>(privileges.subList(0, councilPrivilege.getNumberOfCouncilPrivileges()));
        for(int i = 0; i < councilPrivilege.getNumberOfCouncilPrivileges(); i++)
            privileges.remove(0);
        if(!privileges.isEmpty())
            decisions.remove(reason);
        else
            decisions.put(reason, privileges);
        return privilegesNeeded;
    }

    /**
     * Method to choose how to pay a card
     * @param pointsAndResources
     * @param militaryPointsGiven
     * @param militaryPointsNeeded
     * @return
     */
    @Override
    public int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded) {
        return (int)decisions.get("double-cost");
    }

    /**
     * Method to choose what type of conversion run in ExchangeEffect
     * @param card
     * @param valuableToPay
     * @param valuableEarned
     * @return
     */
    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned) {
        return (int)decisions.get(card);
    }

    /**
     * Method to choose the discount
     * @param reason
     * @param discounts
     * @return
     */
    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts){
        return (int)decisions.get(reason);
    }

    /**
     * Method to choose a new card
     * @param reason
     * @param developmentCardColors
     * @param diceValue
     * @param discount
     * @return
     */
    @Override
    public DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        return (DevelopmentCard)decisions.get(reason);
    }

    /**
     * Method to copy the effect of another leader card (LELorenzoDeMedici effect)
     * @param reason
     * @return
     */
    @Override
    public LeaderCard copyAnotherLeaderCard(String reason) {
        return (LeaderCard)decisions.get(reason);
    }

    /**
     * Method to choose which family member value increase
     * @param reason
     * @return
     */
    @Override
    public FamilyMemberColor choiceLeaderDice(String reason) {
        return (FamilyMemberColor)decisions.get(reason);
    }

}