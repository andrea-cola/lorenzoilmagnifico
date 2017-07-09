package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
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
     * Flag to check if the leader effect is permanent
     */
    private Boolean permanentAbility;

    /**
     * Flag to check if the leader effect is active
     */
    private Boolean leaderEffectActive;

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

    /**
     * Method to get the leader card name
     * @return
     */
    public String getLeaderCardName(){
        return this.leaderCardName;
    }

    /**
     * Method to set the leader card effect
     * @param leaderEffect
     */
    public void setEffect(LeaderEffect leaderEffect){
        this.effect = leaderEffect;
    }

    /**
     * Method to get the leader card effect
     * @return
     */
    public LeaderEffect getEffect(){
        return this.effect;
    }

    /**
     * Set the state of the leader card to used or not, use this at the end of every turn to reset the value to false
     * @param updateValue
     */
    public void setLeaderEffectActive(Boolean updateValue){
        this.leaderEffectActive = updateValue;
    }

    /**
     * Get the state of the leader card to check if it is used or not
     * @return
     */
    public Boolean getLeaderEffectActive(){
        return this.leaderEffectActive;
    }

    /**
     * This method checks if the player has the requisites to activate a leader card
     * @param player
     * @throws GameException
     */
    public void checkRequisites(Player player) throws GameException{
        //check if the player tries to reuse a leader card with a non permanent effect
        if (this.leaderEffectActive){
            throw new GameException(GameErrorType.LEADER_CARD_ALREADY_USED);
        }

        if(pointsAndResourcesRequisites != null) {
            //check if the player has resources enough
            for (Map.Entry<ResourceType, Integer> entry : this.pointsAndResourcesRequisites.getResources().entrySet()) {
                if (player.getPersonalBoard().getValuables().getResources().get(entry.getKey()) < entry.getValue()) {
                    throw new GameException(GameErrorType.PLAYER_RESOURCES_ERROR);
                }
            }

            //check if the player has points enough
            for (Map.Entry<PointType, Integer> entry : this.pointsAndResourcesRequisites.getPoints().entrySet()) {
                if (player.getPersonalBoard().getValuables().getPoints().get(entry.getKey()) < entry.getValue()) {
                    throw new GameException(GameErrorType.PLAYER_POINTS_ERROR);
                }
            }
        }

        if(cardColorMapRequisites != null) {
            //check if the player has cards enough
            for (Map.Entry<DevelopmentCardColor, Integer> entry : this.cardColorMapRequisites.entrySet()) {
                if (player.getPersonalBoard().getCards(entry.getKey()).size() < entry.getValue()) {
                    throw new GameException(GameErrorType.PLAYER_CARDS_ERROR);
                }
            }
        }

        //no exception thrown -> set the leader effect active to true
        this.leaderEffectActive = true;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder(leaderCardName.toUpperCase() + "\n");
        stringBuilder.append("Active: " + leaderEffectActive + "\n");
        if(pointsAndResourcesRequisites != null)
            stringBuilder.append("Requirements: " + pointsAndResourcesRequisites.toString());
        if(cardColorMapRequisites != null && cardColorMapRequisites.size() > 0){
            stringBuilder.append(" + ");
            for(Map.Entry pair : cardColorMapRequisites.entrySet())
                stringBuilder.append(pair.getKey().toString().toLowerCase() + "=" + pair.getValue());
        }
        stringBuilder.append("\n");
        if(permanentAbility)
            stringBuilder.append("Type: permanent\n");
        else
            stringBuilder.append("Type: once per turn\n");
        if(effect != null)
            stringBuilder.append("Effect: " + effect.toString() + "\n");
        return stringBuilder.toString();
    }
}
