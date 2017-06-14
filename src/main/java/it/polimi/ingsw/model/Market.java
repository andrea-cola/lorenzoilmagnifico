package it.polimi.ingsw.model;

/**
<<<<<<< HEAD
 * This class represents the market zone on the mainboard
 */
public class Market {
    /**
     * Array of Market cell
=======
 * This class represent market abstraction.
 */
public class Market {

    /**
     * Market cells.
>>>>>>> c942eee6c0968bf3dbcf79534b020de9956d2bf4
     */
    private MarketCell[] marketCells;

    /**
<<<<<<< HEAD
     * Constructor of the markets
     */
    public Market(){
        this.marketCells = new MarketCell[4];
        for(int i = 0; i < this.marketCells.length; i++){
            this.marketCells[i] = new MarketCell();
        }
    }

    /**
     * This method returns the specific cell at the index
     * @param index index of market cell array
     * @return the market cell
=======
     * Get a specific market cell.
     * @param index of the cell.
     * @return a market cell.
>>>>>>> c942eee6c0968bf3dbcf79534b020de9956d2bf4
     */
    public MarketCell getMarketCell(int index){
        return this.marketCells[index];
    }

    public void setMarketCells(MarketCell[] cells){
        this.marketCells = cells;
    }
}
