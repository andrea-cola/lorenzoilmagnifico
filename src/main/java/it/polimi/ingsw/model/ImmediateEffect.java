package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Created by lorenzo on 23/05/17.
 */
abstract public class ImmediateEffect {

    protected ArrayList<Resource> resources = new ArrayList<>();

    protected ArrayList<Point> points = new ArrayList<>();

    abstract public void runEffect();

    public void setResources(ArrayList<Resource> resources){
        this.resources = resources;
    }

    public ArrayList<Resource> getResources(){
        return this.resources;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public ArrayList<Point> getPoints(){
        return this.points;
    }

}
