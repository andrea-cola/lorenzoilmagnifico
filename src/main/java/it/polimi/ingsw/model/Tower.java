package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;

import java.io.Serializable;

/**
 * This class represents tower abstraction.
 */
public class Tower implements Serializable{

    /**
     * Array of tower cells.
     */
    private TowerCell[] towerCells;


    public Tower(int numberOfCells){
        this.towerCells = new TowerCell[numberOfCells];
        for (int i = 0; i < numberOfCells; i++){
            this.towerCells[i] = new TowerCell((i * 2) + 1);
        }
    }

    /**
     * This method checks if the tower is free (all its cells are empty)
     * @return
     */
    public boolean isFree(){
        for(TowerCell cell : this.towerCells)
            if (cell.getPlayerNicknameInTheCell() != null)
                return false;
        return true;
    }

    public DevelopmentCardColor getColor(){
        return this.towerCells[0].getDevelopmentCard().getColor();
    }

    /**
     * This method sets the content of the cell (put a card inside the cell).
     */
    public void setTowerCell(int index, DevelopmentCard card){
        this.towerCells[index].setDevelopmentCard(card);
    }

    /**
     * This method gets the content of the cell (get the card contained in the cell).
     * @param index of the cells.
     * @return TowerCell object.
     */
    public TowerCell getTowerCell(Integer index){
        return this.towerCells[index];
    }

    public TowerCell[] getTowerCells(){
        return this.towerCells;
    }

    /**
     * This method checks if already exists a cell occupied by the player in the tower and if the family member has been already used
     * @param player
     * @throws GameException
     */
    public void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor) throws GameException{

        //check that the family member used has not been already used
        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed())
            if (familyMemberColor.equals(color))
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);

        //check if the player has not already placed a family member inside the tower
        for (TowerCell cell : this.towerCells)
            if (player.getUsername().equals(cell.getPlayerNicknameInTheCell()))
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_PLACED);

    }
}
