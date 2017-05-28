package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by lorenzo on 25/05/17.
 */
public class RunGame {

    public static void main(String [] args)
    {
        ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();

        Game game = Game.setupGame(4,cards);
        game.setNewRound();
    }
}

