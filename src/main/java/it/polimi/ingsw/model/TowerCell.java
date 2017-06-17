package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents tower cell abstraction.
 */
public class TowerCell implements Serializable{

    /**
     * Min dice value to place a family member in the cell.
     */
    private int minFamilyMemberValue;

    /**
     * Development card assigned to this cell.
     */
    private DevelopmentCard developmentCard;

    /**
     * Immediate effect of the cell.
     */
    private Effect towerCellImmediateEffect;

    /**
     * Family member that could be placed in the cell.
     */
    private FamilyMember familyMember;

    /**
     * Boolean value that checks if the cell is empty
     */
    private Boolean empty = true;

    /**
     * Set immediate effect of the cell.
     * @param effect
     */
    public void setTowerCellImmediateEffect(Effect effect){
        this.towerCellImmediateEffect = effect;
    }

    /**
     * Get immediate effect of the cell.
     * @return
     */
    public Effect getTowerCellImmediateEffect(){
        return this.towerCellImmediateEffect;
    }

    /**
     * Set the minimum value to let a family member access on the cell
     * @param value minimum value
     */
    public void setMinFamilyMemberValue(Integer value){
        this.minFamilyMemberValue = value;
    }

    /**
     * Set development card in the cell.
     * @param card of the cell.
     */
    public void setDevelopmentCard(DevelopmentCard card){
        this.developmentCard = card;
    }

    /**
     * Place a family member in the cell.
     * @param familyMember to be placed.
     */
    public void setFamilyMember(FamilyMember familyMember){
        this.familyMember = familyMember;
    }

    /**
     * Return the development card assigned to the cell.
     * @return a development card.
     */
    public DevelopmentCard getDevelopmentCard(){
        return this.developmentCard;
    }

    /**
     * Return dice value of the cell.
     * @return an integer.
     */
    public int getMinFamilyMemberValue(){
        return this.minFamilyMemberValue;
    }


    public Boolean getEmpty(){
        return this.empty;
    }

    public void setEmpty(Boolean updatedValue){
        this.empty = updatedValue;
    }


    public Boolean familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor){
        if (player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) > this.minFamilyMemberValue){
            return true;
        }
        return false;
    }


    public Boolean developmentCardCanBeBuyedBy(Player player){
        for (Map.Entry<ResourceType, Integer> entry : this.developmentCard.getCost().getResources().entrySet()) {
            if (this.developmentCard.getCost().getResources().get(entry.getKey()) > player.getPersonalBoard().getValuables().getResources().get(entry.getKey())){
                return false;
            }
        }
        return true;
    }
}
