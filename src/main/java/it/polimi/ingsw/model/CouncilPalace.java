package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;

import java.io.Serializable;
import java.util.LinkedList;
import it.polimi.ingsw.model.effects.EffectSimple;

/**
 * This class represents the council palace abstraction.
 */
public class CouncilPalace implements Serializable{

    /**
     * Min dice value of family member to be placed in the council.
     */
    private int minFamilyMemberDiceValue;

    /**
     * Immediate effect when players place a family member in the council.
     */
    private EffectSimple effectSimple;

    /**
     * FIFO queue, it represent the next turn player order.
     */
    private LinkedList<Player> nextTurnOrder;


    public CouncilPalace(int minFamilyMemberDiceValue, EffectSimple effectSimple){
        this.minFamilyMemberDiceValue = minFamilyMemberDiceValue;
        this.effectSimple = effectSimple;
    }

    /**
     * Get min dice value.
     * @return min dice value.
     */
    public int getMinFamilyMemberDiceValue(){
        return this.minFamilyMemberDiceValue;
    }

    /**
     * Get immediate effect.
     * @return immediate effect of the council.
     */
    public EffectSimple getImmediateEffect(){
        return this.effectSimple;
    }

    /**
     * Add player in queue.
     * @param player to add in the queue.
     */
    public void fifoAddPlayer(Player player){
        if(!nextTurnOrder.contains(player))
            this.nextTurnOrder.add(player);
    }

    /**
     * Remove player from the queue.
     * @return the player on the top of the queue.
     */
    public Player fifoGetPlayer(){
        return this.nextTurnOrder.poll();
    }


    public void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor) throws GameException{

        //check that the family member used has not been already used
        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed()){
            if (familyMemberColor.equals(color)){
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);
            }
        }

        //check that the family member value is greater or equal than the minFamilyMemberDiceValue requested
        if (player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) < this.minFamilyMemberDiceValue){
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);
        }

        //check that another family member of the player is not inside the council palace
        for (Player p : this.nextTurnOrder){
            if (p.getUsername().equals(player.getUsername())){
                throw  new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_PLACED);
            }
        }

        //if the family member can be placed, add it to the family members used
        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);

    }


}
