package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.EffectSimple;

import java.io.Serializable;

/**
 * This class represents a cell abstraction in the market.
 */
public class MarketCell implements Serializable{

    /**
     * Min value of the family member to join the cell.
     */
    private int minFamilyMemberValue;

    /**
     * Cell effect.
     */
    private EffectSimple marketCellImmediateEffect;

    /**
     * Check if the cell is empty
     */
    private boolean empty;

    private boolean accessible;

    /**
     * Class constructor.
     * @param minFamilyMemberValue
     * @param effectSimple
     */
    public MarketCell(int minFamilyMemberValue, EffectSimple effectSimple){
        this.minFamilyMemberValue = minFamilyMemberValue;
        this.marketCellImmediateEffect = effectSimple;
        empty = true;
        accessible = true;
    }

    public void setNotAccessible(){
        this.accessible = false;
    }

    public boolean isAccessible(){
        return this.accessible;
    }

    /**
     * This method checks if the cell is empty.
     * @return
     */
    public boolean getEmpty() {
        return this.empty;
    }

    public void setEmpty(Boolean updatedValue){
        this.empty = updatedValue;
    }

    /**
     * This method returns the immediate effect of the market cell selected.
     * @return the immediate effect.
     */
    public EffectSimple getMarketCellImmediateEffect(){
        return this.marketCellImmediateEffect;
    }

    /**
     * This method gets the minimum value to access the cell
     * @return
     */
    public int getMinFamilyMemberValue(){
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