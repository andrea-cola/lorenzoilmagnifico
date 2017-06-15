package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectSimple;

/**
 * This class represents a cell abstraction in the market.
 */
public class MarketCell {

    /**
     * Cell effect.
     */
    private Effect marketCellImmediateEffect;

    /**
     * Min value of the family member to join the cell.
     */
    private Integer minFamilyMemberValue;

    /**
     * Family member that has joined the cell.
     */
    private FamilyMember familyMember;

    /**
     * This method checks if the cell is empty.
     * @return
     */
    public boolean isEmpty() {
        if(familyMember == null)
            return true;
        return false;
    }

    /**
     * Set the immediate effect of a market cell
     * @param effect
     */
    public void setMarketCellImmediateEffect(Effect effect){
        this.marketCellImmediateEffect = effect;
    }

    /**
     * This method returns the immediate effect of the market cell selected.
     * @return the immediate effect.
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