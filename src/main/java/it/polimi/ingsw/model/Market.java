package it.polimi.ingsw.model;

/**
 * This class represent market abstraction.
 */
public class Market {

    /**
     * Market cells.
     */
    private MarketCell[] marketCells;

    /**
     * Get a specific market cell.
     * @param index of the cell.
     * @return a market cell.
     */
    public MarketCell getMarketCell(int index){
        return this.marketCells[index];
    }

    public void setMarketCells(MarketCell[] cells){
        this.marketCells = cells;
    }
}
