package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.InformationCallback;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represent the immediate simple effect.
 * A immediate simple effect allow the player to get resources and points.
 */
public class EffectSimple extends Effect{

    /**
     * Points and resources that user obtain.
     */
    private PointsAndResources valuable;

    /**
     * Council privilege object.
     */
    private int numberOfCouncilPrivileges;

    /**
     * Class constructor.
     */
    public EffectSimple(){
        this.valuable = new PointsAndResources();
        super.effectType = this.getClass().getSimpleName();
    }

    /**
     * Return points and resources of the effect.
     * @return points and resources.
     */
    public PointsAndResources getValuable(){
        return this.valuable;
    }

    /**
     * Return council privilege object.
     * @return council privilege object.
     */
    public int getCouncilPrivilege(){
        return this.numberOfCouncilPrivileges;
    }

    /**
     * Method to run the effect.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        //check if the player has the leader card for this effect and if it is active
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCardWithName("Santa Rita");
        if (leaderCard != null && leaderCard.getLeaderEffectActive()){
            updateResources(player, 2);
        }else {
            updateResources(player, 1);
        }

        updatePoints(player);

        //here the logic to manage the player's council privilege decision
        if (this.numberOfCouncilPrivileges > 0){
            CouncilPrivilege councilPrivilege = new CouncilPrivilege(numberOfCouncilPrivileges);
            councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
        }
    }

    /**
     * This method updates player's resources
     * @param player
     * @param multiplicatorValue
     */
    private void updateResources(Player player, int multiplicatorValue){
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet()) {
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue() * multiplicatorValue);
        }
    }

    /**
     * This method updates player's points
     * @param player
     */
    private void updatePoints(Player player){
        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet()) {
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.effectType + ": ");
        stringBuilder.append("resources earned: " + valuable.toString() + " council privileges: " + numberOfCouncilPrivileges);
        return stringBuilder.toString();
    }
}
