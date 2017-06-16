package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This enumeration represents the possible colors of the player board
 */
public enum PlayerColor {

    /**
     * Green
     */
    GREEN("green"),

    /**
     * Blue
     */
    BLUE("blue"),

    /**
     * Yellow
     */
    YELLOW("yellow"),

    /**
     * Red
     */
    RED("red");

    /**
     * Enumeration message.
     */
    private final String color;


    PlayerColor(String color) {
        this.color = color;
    }

    public String toString(){
        return this.color;
    }

    public static List<String> getValues() {
        ArrayList<String> colors = new ArrayList<>();
        for(PlayerColor playerColor : PlayerColor.values())
            colors.add(playerColor.toString());
        return Collections.unmodifiableList(colors);
    }
}
