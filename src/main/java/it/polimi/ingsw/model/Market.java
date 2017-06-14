package it.polimi.ingsw.model;

/**
 * This class represents the market zone on the mainboard
 */
public class Market {
    /**
     * Array of Market cell
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
     * This method returns the specific cell at the index
     * @param index index of market cell array
     * @return the market cell
     */
    public MarketCell getMarketCell(int index){
        return this.marketCells[index];
    }
}
