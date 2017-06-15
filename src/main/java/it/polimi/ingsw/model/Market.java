package it.polimi.ingsw.model;

/**
 * This class represents the market zone on the main board
 */
public class Market {

    /**
     * Market cells
     */
    private MarketCell[] marketCells;

    /**

     * Constructor of the markets
     */
    public Market(){
        this.marketCells = new MarketCell[4];
        for(int i = 0; i < this.marketCells.length; i++){
            this.marketCells[i] = new MarketCell();
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
