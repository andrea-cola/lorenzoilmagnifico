package it.polimi.ingsw.model;

import java.util.*;

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

    @Override
    public String toString(){
        return this.color;
    }

    public static Map<String, PlayerColor> getHashMap(){
        HashMap<String, PlayerColor> colorHashMap = new HashMap<>();
        for(PlayerColor playerColor : PlayerColor.values())
            colorHashMap.put(playerColor.toString(), playerColor);
        return colorHashMap;
    }
}
