package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectSimple;

/**
 * Created by lorenzo on 24/05/17.
 */
public class MarketCell {

    private Effect marketCellImmediateEffect;

    private Integer minFamilyMemberValue;

    private boolean empty;

    /**
     * Class constructor
     */
    public MarketCell(){
        this.empty = true;
        this.minFamilyMemberValue = 1;
        this.marketCellImmediateEffect = new EffectSimple();
    }

    /**
     * This method checks if the cell is empty
     * @return
     */
    public boolean isEmpty() {
        return this.empty;
    }

    /**
     * This method returns the immediate effect of the market cell selected
     * @return
     */
    public Effect getImmediateEffect(){
        return this.marketCellImmediateEffect;
    }

    /**
     * This method sets the minimum value to access the cell (default = 1 but in case of bonus it may be changed)
     * @param value
     */
    public void setMinFamilyMemberValue(Integer value){
        this.minFamilyMemberValue = value;
    }

    /**
     * This method gets the minimum value to access the cell
     * @return
     */
    public Integer getMinFamilyMemberValue(){
        return this.minFamilyMemberValue;
    }

}

