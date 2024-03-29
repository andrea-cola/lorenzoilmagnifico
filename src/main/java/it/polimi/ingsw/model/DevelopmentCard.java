package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represent the abstraction of the development card.
 */
public class DevelopmentCard implements Serializable{

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Card ID.
     */
    private int id;

    /**
     * Game period of the card.
     */
    private int period;

    /**
     * Color of the card. The color specifies also the type.
     */
    private DevelopmentCardColor cardColor;

    /**
     * Cost of the card in points and resources.
     */
    private PointsAndResources cost;

    /**
     * This flag indicates if the player have to choose one requisite
     * among those available.
     */
    private boolean multipleRequisiteSelectionEnabled;

    /**
     * Amount of military points required.
     */
    private int militaryPointsRequired;

    /**
     * Amount of military points given.
     */
    private int militaryPointsToPay;

    /**
     * Immediate effect of the card.
     */
    private Effect immediateEffect;

    /**
     * Permanent effect of the card.
     */
    private Effect permanentEffect;

    /**
     * Class constructor.
     */
    public DevelopmentCard(String name, int id, int period, DevelopmentCardColor developmentCardColor, PointsAndResources cost,
                           boolean multipleRequisiteSelectionEnabled, int militaryPointsRequired,
                           Effect immediateEffect, Effect permanentEffect){
        this.name = name;
        this.id = id;
        this.period = period;
        this.cardColor = developmentCardColor;
        this.cost = cost;
        this.multipleRequisiteSelectionEnabled = multipleRequisiteSelectionEnabled;
        this.militaryPointsRequired =militaryPointsRequired;
        this.immediateEffect = immediateEffect;
        this.permanentEffect = permanentEffect;
    }

    /**
     * Get the card ID.
     * @return the id.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the period.
     * @return the period.
     */
    public int getPeriod(){
        return this.period;
    }

    /**
     * Get the effectType of the card.
     * @return the effectType.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the color of the card.
     * @return the color.
     */
    public DevelopmentCardColor getColor(){
        return  this.cardColor;
    }

    /**
     * Get the cost to pick up the card.
     * @return the cost.
     */
    public PointsAndResources getCost(){
        return this.cost;
    }

    /**
     * Get the immediate effect of the card.
     * @return the immediate effect.
     */
    public Effect getImmediateEffect(){
        return this.immediateEffect;
    }

    /**
     * Get the permanent effect of the card.
     * @return the permanent effect
     */
    public Effect getPermanentEffect(){
        return this.permanentEffect;
    }

    /**
     * Get the flag of multiple requisite selection.
     * @return a boolean flag.
     */
    public boolean getMultipleRequisiteSelectionEnabled(){
        return this.multipleRequisiteSelectionEnabled;
    }

    /**
     * Get the military points required.
     * @return amount of military points.
     */
    public int getMilitaryPointsRequired(){
        return this.militaryPointsRequired;
    }

    /**
     * This method is used to pay the card
     * @param player the player that wants to perform the action
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     */
    public void payCost(Player player, InformationCallback informationCallback){
        if (multipleRequisiteSelectionEnabled) {
            if (checkNullResources()){
                player.getPersonalBoard().getValuables().decrease(PointType.MILITARY, militaryPointsToPay);
            }else if (militaryPointsRequired <= player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY) &&
                    !player.getPersonalBoard().getValuables().checkDecrease(cost)) {
                player.getPersonalBoard().getValuables().decrease(PointType.MILITARY, militaryPointsToPay);
            } else if(militaryPointsRequired > player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY) &&
                    player.getPersonalBoard().getValuables().checkDecrease(cost)) {
                player.getPersonalBoard().getValuables().decreaseAll(cost);
            } else {
                int choice = informationCallback.chooseDoubleCost(cost, militaryPointsToPay, militaryPointsRequired);
                if(choice == 2)
                    player.getPersonalBoard().getValuables().decrease(PointType.MILITARY, militaryPointsToPay);
                else
                    player.getPersonalBoard().getValuables().decreaseAll(cost);
            }
        } else {
            player.getPersonalBoard().getValuables().decreaseAll(cost);
        }
    }

    /**
     * This method is used to check if there are no resources to pay for the card
     * @return true if resources are null, false if there is something greater than 0
     */
    private boolean checkNullResources(){
        for(Map.Entry<ResourceType, Integer> entry : cost.getResources().entrySet())
            if(entry.getValue() > 0)
                return false;
        for(Map.Entry<PointType, Integer> entry : cost.getPoints().entrySet())
            if(entry.getValue() > 0)
                return false;
        return true;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name.toUpperCase() + " (" + cardColor + ")\nRequirements: " + cost.toString() + "\n");
        if(multipleRequisiteSelectionEnabled)
            stringBuilder.append("Alternative cost (requirements, cost): " + militaryPointsRequired + ", " + militaryPointsToPay + "\n");
        if(immediateEffect != null)
            stringBuilder.append("Immediate effect: " + immediateEffect.toString() + "\n");
        if(permanentEffect != null)
            stringBuilder.append("Permanent effect " + permanentEffect.toString() + "\n");
        return stringBuilder.toString();
    }
}
