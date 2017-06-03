package it.polimi.ingsw.model;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Tower {

    private static final int NUMBER_OF_TOWER_CELLS = 4;

    /**
     * Constant value to indicate the number of cells for each tower
     */

    private TowerCell[] towerCells = new TowerCell[NUMBER_OF_TOWER_CELLS];

    private DevelopmentCardColor towerColor;

    /**
     * Class constructor
     */
    public Tower(DevelopmentCardColor towerColor){
        this.towerColor = towerColor;
        for (int i = 0; i < NUMBER_OF_TOWER_CELLS; i++){
            this.towerCells[i] = new TowerCell(towerColor);
        }
    }

    /**
     * This method checks if the tower is free (all its cells are empty)
     * @return
     */
    public boolean isFree(){
        for(TowerCell cell : this.towerCells){
            if (!cell.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * This method sets the content of the cell (put a card inside the cell)
     */
    public void setTowerCell(int index, DevelopmentCard card){
        this.towerCells[index].setDevelopmentCard(card);
    }

    /**
     * This method gets the content of the cell
     * @param index
     * @return
     */
    public TowerCell getTowerCell(Integer index){
        return this.towerCells[index];
    }

    public int getNumberOfTowerCells(){
        return NUMBER_OF_TOWER_CELLS;
    }

    public DevelopmentCardColor getTowerColor(){
        return this.towerColor;
    }

}
