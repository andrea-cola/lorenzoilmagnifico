package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
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
     * The nickname of the player whose one of his familiar is inside the cell
     */
    private String playerNicknameInTheCell;


    public TowerCell(int minFamilyMemberValue){
        this.minFamilyMemberValue = minFamilyMemberValue;
    }

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


    /**
     * Assign the cell to the user that occupied it
     * @param playerNicknameInTheCell
     */
    public void setPlayerNicknameInTheCell(String playerNicknameInTheCell){
        this.playerNicknameInTheCell = playerNicknameInTheCell;
    }

    /**
     * Get the username of the player inside a particular cell
     * @return
     */
    public String getPlayerNicknameInTheCell(){
        return this.playerNicknameInTheCell;
    }

    /**
     * Checks if the value is enough to place the family member inside the cell
     * @param player
     * @param familyMemberColor
     * @throws GameException
     */
    public void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor) throws GameException{
        if (player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) < this.minFamilyMemberValue){
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        }
    }

    /**
     * Checks if the player has enough resources to buy the development card
      * @param player
     * @throws GameException
     */
    public void developmentCardCanBeBuyedBy(Player player) throws GameException{
        for (Map.Entry<ResourceType, Integer> entry : this.developmentCard.getCost().getResources().entrySet()) {
            if (this.developmentCard.getCost().getResources().get(entry.getKey()) > player.getPersonalBoard().getValuables().getResources().get(entry.getKey())) {
                throw new GameException(GameErrorType.PLAYER_RESOURCES_ERROR);
            }
        }
    }
}
