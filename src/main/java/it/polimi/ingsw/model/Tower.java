package it.polimi.ingsw.model;

import java.awt.*;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Tower {

    /**
     * Constant value to indicate the number of cells for each tower
     */
    private static final int NUMBER_OF_CELLS = 4;

    private TowerCell towerCells[];

    private DevelopmentCardColor cardColor;

    /**
     * Class constructor
     * @param color
     */
    public Tower(DevelopmentCardColor color){
        this.cardColor = color;

        this.towerCells = new TowerCell[NUMBER_OF_CELLS];
        for(int i = 0; i < NUMBER_OF_CELLS; i++){
            this.towerCells[i] = new TowerCell();
        }
    }

    /**
     * This method checks if the tower is free (all its cells are empty)
     * @return
     */
    public boolean isFree(){
        for(int i = 0; i < NUMBER_OF_CELLS; i++){
            if(!this.towerCells[i].isEmpty()){
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
     * @param atIndex
     * @return
     */
    public TowerCell getTowerCell(Integer atIndex){
        return this.towerCells[atIndex];
    }

    /**
     * This method identifies which type of cards the tower manages
     * @return
     */
    public DevelopmentCardColor getDevelopmentCardType(){
        return this.cardColor;
    }


}
