package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameException;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class is used to manage amounts of resources and points.
 */
public class PointsAndResources implements Serializable{

    /**
     * Map of resources.
     */
    private Map<ResourceType, Integer> resources;

    /**
     * Map of points.
     */
    private Map<PointType, Integer> points;

    /**
     * Class constructor.
     * Initialize resources and points maps.
     * Each record is set to zero value.
     */
    public PointsAndResources(){
        this.resources = new EnumMap<>(ResourceType.class);
        this.points = new EnumMap<>(PointType.class);

        for (ResourceType type : ResourceType.values())
            this.resources.put(type, 0);

        for (PointType type : PointType.values())
            this.points.put(type, 0);
    }

    /**
     * Method to increase a specific resource in resources map.
     * @param type of the resource to increase.
     * @param value of the resource to increase.
     */
    public void increase(ResourceType type, Integer value){
        this.resources.put(type, this.resources.get(type) + value);
    }

    /**
     * Method to increase a specific point in points map.
     * @param type of the point to increase.
     * @param value of the point to increase.
     */
    public void increase(PointType type, Integer value){
        this.points.put(type, this.points.get(type) + value);
    }

    /**
     * Method to decrease a specific resource in resources map.
     * @param type of the resource to decrease.
     * @param value of the resource to decrease.
     */
    public void decrease(ResourceType type, Integer value) {
        this.resources.put(type, this.resources.get(type) - value);
    }

    /**
     * Method to decrease a specific point in points map.
     * @param type of the point to decrease.
     * @param value of the point to decrease.
     */
    public void decrease(PointType type, Integer value) {
        this.points.put(type, this.points.get(type) - value);
    }

    /**
     * Method to check if the player as valuables enough to pay something
     * @param valuableToDecrease valuables to decrease.
     * @throws GameException if palyer has not resources enough.
     */
    public boolean checkDecrease(PointsAndResources valuableToDecrease){
        for (Map.Entry<ResourceType, Integer> entry: valuableToDecrease.getResources().entrySet())
            if (entry.getValue() > this.resources.get(entry.getKey()))
                return false;

        for (Map.Entry<PointType, Integer> entry: valuableToDecrease.getPoints().entrySet())
            if (entry.getValue() > this.points.get(entry.getKey()))
                return false;
        return true;
    }

    /**
     * If the player has valuables enough to pay something, decrease its valuables
     * @param valuableToDecrease valuables to decrease.
     */
    public void decreaseAll(PointsAndResources valuableToDecrease){
        for (Map.Entry<ResourceType, Integer> entry: valuableToDecrease.getResources().entrySet())
            this.decrease(entry.getKey(), entry.getValue());

        for (Map.Entry<PointType, Integer> entry: valuableToDecrease.getPoints().entrySet())
            this.decrease(entry.getKey(), entry.getValue());
    }

    /**
     * Method to get the resources map.
     * @return the resources map.
     */
    public Map<ResourceType, Integer> getResources(){
        return this.resources;
    }

    /**
     * Method to get the points map.
     * @return the points map.
     */
    public Map<PointType, Integer> getPoints(){
        return this.points;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("");
        for(Map.Entry pair : resources.entrySet())
            if((int)pair.getValue() != 0)
                stringBuilder.append(pair.getKey().toString().toLowerCase() + "=" + pair.getValue() + " ");
        for(Map.Entry pair : points.entrySet())
            if((int)pair.getValue() != 0)
                stringBuilder.append(pair.getKey().toString().toLowerCase() + "=" + pair.getValue() + " ");
        return stringBuilder.toString();
    }

}
