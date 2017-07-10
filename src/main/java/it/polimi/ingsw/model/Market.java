package it.polimi.ingsw.model;

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
    /*package-local*/ Market(int numberOfCells, MarketCell[] marketCells){
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

    /**
     * Get all the market cells
     */
    public MarketCell[] getMarketCells(){
        return this.marketCells;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < marketCells.length; i++)
            if(marketCells[i].isAccessible()){
                stringBuilder.append("<MarketCell #" + (i+1) + "> ");
                if(!marketCells[i].isEmpty())
                    stringBuilder.append("already used");
                else{
                    stringBuilder.append("Dice: " + marketCells[i].getMinFamilyMemberValue() + "\n");
                    stringBuilder.append("Effect: " + marketCells[i].getMarketCellImmediateEffect());
                }
            }
        return stringBuilder.toString();
    }

}
