package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;

public class LELorenzoDeMedici extends LeaderEffect{

    /**
     * Leader card replicated by Lorenzo De Medici.
     */
    private LeaderCard leaderCard;

    /**
     * Class constructor.
     */
    public LELorenzoDeMedici(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Set leader card to be replicated by Lorenzo De Medici effect.
     * @param leaderCard to be replicated.
     */
    public void setLeaderCard(LeaderCard leaderCard){
        this.leaderCard = leaderCard;
    }

    /**
     * Return replicated leader card.
     * @return replicated leader card.
     */
    public LeaderCard getLeaderCard(){
        return this.leaderCard;
    }

    /**
     * Method to run the effect of the card.
     * @param player is gaining benefit of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        this.leaderCard = informationCallback.copyAnotherLeaderCard("lorenzo-il-magnifico");
        player.getPersonalBoard().getLeaderCards().add(leaderCard);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "You can copy the effect of another card.";
    }
}
