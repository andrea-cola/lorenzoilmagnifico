package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
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
        this.resources = new HashMap<>();
        this.points = new HashMap<>();

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
     * Method to decrease a specific resource in resources map.
     * @param type of the resource to decrease.
     * @param value of the resource to decrease.
     */
    public void decrease(ResourceType type, Integer value){
        this.resources.put(type, this.resources.get(type) - value);
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
     * Method to decrease a specific point in points map.
     * @param type of the point to decrease.
     * @param value of the point to decrease.
     */
    public void decrease(PointType type, Integer value){
        this.points.put(type, this.points.get(type) - value);
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

    /**
     * Return a string with resources and points status.
     * @return a string.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        Iterator it = resources.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if((int)pair.getValue() > 0)
                stringBuilder.append(pair.getKey().toString().toUpperCase() + ": " + pair.getValue().toString() + ", ");
        }
        it = points.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if((int)pair.getValue() > 0)
                stringBuilder.append(pair.getKey().toString().toUpperCase() + ": " + pair.getValue().toString() + ", ");
        }
        return stringBuilder.toString();
    }


    public String doubleCostToString(){
        StringBuilder stringBuilder = new StringBuilder();
        Iterator it = resources.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if((int)pair.getValue() > 0)
                stringBuilder.append(pair.getKey().toString().toUpperCase() + ": " + pair.getValue().toString() + ", ");
        }
        it = points.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if((int)pair.getValue() > 0 && !((PointType)pair.getKey()).equals(PointType.MILITARY))
                stringBuilder.append(pair.getKey().toString().toUpperCase() + ": " + pair.getValue().toString() + ", ");
        }
        return stringBuilder.toString();
    }

}
