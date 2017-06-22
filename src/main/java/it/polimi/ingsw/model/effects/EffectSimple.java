package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.InformationCallback;

import java.util.List;
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
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

        //logica di gestione del privilegio del consiglio
        if (this.numberOfCouncilPrivileges > 0){
            CouncilPrivilege councilPrivilege = new CouncilPrivilege();
            for (int i = 0; i < this.numberOfCouncilPrivileges; i++){
                //informationCallback.chooseCouncilPrivilege();

            }
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
