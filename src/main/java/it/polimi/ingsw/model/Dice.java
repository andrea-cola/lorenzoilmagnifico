package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class Dice implements Serializable{

    /**
     * Map of all dices and relative random values
     */
    private Map<FamilyMemberColor, Integer> values;

    /**
     * Class constructor.
     */
    public Dice(){
        this.values = new EnumMap<>(FamilyMemberColor.class);
    }

    /**
     * Set random value for dices
     */
    public void setValues(){
        Random randomValue = new Random();
        int minValue = 1;
        int maxValue = 6;

        this.values.put(FamilyMemberColor.BLACK, randomValue.nextInt((maxValue - minValue) + 1) + minValue);
        this.values.put(FamilyMemberColor.ORANGE, randomValue.nextInt((maxValue - minValue) + 1) + minValue);
        this.values.put(FamilyMemberColor.WHITE, randomValue.nextInt((maxValue - minValue) + 1) + minValue);
        this.values.put(FamilyMemberColor.NEUTRAL, 0);
    }

    /**
     * Get dices' value
     */
    public Map<FamilyMemberColor, Integer> getValues(){
        return this.values;
    }

}
