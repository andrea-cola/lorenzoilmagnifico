package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.server.AbstractPlayer;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by andrea on 21/05/17.
 */
public class Room {
    private Configurator roomConfigurator = Configurator.setupConfigurator();

    private GameManager gameManager = new GameManager();

    private ArrayList<AbstractPlayer> players = new ArrayList<AbstractPlayer>();

    public Room(){

    }


    public void newRound(int period){
        this.gameManager.setupNewRound(period);
    }

    public void setupGameConfiguration(){
        try {
            ArrayList<DevelopmentCard> developmentCardsDeck = this.roomConfigurator.parseDevelopmentCard();
            this.gameManager.setupDevelopmentCards(developmentCardsDeck);
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

}
