package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.model.Player;

import java.util.List;

/**
 * This class represents the abstraction of an user interface. It will be extended by every user interface
 */
public abstract class AbstractUI {

    private UiController client;

    public AbstractUI(UiController controller){
        this.client =controller;
    }

    protected UiController getClient(){
        return client;
    }

    public abstract void chooseConnectionType();

    public abstract void loginScreen();

    public abstract void joinRoomScreen();

    public abstract void createRoomScreen();

    public abstract void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList);

    public abstract void chooseLeaderCards(List<LeaderCard> leaderCards);

    public abstract void notifyGameStarted();

    public abstract void turnScreen(String username, long seconds);

    public abstract void notifyUpdate(String message);
}