package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;

public class LELorenzoDeMedici extends LeaderEffect{

    private LeaderCard leaderCard;

    public LELorenzoDeMedici(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void setLeaderCard(LeaderCard leaderCard){
        this.leaderCard = leaderCard;
    }

    public LeaderCard getLeaderCard(){
        return this.leaderCard;
    }

    /**
     * Method to run the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "You can copy the effect of another card.";
    }
}
