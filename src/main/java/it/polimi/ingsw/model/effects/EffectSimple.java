package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.InformationCallback;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represent the immediate simple effect.
 * A immediate simple effect allow the player to get resources and points.
 */
public class EffectSimple extends Effect{

    private int diceActionValue;

    private ActionType actionType;

    /**
     * Points and resources that user obtain.
     */
    private PointsAndResources valuable;

    /**
     * Council privilege object.
     */
    private int numberOfCouncilPrivileges;

    /**
     * Class constructor.
     */
    public EffectSimple(){
        super.setEffectType(this.getClass().getSimpleName());
        this.valuable = new PointsAndResources();
    }

    /**
     * Return points and resources of the effect.
     * @return points and resources.
     */
    public PointsAndResources getValuable(){
        return this.valuable;
    }

    /**
     * Return council privilege object.
     * @return council privilege object.
     */
    public int getCouncilPrivilege(){
        return this.numberOfCouncilPrivileges;
    }

    /**
     * Method to run the effect.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        //check if the player has the leader card for this effect and if it is active
        LeaderCard leaderCard = player.getPersonalBoard().getLeaderCardWithName("Santa Rita");
        if (leaderCard != null && leaderCard.getLeaderEffectActive()){
            updateResources(player, 2);
        }else {
            updateResources(player, 1);
        }

        updatePoints(player);

        //here the logic to manage the player's council privilege decision
        if (this.numberOfCouncilPrivileges > 0){
            CouncilPrivilege councilPrivilege = new CouncilPrivilege(numberOfCouncilPrivileges);
            councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
        }

        if(this.actionType != null){
            if(actionType.equals(ActionType.HARVEST)){
                updateFamilyMemberValue(player);
                //personal board tile effect
                player.getPersonalBoard().getPersonalBoardTile().getHarvestEffect().runEffect(player, informationCallback);

                //permanent effect
                for (DevelopmentCard card : player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN)) {
                    card.getPermanentEffect().runEffect(player, informationCallback);
                }
            }
            else{
                updateFamilyMemberValue(player);
                //personal board tile effect
                player.getPersonalBoard().getPersonalBoardTile().getProductionEffect().runEffect(player, informationCallback);

                //run permanent effect
                for (DevelopmentCard card : player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW)) {
                    card.getPermanentEffect().runEffect(player, informationCallback);
                }
            }
        }
    }

    private void updateFamilyMemberValue(Player player){
        int familyMemberRealValue = diceActionValue + player.getPersonalBoard().getHarvestProductionDiceValueBonus().get(actionType)
                - player.getPersonalBoard().getExcommunicationValues().getHarvestProductionDiceMalus().get(actionType);

        ArrayList<FamilyMemberColor> familyMembersUsed = player.getPersonalBoard().getFamilyMembersUsed();
        FamilyMemberColor familyMemberColor = familyMembersUsed.get(familyMembersUsed.size() - 1);
        while(player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) != diceActionValue)
            if(player.getPersonalBoard().getFamilyMember().getMembers().get(familyMemberColor) > diceActionValue)
                player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(familyMemberColor, 1);
            else
                player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(familyMemberColor, 1);

    }

    /**
     * This method updates player's resources
     * @param player
     * @param multiplicatorValue
     */
    private void updateResources(Player player, int multiplicatorValue){
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet()) {
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue() * multiplicatorValue);
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
        }
    }

    /**
     * This method updates player's points
     * @param player
     */
    private void updatePoints(Player player){
        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet()) {
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "Earn " + valuable.toString() + "and " + numberOfCouncilPrivileges + " council privileges";
    }
}
