package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Market {

    private MarketCell[] marketCells;

    public Market(){
        this.marketCells = new MarketCell[4];
        for(int i = 0; i < this.marketCells.length; i++){
            this.marketCells[i] = new MarketCell();
        }
    }

    public MarketCell getMarketCell(int index){
        return this.marketCells[index];
    }
}
