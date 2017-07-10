package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;

import java.util.List;

/**
 * This class represents the abstraction of an user interface. It will be extended by every user interface
 */
public abstract class AbstractUserInterface {

    /**
     * Client user interface.
     */
    private UserInterface client;

    /**
     * Class constructor.
     * @param controller ui controller.
     */
    public AbstractUserInterface(UserInterface controller){
        this.client = controller;
    }

    /**
     * Return user interface controller.
     * @return user interface controller.
     */
    protected UserInterface getClient(){
        return client;
    }

    /**
     * Choose connection type.
     */
    public abstract void chooseConnectionType();

    /**
     * Run login screen.
     */
    public abstract void loginScreen();

    /**
     * Run join room screen.
     */
    public abstract void joinRoomScreen();

    /**
     * Run room create screen.
     */
    public abstract void createRoomScreen();

    /**
     * Run choose personal tile method.
     * @param personalBoardTileList to choose.
     */
    public abstract void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList);

    /**
     * Run choose leader cards method.
     * @param leaderCards to choose.
     */
    public abstract void chooseLeaderCards(List<LeaderCard> leaderCards);

    /**
     * Notify game started.
     */
    public abstract void notifyGameStarted();

    /**
     * Run turn screen.
     * @param username of the player has turn token.
     * @param seconds of the turn.
     */
    public abstract void turnScreen(String username, long seconds);

    /**
     * Handle excommunication logic on the client.
     * @param flag that indicates the excommunication status.
     */
    public abstract void supportForTheChurch(boolean flag);

    /**
     * Notify game ended.
     * @param ranking of the game.
     */
    public abstract void notifyEndGame(ServerPlayer[] ranking);
}