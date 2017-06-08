package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.ServerPlayer;

import java.util.ArrayList;

public class GameManager {

    /**
     * Array of all development cards.
     */
    private ArrayList<DevelopmentCard> developmentCards;

    /**
     * Array of players.
     */
    private ArrayList<ServerPlayer> players;

    /**
     * Game instance.
     */
    private Game game;

    /**
     * Class constructor.
     */
    public GameManager(){
        this.game = Game.setupGame();
    }

    protected void setupPlayers(ArrayList<ServerPlayer> players){
        this.game.setupPlayers(players.size());
    }

    protected void setupMainBoard(){
        this.game.setupMainBoard();
    }

    protected void setupDevelopmentCards(ArrayList<DevelopmentCard> cards){
        //for(DevelopmentCard card: cards){
         //   System.out.print(card.getImmediateEffect().getCouncilPrivilege());
        //}
        this.game.setupDevelopmentCardsDeck(cards);
    }

    protected void setupNewRound(int period){
        this.game.setNewRound(period);
    }


}
