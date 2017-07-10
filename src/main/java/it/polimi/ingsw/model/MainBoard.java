package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the main board abstraction.
 */
public class MainBoard implements Serializable{

    private static final int NUMBER_OF_TOWERS = 4;
    private static final int NUMBER_OF_TOWER_CELLS = 4;
    private static final int NUMBER_OF_MARKET_CELLS = 4;

    /**
     * Array of towers.
     */
    private Tower[] towers;

    /**
     * Vatican reference.
     */
    private Vatican vatican;

    /**
     * Council palace reference.
     */
    private CouncilPalace councilPalace;

    /**
     * Market reference.
     */
    private Market market;

    /**
     * Harvest reference.
     */
    private ActionSpace harvest;

    /**
     * Production reference.
     */
    private ActionSpace production;

    /**
     * Harvest extended reference.
     */
    private ActionSpaceExtended harvestExtended;

    /**
     * Production extended reference.
     */
    private ActionSpaceExtended productionExtended;


    /**
     * Constructor for the main board
     */
    /*package-local*/ MainBoard(MainBoard configuration) {
        this.towers = new Tower[configuration.towers.length];
        for(int i = 0; i < configuration.towers.length; i++) {
            int cellPerTower = configuration.towers[i].getTowerCells().length;
            this.towers[i] = new Tower(cellPerTower);
            for(int j = 0; j < cellPerTower; j++){
                this.towers[i].getTowerCell(j).setMinFamilyMemberValue(configuration.towers[i].getTowerCell(j).getMinFamilyMemberValue());
                this.towers[i].getTowerCell(j).setTowerCellImmediateEffect(configuration.towers[i].getTowerCell(j).getTowerCellImmediateEffect());
            }
        }

        this.harvest = new ActionSpace(ActionType.HARVEST, configuration.getHarvest().getActionSpaceEffect());
        this.production = new ActionSpace(ActionType.PRODUCTION, configuration.getProduction().getActionSpaceEffect());

        this.harvestExtended = new ActionSpaceExtended(ActionType.HARVEST, configuration.getHarvestExtended().getDiceValueMalus(), configuration.getHarvestExtended().getEffect());
        this.productionExtended = new ActionSpaceExtended(ActionType.PRODUCTION, configuration.getProductionExtended().getDiceValueMalus(), configuration.getProductionExtended().getEffect());

        this.councilPalace = new CouncilPalace(configuration.getCouncilPalace().getMinFamilyMemberDiceValue(), configuration.getCouncilPalace().getImmediateEffect());

        this.market = new Market(configuration.getMarket().getMarketCells().length, configuration.getMarket().getMarketCells());
        this.vatican = new Vatican(configuration.getVatican().getExcommunicationCheckPoints());
    }

    /**
     * Set card in the tower. This method is used at the beginning of all ages.
     * @param index of the tower.
     * @param cards to be set.
     */
    public void setTower(int index, List<DevelopmentCard> cards){
        int cell = 0;
        for (DevelopmentCard card : cards){
            this.towers[index].setTowerCell(cell, card);
            cell++;
        }
    }

    /**
     * This method returns a specific tower of the mainBoard
     * @param index of the tower.
     * @return a specific tower.
     */
    public Tower getTower(int index){
        return this.towers[index];
    }

    /**
     * Method to get all the tower of the MainBoard
     */
    public Tower[] getTowers(){
        return this.towers;
    }

    /**
     * Method to get the Vatican
     */
    public Vatican getVatican(){
        return this.vatican;
    }

    /**
     * Method to get the CouncilPalace
     */
    public CouncilPalace getCouncilPalace(){
        return this.councilPalace;
    }

    /**
     * Method to get the Harvest
     */
    public ActionSpace getHarvest(){
        return this.harvest;
    }

    /**
     * Method to get the Production
     */
    public ActionSpace getProduction(){
        return this.production;
    }

    /**
     * Method to get the HarvestExtended
     */
    public ActionSpaceExtended getHarvestExtended(){
        return this.harvestExtended;
    }

    /**
     * Method to get the ProductionExtended
     */
    public ActionSpaceExtended getProductionExtended(){
        return this.productionExtended;
    }

    /**
     * Method to get the market
     */
    public Market getMarket(){
        return this.market;
    }

    /**
     * Method to get the number of towers in the MainBoard
     */
    public int getNumberOfTowers(){
        return NUMBER_OF_TOWERS;
    }

    /**
     * Method to get the number of cells for each Tower
     */
    public int getNumberOfTowerCells(){
        return NUMBER_OF_TOWER_CELLS;
    }

    /**
     * Method to get the number of market cells in the MainBoard
     */
    public int getNumberOfMarketCells(){
        return NUMBER_OF_MARKET_CELLS;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int j = 0; j < towers.length; j++)
            stringBuilder.append("[TOWER " + (j+1) + " " + towers[j].getColor() + "]\n" + towers[j]);

        stringBuilder.append("[COUNCIL PALACE] -> " + councilPalace);

        stringBuilder.append("\n\n[VATICAN]\n");
        for(int i = 0; i < vatican.getExcommunicationCards().length; i++)
            stringBuilder.append("Excommunication #" + (i+1) + " [faith points limit = " + (vatican.getExcommunicationCheckPoints())[i] + "] -> " + vatican.getExcommunicationCard(i).getEffect().getDescription() + "\n");

        stringBuilder.append("\n[MARKET]\n" + market);
        stringBuilder.append("\n\n[HARVEST SIMPLE]\n" + harvest);
        stringBuilder.append("\n\n[PRODUCTION SIMPLE]\n" + production);

        if(harvestExtended.isAccessible())
            stringBuilder.append("\n\n[HARVEST EXTENDED]\n" + harvestExtended);
        if(productionExtended.isAccessible())
            stringBuilder.append("\n\n[PRODUCTION EXTENDED]\n" + productionExtended);

        return stringBuilder.toString();
    }

}