package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PersonalBoardTile;

import java.util.List;

/**
 * This class represents the abstraction of an user interface. It will be extended by every user interface
 */
public abstract class AbstractUI {

    private UiController controller;

    public AbstractUI(UiController controller){
        this.controller=controller;
    }

    protected UiController getController(){
        return controller;
    }

    public abstract void chooseConnectionType();

    public abstract void loginScreen();

    public abstract void joinRoomScreen();

    public abstract void createRoomScreen();

    public abstract void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList);

}