package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Created by lorenzo on 23/05/17.
 */
abstract public class ImmediateEffect {

    protected ArrayList<Resource> resources;

    protected ArrayList<Point> points;

    abstract public void runEffect();
}
