package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;

import java.util.List;

/**
 * This class represents the abstraction of an user interface. It will be extended by every user interface
 */
public abstract class AbstractUserInterface {

    private UserInterface client;

    public AbstractUserInterface(UserInterface controller){
        this.client =controller;
    }

    protected UserInterface getClient(){
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

    public abstract void supportForTheChurch(boolean flag);

    public abstract void notifyEndGame(ServerPlayer[] ranking);
}