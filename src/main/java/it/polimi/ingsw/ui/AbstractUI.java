package it.polimi.ingsw.ui;

import java.io.IOException;

/**
 * This class represents the abstraction of an user interface. It will be extended by every user interface
 */
public abstract class AbstractUI {

    /**
     * Controller of the user interface
     */
    private UiController controller;

    /**
     * This is the contructor implemented by each interface
     * @param controller is the callback interface
     */
    public AbstractUI(UiController controller){
        this.controller=controller;
    }

    /**
     * This method returns the controller
     * @return the controller
     */
    protected UiController getController(){
        return controller;
    }

    /**
     * Called when ui should show a form to let the user decide the network settings
     */
    public abstract void showNetworkMenu();

    /**
     * Called when ui should show the loginPlayer menu to the user
     */
    public abstract void showLoginMenu();

    /**
     * Called when ui should show a "loginPlayer error" message
     */
    public abstract void notifyLoginError();

    /**
     * Called when ui should show a "loginPlayer successful" message
     */
    public abstract void notifyLoginSuccess();

    /**
     * Called when ui should show a form to let the user create one room or join one already created
     */
    public abstract void showRoomMenu();

    /**
     * Called when ui should show a "creating room successful" message to the user
     */
    public abstract void notifyCreatingRoomSuccess();

    /**
     * Called when ui should show a "creating room failed" message to the user
     */
    public abstract void notifyCreatingRoomFailed();

    /**
     *  Called when ui should show a "join successful" massage to the user
     */
    public abstract void notifyJoinRoomSuccess();

    /**
     * Called when ui should show a "join failure" to the user
     */
    public abstract void notifyJoinRoomFailed();

    /**
     * Called when ui should show a form to let the user decide the LorenzoIlMagnifico settings
     */
    public abstract void showGameConfigurationMenu();

    /**
     * Called when ui should show a "successful configuration" message
     */
    public abstract void notifyGameConfiguationDone();

    /**
     * Called when ui should show a "configuaration failure" message
     */
    public abstract void notifyGameConfigurationError();

    /**
     * Called when ui should notify the begining of the LorenzoIlMagnifico
     */
    public abstract void notifyGameBeginning();

    /**
     * Called when ui should notify to all users that the turn of a player started
     * @param nickname of the player
     */
    public abstract void notifyTurnBeginning(String nickname);

    /**
     * Called when ui should notify to all users that the turn of a player ended
     * @param nickname of the player
     */
    public abstract void notifyTurnEnd(String nickname);

    /**
     * Called when ui should show to all users the mainboard
     * @param update the current state of the session
     */
    //public abstract void showMainBoard(Stato update);

    /**
     * Called when ui should show the personal board
     * @param username of the player
     * @param update the current state of the personal board
     */
    //public abstract void showPersonalBoard(String username, Stato update);

    /**
     * Called when ui should show the personal bonus tile
     * @param nickname of the player
     */
    public abstract void showPersonalBonusTile(String nickname);

    /**
     * Called when ui should show the card chosen by a player
     */
    public abstract void showDevelopmentCard();

    /**
     * Called when ui should show a Tower
     * @param update the current state of a Tower
     */
    //public abstract void showTower(Status update);

    /**
     * Called when ui should show the Council Palace
     */
    //public abstract void showCouncilPalace(Status update);

    /**
     * Called when ui should show the Market
     */
    //public abstract void showMarket(Status update);

    /**
     * Called when ui should show the Production Area
     */
    //public abstract void showProductionArea(Status update);

    /**
     * Called when ui should show the Harvest Area
     */
    //public abstract void showHarvestArea(Status update);

    /**
     * Called when ui should show the value of the dices
     */
    //public abstract void showDices(Status update);

    /**
     * Called when ui should show the immediate effect of an action space
     */
    public abstract void showImmediateEffect();

    /**
     * Called when ui should show the requirements for a move
     */
    public abstract void showMoveRequirements();

    /**
     * Called when ui should show all my card possessed by a player
     */
    public abstract void showAllMyCards();

    /**
     * Called when ui should show a leader chosen by a player
     */
    public abstract void showLeader();

    /**
     * Called when ui should show all my resources of a player
     */
    public abstract void showResources(String nickname);

    /**
     * Called when ui should show all possible choices in exchange for a council privilege
     */
    public abstract void showCouncilPrivilegeChoises();

    /**
     * Called when ui should notify a "you have enough cards" error message
     */
    public abstract void notifyEnoughCardsError();

    /**
     * Called when ui should show all points of a player
     */
    // public abstract void showPoints(String username, Status update);

    /**
     * Called when ui should notify the immediate effect of a chosen card or an action space
     */
    public abstract void notifyImmediateEffect();

    /**
     * Called when ui should remember the players about a permanent effect
     */
    public abstract void notifyPermanentEffect();

    /**
     * Called when ui should show a "unsuccess move" message for not satisfying the requirements
     */
    public abstract void notifyUnsuccessMove();

    /**
     * Called when ui should show a "tower is occupied" message
     */
    public abstract void notifyOccupiedTower();

    /**
     * Called when ui should show a "action space is occupied" message
     */
    public abstract void notifyOccupiedActionSpace();

    /**
     * Called when ui should show the choices to answer to the Vatican action
     */
    public abstract void showExcommunicationMenu();

    /**
     * Called when should remember when Vatican takes action
     */
    public abstract void notifyVaticanAction();

    /**
     *
     */

}