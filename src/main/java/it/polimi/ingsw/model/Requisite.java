package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Requisite {

    private ArrayList<Resource> resources;

    private ArrayList<Point> points;

    public Requisite(){
        this.resources = new ArrayList<>();
        this.points = new ArrayList<>();
    }

    public void setResources(ArrayList<Resource> resources){
        this.resources = resources;
    }

    public void setPoints(ArrayList<Point> points){
        this.points = points;
    }

    public ArrayList<Resource> getResources(){
        return this.resources;
    }

    public ArrayList<Point> getPoints(){
        return this.points;
    }

}

