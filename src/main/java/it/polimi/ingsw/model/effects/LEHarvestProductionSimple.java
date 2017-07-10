package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

import java.util.List;

/**
 * This class represents the leader effect that performs an harvest/production effect
 */
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
        diceActionValue = 0;
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
     * @param player is gaining benefit from
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback){
        //get the last family member used and change its value
        List<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
        FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(familyMemberColor, this.diceActionValue);

        if (this.actionType.equals(ActionType.HARVEST)){
            for (DevelopmentCard card : player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN)){
                card.getPermanentEffect().runEffect(player, informationCallback);
            }
        }else if (this.actionType.equals(ActionType.PRODUCTION)){
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
