package it.polimi.ingsw.model;

import sun.applet.Main;
import sun.jvm.hotspot.oops.Array;
import sun.jvm.hotspot.oops.Mark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class represents the main board abstraction.
 */
public class MainBoard {

    /**
     * Array of towers.
     */
    private Tower[] towers;

    /**
     * Vatican reference.
     */
    private Vatican vatican;

    private CouncilPalace councilPalace;

    private WorkArea harvest;

    private WorkArea production;

    private WorkAreaExtended harvestExtended;

    private WorkAreaExtended productionExtended;

    private Market market;

    private static MainBoard mainBoard;

    /**
     * Class constructor.
     */
    private MainBoard(){
        //instantiates the 4 towers
        int i = 0;
        towers = new Tower[4];

        //instantiates the vatican area
        this.vatican = new Vatican();

        //instantiates the councilPalace area
        this.councilPalace = new CouncilPalace();

        //instantiates the market area
        this.market = new Market();
    }

    /**
     * This method sets new cards for the towers
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
     * @param index
     * @return
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

    public WorkArea getHarvest(){
        return this.harvest;
    }

    public WorkArea getProduction(){
        return this.production;
    }

    public WorkAreaExtended getHarvestExtended(){
        return this.harvestExtended;
    }

    public WorkAreaExtended getProductionExtended(){
        return this.productionExtended;
    }

    public Market getMarket(){
        return this.market;
    }



}
