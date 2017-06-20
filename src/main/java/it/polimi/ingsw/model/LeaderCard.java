package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.LeaderEffect;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents a leader card abstraction.
 */
public class LeaderCard implements Serializable{

    private String leaderCardName;

    private String leaderCardDescription;

    private PointsAndResources pointsAndResourcesRequisites;

    private Map<DevelopmentCardColor, Integer> cardColorMapRequisites;

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
