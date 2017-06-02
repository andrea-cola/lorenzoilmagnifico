package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lorenzo on 31/05/17.
 */
public class PointsAndResources {

    private Map<ResourceType, Integer> resources;

    private Map<PointType, Integer> points;

    public PointsAndResources(){
        this.resources = new HashMap<>();
        this.points = new HashMap<>();

        for (ResourceType type : ResourceType.values()){
            this.resources.put(type, 0);
        }

        for (PointType type : PointType.values()){
            this.points.put(type, 0);
        }
    }

    public void increase(ResourceType type, Integer value){
        this.resources.put(type, this.resources.get(type) + value);
    }

    public void decrease(ResourceType type, Integer value){
        this.resources.put(type, this.resources.get(type) - value);
    }


    public void increase(PointType type, Integer value){
        this.points.put(type, this.points.get(type) + value);
    }

    public void decrease(PointType type, Integer value){
        this.points.put(type, this.points.get(type) - value);
    }


    public Map<ResourceType, Integer> getResources(){
        return this.resources;
    }

    public Map<PointType, Integer> getPoints(){
        return this.points;
    }
}
