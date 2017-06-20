package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;

import java.io.Serializable;

/**
 * This class represents the market zone on the main board
 */
public class Market implements Serializable{

    private static final int MIN_FAMILY_MEMBER_VALUE = 1;

    /**
     * Market cells
     */
    private MarketCell[] marketCells;

    /**

     * Constructor of the markets
     */
    public Market(int numberOfCells){
        this.marketCells = new MarketCell[numberOfCells];
        for(int i = 0; i < numberOfCells; i++){
            this.marketCells[i] = new MarketCell(MIN_FAMILY_MEMBER_VALUE);
        }
    }

    /**
     * Get a specific market cell.
     * @param index of the cell.
     * @return a market cell.
     */
    public MarketCell getMarketCell(int index){
        return this.marketCells[index];
    }

    /**
     * Set the market cells on the main board
     * @param cells of the market
     */
    public void setMarketCells(MarketCell[] cells){
        this.marketCells = cells;
    }

}
