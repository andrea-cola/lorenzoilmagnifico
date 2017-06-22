package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;
import it.polimi.ingsw.model.effects.EffectSimple;
import org.omg.PortableServer.THREAD_POLICY_ID;

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
    public MainBoard() {
        this.towers = new Tower[NUMBER_OF_TOWERS];
        for (int i = 0; i < NUMBER_OF_TOWERS; i++){
            this.towers[i] = new Tower(NUMBER_OF_TOWER_CELLS);
        }

        this.harvest = new ActionSpace(ActionType.HARVEST, 1);

        this.production = new ActionSpace(ActionType.PRODUCTION, 1);

        this.councilPalace = new CouncilPalace(1);

        this.market = new Market(NUMBER_OF_MARKET_CELLS);
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

    /**
     * This method returns a specific tower of the mainBoard
     * @param index of the tower.
     * @return a specific tower.
     */
    public Tower getTower(int index){
        return this.towers[index];
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