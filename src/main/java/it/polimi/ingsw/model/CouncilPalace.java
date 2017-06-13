package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;

import java.util.LinkedList;

/**
 * This class represents the council palace abstraction.
 */
public class CouncilPalace {

    /**
     * Min dice value of family member to be placed in the council.
     */
    private int minFamilyMemberDiceValue;

    /**
     * Immediate effect when players place a family member in the council.
     */
    private Effect immediateEffect;

    /**
     * FIFO queue, it represent the next turn player order.
     */
    private LinkedList<Player> nextTurnOrder;

    /**
     * Array of benefits.
     */
    private static PointsAndResources[] options;

    /**
     * Set immediate effect.
     * @param effect of the council.
     */
    public void setImmediateEffect(Effect effect){
        this.immediateEffect = effect;
    }

    /**
     * Get immediate effect.
     * @return immediate effect of the council.
     */
    public Effect getImmediateEffect(){
        return this.immediateEffect;
    }

    /**
     * Add player in queue.
     * @param player to add in the queue.
     */
    public void fifoAddPlayer(Player player){
        this.nextTurnOrder.add(player);
    }

    /**
     * Remove player from the queue.
     * @return the player on the top of the queue.
     */
    public Player fifoGetPlayer(){
        return this.nextTurnOrder.poll();
    }

    /**
     * Set min dice value of the family member to be placed in the council.
     * @param value of the family member.
     */
    public void setMinFamilyMemberDiceValue(int value){
        this.minFamilyMemberDiceValue = value;
    }

    /**
     * Get min dice value.
     * @return min dice value.
     */
    public int getMinFamilyMemberDiceValue(){
        return this.minFamilyMemberDiceValue;
    }

    /**
     * Set array of possible options.
     * @param pointsAndResources options.
     */
    public void setOptions(PointsAndResources[] pointsAndResources){
        options = pointsAndResources;
    }

    /**
     * Get array of possible options.
     * @param index of options array.
     * @return selected option.
     */
    public static PointsAndResources getOptions(int index){
        return options[index];
    }
}
