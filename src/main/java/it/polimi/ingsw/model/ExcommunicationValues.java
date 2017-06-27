package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ExcommunicationValues implements Serializable{

    private Map<PointType, Integer> normalPointsMalus = new HashMap<>();

    private Map<ResourceType, Integer> normalResourcesMalus = new HashMap<>();

    private Map<ActionType, Integer> harvestProductionDiceMalus = new HashMap<>();

    private Map<DevelopmentCardColor, Integer> developmentCardDiceMalus = new HashMap<>();

    private Boolean marketIsAvailable = true;

    private Boolean skipFirstTurn = false;

    private Integer numberOfSlaves = 1;

    private Map<DevelopmentCardColor, Boolean> developmentCardGetFinalPoints = new HashMap<>();

    private Map<PointType, Integer> finalPointsIndexMalus = new HashMap<>();

    private Map<ResourceType, Integer> finalResourcesIndexMalus = new HashMap<>();

    private Map<ResourceType, Integer> finalResourcesDevCardIndexMalus = new HashMap<>();


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

    //set
    public void increaseNormalValuablesMalus(PointType pointType, Integer value){
        this.normalPointsMalus.put(pointType, this.normalPointsMalus.get(pointType) + value);
    }

    public void increaseNormalValuablesMalus(ResourceType resourceType, Integer value){
        this.normalResourcesMalus.put(resourceType, this.normalResourcesMalus.get(resourceType) + value);
    }

    public void increaseFinalValuablesIndexMalus(PointType pointType, Integer value){
        this.finalPointsIndexMalus.put(pointType, this.finalPointsIndexMalus.get(pointType) + value);
    }

    public void increaseFinalValuablesIndexMalus(ResourceType resourceType, Integer value){
        this.finalResourcesIndexMalus.put(resourceType, this.finalResourcesIndexMalus.get(resourceType) + value);
    }

    public void setHarvestProductionDiceMalus(ActionType actionType, Integer value){
        this.harvestProductionDiceMalus.put(actionType, this.harvestProductionDiceMalus.get(actionType) + value);
    }

    public void setDevelopmentCardDiceMalus(DevelopmentCardColor cardColor, Integer value){
        this.developmentCardDiceMalus.put(cardColor, this.developmentCardDiceMalus.get(cardColor) + value);
    }

    public void setDevelopmentCardGetFinalPoints(DevelopmentCardColor cardColor, Boolean value){
        this.developmentCardGetFinalPoints.replace(cardColor, value);
    }

    public void setFinalResourcesDevCardIndexMalus(ResourceType resourceType, Integer value){
        this.finalResourcesDevCardIndexMalus.put(resourceType, this.finalResourcesDevCardIndexMalus.get(resourceType) + value);
    }

    public void setMarketIsAvailable(Boolean value){
        this.marketIsAvailable = value;
    }

    public void setSkipFirstTurn(Boolean value){
        this.skipFirstTurn = value;
    }

    public void setNumberOfSlaves(Integer number){
        this.numberOfSlaves = number;
    }


    //get
    public Map<PointType, Integer> getNormalPointsMalus(){
        return this.normalPointsMalus;
    }

    public Map<ResourceType, Integer> getNormalResourcesMalus(){
        return this.normalResourcesMalus;
    }

    public Map<ActionType, Integer> getHarvestProductionDiceMalus(){
        return this.harvestProductionDiceMalus;
    }

    public Map<DevelopmentCardColor, Integer> getDevelopmentCardDiceMalus(){
        return this.developmentCardDiceMalus;
    }

    public Boolean getMarketIsAvailable(){
        return this.marketIsAvailable;
    }

    public Integer getNumberOfSlaves(){
        return this.numberOfSlaves;
    }

    public Map<DevelopmentCardColor, Boolean> getDevelopmentCardGetFinalPoints(){
        return this.developmentCardGetFinalPoints;
    }

    public Map<PointType, Integer> getFinalPointsIndexMalus(){
        return this.finalPointsIndexMalus;
    }

    public Boolean getSkipFirstTurn(){
        return this.skipFirstTurn;
    }

    public Map<ResourceType, Integer> getFinalResourcesIndexMalus(){
        return this.finalResourcesIndexMalus;
    }

    public Map<ResourceType, Integer> getFinalResourcesDevCardIndexMalus(){
        return this.finalResourcesDevCardIndexMalus;
    }


}
