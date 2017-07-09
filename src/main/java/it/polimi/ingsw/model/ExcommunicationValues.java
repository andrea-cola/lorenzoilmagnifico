package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class collects all the malus values reached by the player with excommunications during the game
 */
public class ExcommunicationValues implements Serializable{

    /**
     * Malus on points
     */
    private Map<PointType, Integer> normalPointsMalus = new EnumMap<>(PointType.class);

    /**
     * Malus on resources
     */
    private Map<ResourceType, Integer> normalResourcesMalus = new EnumMap<>(ResourceType.class);

    /**
     * Malus on the harvest production dice
     */
    private Map<ActionType, Integer> harvestProductionDiceMalus = new EnumMap<>(ActionType.class);

    /**
     * Malus on the development card dice
     */
    private Map<DevelopmentCardColor, Integer> developmentCardDiceMalus = new EnumMap<>(DevelopmentCardColor.class);

    /**
     * Market is available for the player
     */
    private Boolean marketIsAvailable = true;

    /**
     * The player has to skip the first turn
     */
    private Boolean skipFirstTurn = false;

    /**
     * The number of slaves necessary to increase the dice value of 1
     */
    private Integer numberOfSlaves = 1;

    /**
     * Get the final points for a particular type of development cards
     */
    private Map<DevelopmentCardColor, Boolean> developmentCardGetFinalPoints = new EnumMap<>(DevelopmentCardColor.class);

    /**
     * Index malus on final points
     */
    private Map<PointType, Integer> finalPointsIndexMalus = new EnumMap<>(PointType.class);

    /**
     * Index malus on final resources
     */
    private Map<ResourceType, Integer> finalResourcesIndexMalus = new EnumMap<>(ResourceType.class);

    /**
     * Index malus on final resources of development card
     */
    private Map<ResourceType, Integer> finalResourcesDevCardIndexMalus = new EnumMap<>(ResourceType.class);


    /**
     * Class constructor
     */
    public ExcommunicationValues(){
        for (PointType pointType : PointType.values()){
            this.normalPointsMalus.put(pointType, 0);
            this.finalPointsIndexMalus.put(pointType, 0);
        }

        for (ResourceType resourceType : ResourceType.values()){
            this.normalResourcesMalus.put(resourceType, 0);
            this.finalResourcesIndexMalus.put(resourceType, 0);
            this.finalResourcesDevCardIndexMalus.put(resourceType, 0);
        }

        for (ActionType actionType : ActionType.values()){
            this.harvestProductionDiceMalus.put(actionType, 0);
        }

        for (DevelopmentCardColor cardColor : DevelopmentCardColor.values()){
            this.developmentCardDiceMalus.put(cardColor, 0);
            this.developmentCardGetFinalPoints.put(cardColor, true);
        }
    }

    /**
     * Method to increase the points malus
     * @param pointType
     * @param value
     */
    public void increaseNormalValuablesMalus(PointType pointType, Integer value){
        this.normalPointsMalus.put(pointType, this.normalPointsMalus.get(pointType) + value);
    }

    /**
     * Method to increase the resources malus
     * @param resourceType
     * @param value
     */
    public void increaseNormalValuablesMalus(ResourceType resourceType, Integer value){
        this.normalResourcesMalus.put(resourceType, this.normalResourcesMalus.get(resourceType) + value);
    }

    /**
     * Method to increase the index malus for final points
     * @param pointType
     * @param value
     */
    public void increaseFinalValuablesIndexMalus(PointType pointType, Integer value){
        this.finalPointsIndexMalus.put(pointType, this.finalPointsIndexMalus.get(pointType) + value);
    }

    /**
     * Method to increase the index malus for final resources
     * @param resourceType
     * @param value
     */
    public void increaseFinalValuablesIndexMalus(ResourceType resourceType, Integer value){
        this.finalResourcesIndexMalus.put(resourceType, this.finalResourcesIndexMalus.get(resourceType) + value);
    }

    /**
     * Method to set the harvest production dice malus
     * @param actionType
     * @param value
     */
    public void setHarvestProductionDiceMalus(ActionType actionType, Integer value){
        this.harvestProductionDiceMalus.put(actionType, this.harvestProductionDiceMalus.get(actionType) + value);
    }

    /**
     * Method to set the development card dice malus
     * @param cardColor
     * @param value
     */
    public void setDevelopmentCardDiceMalus(DevelopmentCardColor cardColor, Integer value){
        this.developmentCardDiceMalus.put(cardColor, this.developmentCardDiceMalus.get(cardColor) + value);
    }

    /**
     * Method to set if the player can get the final points for a particular type of development cards
     * @param cardColor
     * @param value
     */
    public void setDevelopmentCardGetFinalPoints(DevelopmentCardColor cardColor, Boolean value){
        this.developmentCardGetFinalPoints.replace(cardColor, value);
    }

    /**
     * Method to set the final resources index malus for development cards
     * @param resourceType
     * @param value
     */
    public void setFinalResourcesDevCardIndexMalus(ResourceType resourceType, Integer value){
        this.finalResourcesDevCardIndexMalus.put(resourceType, this.finalResourcesDevCardIndexMalus.get(resourceType) + value);
    }

    /**
     * Method to set the state of the market
     * @param value
     */
    public void setMarketIsAvailable(Boolean value){
        this.marketIsAvailable = value;
    }

    /**
     * Method to set the turn management for a particular player
     * @param value
     */
    public void setSkipFirstTurn(Boolean value){
        this.skipFirstTurn = value;
    }

    /**
     * Method to set the number of slaves the user has to use to increment dice values of 1
     * @param number
     */
    public void setNumberOfSlaves(Integer number){
        this.numberOfSlaves = number;
    }


    /**
     * Method to get the points malus
     * @return
     */
    public Map<PointType, Integer> getNormalPointsMalus(){
        return this.normalPointsMalus;
    }

    /**
     * Method to get the resources malus
     * @return
     */
    public Map<ResourceType, Integer> getNormalResourcesMalus(){
        return this.normalResourcesMalus;
    }

    /**
     * Method to get the harvest production dice malus
     * @return
     */
    public Map<ActionType, Integer> getHarvestProductionDiceMalus(){
        return this.harvestProductionDiceMalus;
    }

    /**
     * Method to get the development card dice malus
     * @return
     */
    public Map<DevelopmentCardColor, Integer> getDevelopmentCardDiceMalus(){
        return this.developmentCardDiceMalus;
    }

    /**
     * Method to check if the market is available
     * @return
     */
    public Boolean getMarketIsAvailable(){
        return this.marketIsAvailable;
    }

    /**
     * Method to get the number of slaves the user has to use to increment dice values of 1
     * @return
     */
    public Integer getNumberOfSlaves(){
        return this.numberOfSlaves;
    }

    /**
     *  Method to check if the player can get the final points for a particular type of development cards
     * @return
     */
    public Map<DevelopmentCardColor, Boolean> getDevelopmentCardGetFinalPoints(){
        return this.developmentCardGetFinalPoints;
    }

    /**
     * Method to get the index malus for final points
     * @return
     */
    public Map<PointType, Integer> getFinalPointsIndexMalus(){
        return this.finalPointsIndexMalus;
    }

    /**
     * Check if the player has to skip the first turn
     * @return
     */
    public Boolean getSkipFirstMove(){
        return this.skipFirstTurn;
    }

    /**
     * Method to get the index malus for final resources
     * @return
     */
    public Map<ResourceType, Integer> getFinalResourcesIndexMalus(){
        return this.finalResourcesIndexMalus;
    }

    /**
     * Method to get the final resources index malus for development cards
     * @return
     */
    public Map<ResourceType, Integer> getFinalResourcesDevCardIndexMalus(){
        return this.finalResourcesDevCardIndexMalus;
    }


}
