package it.polimi.ingsw.model;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents tower abstraction.
 */
public class Tower {

    /**
     * Color of the tower. It specifies the type of the contained.
     */
    private DevelopmentCardColor towerColor;

    /**
     * Array of tower cells.
     */
    private TowerCell[] towerCells;

    /**
     * Set the color of the tower.
     * @param color of the tower.
     */
    public void setTowerColor(DevelopmentCardColor color){
        this.towerColor = color;
    }

    /**
     * Get tower color.
     * @return tower color.
     */
    public DevelopmentCardColor getTowerColor(){
        return this.towerColor;
    }

    /**
     * Set tower cells.
     * @param towerCells of the tower.
     */
    public void setTowerCells(TowerCell[] towerCells){
        this.towerCells = towerCells;
    }

    /**
     * Get tower cells.
     * @return TowerCell array.
     */
    public TowerCell[] getTowerCells(){
        return this.towerCells;
    }

    /**
     * This method checks if the tower is free (all its cells are empty)
     * @return
     */
    public boolean isFree(){
        for(TowerCell cell : this.towerCells)
            if (!cell.isEmpty())
                return false;
        return true;
    }

    /**
     * This method sets the content of the cell (put a card inside the cell).
     */
    public void setTowerCell(int index, DevelopmentCard card){
        this.towerCells[index].setDevelopmentCard(card);
    }

    /**
     * This method gets the content of the cell (get the card contained in the cell).
     * @param index of the cells.
     * @return TowerCell object.
     */
    public TowerCell getTowerCell(Integer index){
        return this.towerCells[index];
    }



}
