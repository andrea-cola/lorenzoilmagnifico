package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;

import java.io.Serializable;

/**
 * This class represents the market zone on the main board
 */
public class Market implements Serializable{

    /**
     * Market cells
     */
    private MarketCell[] marketCells;

    /**
     * Constructor of the markets
     */
    public Market(int numberOfCells, MarketCell[] marketCells){
        this.marketCells = new MarketCell[numberOfCells];
        int i = 0;
        for(MarketCell marketCell : marketCells) {
            this.marketCells[i] = new MarketCell(marketCell.getMinFamilyMemberValue(), marketCell.getMarketCellImmediateEffect());
            i++;
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

    public MarketCell[] getMarketCells(){
        return this.marketCells;
    }

}
