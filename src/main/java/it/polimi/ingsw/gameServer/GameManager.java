package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorenzo on 27/05/17.
 */
public class GameManager {

    private ArrayList<DevelopmentCard> developmentCards;

    private ArrayList<AbstractPlayer> players;

    private Game game;

    public GameManager(){
        this.game = Game.setupGame();
    }


    protected void setupPlayers(ArrayList<AbstractPlayer> players){
        this.game.setupPlayers(players.size());
    }

    protected void setupMainBoard(){
        this.game.setupMainBoard();
    }

    protected void setupDevelopmentCards(ArrayList<DevelopmentCard> cards){
        this.game.setupDevelopmentCardsDeck(cards);
    }

    protected void setupNewRound(int period){
        this.game.setNewRound(period);
    }


}
