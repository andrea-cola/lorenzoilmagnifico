package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class LEHarvestProductionSimple extends LeaderEffect{

    /**
     * Dice value to run the permanent effect.
     */
    private int diceActionValue;

    /**
     * Type of the action that activate the effect.
     */
    private ActionType actionType;

    /**
     * Class constructor.
     */
    public LEHarvestProductionSimple(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Set the dice value to activate the action
     * of the permanent effect.
     * @param value of the dice.
     */
    public void setDiceActionValue(int value){
        this.diceActionValue = value;
    }

    /**
     * Get the dice value to active the action
     * of the permanent effect.
     * @return value of the dice.
     */
    public int getDiceActionValue(){
        return this.diceActionValue;
    }

    /**
     * Set the action type of the permanent effect.
     * @param type of action.
     */
    public void setActionType(ActionType type){
        this.actionType = type;
    }

    /**
     * Get the action type of the permanent effect.
     * @return type of the action.
     */
    public ActionType getActionType(){
        return this.actionType;
    }

    /**
     * Run the effect.
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback){
        //get the last family member used and change its value
        ArrayList<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
        FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(familyMemberColor, this.diceActionValue);

        if (this.actionType.equals(ActionType.HARVEST)){
            for (DevelopmentCard card : player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN)){
                card.getPermanentEffect().runEffect(player, informationCallback);
            }
        }

        if (this.actionType.equals(ActionType.PRODUCTION)){
            for (DevelopmentCard card : player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW)){
                card.getPermanentEffect().runEffect(player, informationCallback);
            }
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "You can run " + actionType.toString().toLowerCase() + " with dice value = " + diceActionValue;
    }

}
