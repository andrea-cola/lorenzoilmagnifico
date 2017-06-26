package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;

public class LELorenzoDeMedici extends LeaderEffect{

    private LeaderCard leaderCard;

    public LELorenzoDeMedici(){
        super.effectType = this.getClass().getSimpleName();
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
    public String getDescription() {
        String header = this.effectType + "\n";
        String resources = "Copia l'effetto di un'altra carta leader.\n";
        return new StringBuilder(header).append(resources).toString();
    }
}
