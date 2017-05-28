package it.polimi.ingsw.model;

import sun.applet.Main;
import sun.jvm.hotspot.oops.Mark;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lorenzo on 23/05/17.
 */
public class MainBoard {

    private Tower[] towers;

    private Vatican vatican;

    private CouncilPalace councilPalace;

    private WorkArea harvest;

    private WorkArea production;

    private WorkAreaExtended harvestExtended;

    private WorkAreaExtended productionExtended;

    private Market market;

    private static MainBoard mainBoard;

    /**
     * Class constructor, note that this method gets called once due to the Singleton pattern
     */
    private MainBoard(){
        //instantiates the 4 towers
        int i = 0;
        this.towers = new Tower[DevelopmentCardColor.values().length];
        for(DevelopmentCardColor cardColor: DevelopmentCardColor.values()){
            this.towers[i] = new Tower(cardColor);
            i++;
        }

        //instantiates the vatican area
        this.vatican = new Vatican();

        //instantiates the councilPalace area
        this.councilPalace = new CouncilPalace();

        //instantiates the market area
        this.market = new Market();
    }

    /**
     *  Lazy instantiation of the mainBoard
     */
    public static MainBoard setupMainBoard(){
        if(mainBoard == null){
            mainBoard = new MainBoard();
        }
        return mainBoard;
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
