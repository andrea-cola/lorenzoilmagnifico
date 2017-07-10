package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.EffectSimple;

import java.io.Serializable;
import java.util.LinkedList;

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

    /**
     * Class constructor
     * @param minFamilyMemberDiceValue the minimum family member dice value to place a family member inside the council palace
     * @param effectSimple the effect that is performed by the council palace
     */
    /*package-local*/ CouncilPalace(int minFamilyMemberDiceValue, EffectSimple effectSimple){
        this.minFamilyMemberDiceValue = minFamilyMemberDiceValue;
        this.effectSimple = effectSimple;
        nextTurnOrder = new LinkedList<>();
    }

    /**
     * Get min dice value.
     * @return min dice value.
     */
    /*package-local*/ int getMinFamilyMemberDiceValue(){
        return this.minFamilyMemberDiceValue;
    }

    /**
     * Get immediate effect.
     * @return immediate effect of the council.
     */
    /*package-local*/ EffectSimple getImmediateEffect(){
        return this.effectSimple;
    }

    /**
     * Add player in queue.
     * @param player to add in the queue.
     */
    /*package-local*/ void fifoAddPlayer(Player player){
        if(!nextTurnOrder.contains(player))
            this.nextTurnOrder.add(player);
    }

    /**
     * This method returns the new order of players for the next turn
     * @return
     */
    public LinkedList<Player> getNewOrder(){
        return this.nextTurnOrder;
    }

    /**
     * Reset fifo.
     */
    public void resetFifo(){
        this.nextTurnOrder = new LinkedList<>();
    }

    /**
     * Remove player from the queue.
     * @return the player on the top of the queue.
     */
    public Player fifoGetPlayer(){
        return this.nextTurnOrder.poll();
    }


    /**
     * Check if the family member can be placed inside the Council palace
     * @param player the player that wants to perform the action
     * @param familyMemberColor the color of the family member to be placed
     * @param servants the number of servants to add to the family member dice value
     * @throws GameException
     */
    /*package-local*/ void familyMemberCanBePlaced(Player player, FamilyMemberColor familyMemberColor, int servants) throws GameException{

        for (FamilyMemberColor color : player.getPersonalBoard().getFamilyMembersUsed())
            if (familyMemberColor.equals(color))
                throw new GameException(GameErrorType.FAMILY_MEMBER_ALREADY_USED);

        int familyMemberValueTot = player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) + servants;
        if (familyMemberValueTot < this.minFamilyMemberDiceValue)
            throw new GameException(GameErrorType.FAMILY_MEMBER_DICE_VALUE);

        player.getPersonalBoard().setFamilyMembersUsed(familyMemberColor);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Dice: " + minFamilyMemberDiceValue + "\nEffect: " + effectSimple + "\nPlayers in council:");
        for(Player player : nextTurnOrder)
            stringBuilder.append(" " + player.getUsername());
        return stringBuilder.toString();
    }

}
