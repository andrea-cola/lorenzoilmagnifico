package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

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
    public MainBoard(MainBoard configuration) {
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
        this.vatican = new Vatican(configuration.getVatican().getExcommunicationCard(), configuration.getVatican().getVictoryPointsBonusArray());
    }

    /**
     * Set card in the tower. This method is used at the beginning of all ages.
     * @param index of the tower.
     * @param cards to be set.
     */
    public void setTower(int index, ArrayList<DevelopmentCard> cards){
        int cell = 0;
        for (DevelopmentCard card : cards){
            this.towers[index].setTowerCell(cell, card);
            cell++;
        }
    }

    public void setVatican(ExcommunicationCard card){
        this.vatican.setExcommunicationCard(card);
    }

    /**
     * This method returns a specific tower of the mainBoard
     * @param index of the tower.
     * @return a specific tower.
     */
    public Tower getTower(int index){
        return this.towers[index];
    }

    public Tower[] getTowers(){
        return this.towers;
    }

    public Vatican getVatican(){
        return this.vatican;
    }

    public CouncilPalace getCouncilPalace(){
        return this.councilPalace;
    }

    public ActionSpace getHarvest(){
        return this.harvest;
    }

    public ActionSpace getProduction(){
        return this.production;
    }

    public ActionSpaceExtended getHarvestExtended(){
        return this.harvestExtended;
    }

    public ActionSpaceExtended getProductionExtended(){
        return this.productionExtended;
    }

    public Market getMarket(){
        return this.market;
    }

    public int getNumberOfTowers(){
        return NUMBER_OF_TOWERS;
    }

    public int getNumberOfTowerCells(){
        return NUMBER_OF_TOWER_CELLS;
    }

    public int getNumberOfMarketCells(){
        return NUMBER_OF_MARKET_CELLS;
    }

}