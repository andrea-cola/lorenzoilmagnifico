package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.LeaderEffect;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents a leader card abstraction.
 */
public class LeaderCard implements Serializable{

    /**
     * Leader card name
     */
    private String leaderCardName;

    /**
     * Leader card description
     */
    private String leaderCardDescription;

    /**
     * Valuables requested to activate the leader effect
     */
    private PointsAndResources pointsAndResourcesRequisites;

    /**
     * Cards requested to activate the leader effect
     */
    private Map<DevelopmentCardColor, Integer> cardColorMapRequisites;

    /**
     * Leader effect instance
     */
    private LeaderEffect effect;


    public void setLeaderCardName(String leaderCardName){
        this.leaderCardName = leaderCardName;
    }

    public String getLeaderCardName(){
        return this.leaderCardName;
    }

    public void setLeaderCardDescription(String leaderCardDescription){
        this.leaderCardDescription = leaderCardDescription;
    }

    public String getLeaderCardDescription(){
        return this.leaderCardDescription;
    }

    public void setPointsAndResourcesRequisites(PointsAndResources pointsAndResourcesRequisites){
        this.pointsAndResourcesRequisites = pointsAndResourcesRequisites;
    }

    public PointsAndResources getPointsAndResourcesRequisites(){
        return this.pointsAndResourcesRequisites;
    }

    public void setCardColorMapRequisites(Map<DevelopmentCardColor, Integer> cardColorMapRequisites){
        this.cardColorMapRequisites = cardColorMapRequisites;
    }

    public Map<DevelopmentCardColor, Integer> getCardColorMapRequisites(){
        return this.cardColorMapRequisites;
    }

    public void setEffect(LeaderEffect leaderEffect){
        this.effect = leaderEffect;
    }

    public LeaderEffect getEffect(){
        return this.effect;
    }
}
