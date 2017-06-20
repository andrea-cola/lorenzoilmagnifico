package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectSimple;

import java.io.Serializable;

/**
 * This class represents a cell abstraction in the market.
 */
public class MarketCell implements Serializable{

    /**
     * Cell effect.
     */
    private Effect marketCellImmediateEffect;

    /**
     * Min value of the family member to join the cell.
     */
    private Integer minFamilyMemberValue;

    /**
     * Check if the cell is empty
     * @param minFamilyMemberValue
     */
    private Boolean empty = true;


    public MarketCell(int minFamilyMemberValue){
        this.minFamilyMemberValue = minFamilyMemberValue;
    }

    /**
     * This method checks if the cell is empty.
     * @return
     */
    public Boolean getEmpty() {
        return this.empty;
    }

    public void setEmpty(Boolean updatedValue){
        this.empty = updatedValue;
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

    public void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor) throws GameException{

        //check that the family member used has not been already used
        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed()){
            if (familyMemberColor.equals(color)){
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);
            }
        }

        //check that the family member value is greater or equal than the minFamilyMemberDiceValue requested
        if (player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) < this.minFamilyMemberValue){
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        }


        //if the family member can be placed, add it to the family members used and set this zone as not empty
        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);
        this.empty = false;
    }


}