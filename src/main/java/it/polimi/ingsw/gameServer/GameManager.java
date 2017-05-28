package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorenzo on 27/05/17.
 */
public class GameManager {

    private List<DevelopmentCard> developmentCards;

    private Game game;

    public GameManager(){

    }

    protected void setDevelopmentCards(ArrayList<DevelopmentCard> cards){
        this.game.setupDevelopmentCardsDeck(cards);
    }


}
