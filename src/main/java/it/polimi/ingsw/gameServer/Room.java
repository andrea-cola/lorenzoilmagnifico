package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.model.DevelopmentCard;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by andrea on 21/05/17.
 */
public class Room {
    public static void main(String [] args) {
        Configurator roomConfigurator = Configurator.setupConfigurator();
        GameManager gameManager = new GameManager();

        try {
            ArrayList<DevelopmentCard> developmentCardsDeck = roomConfigurator.parseDevelopmentCard();
            gameManager.setDevelopmentCards(developmentCardsDeck);
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }
}
