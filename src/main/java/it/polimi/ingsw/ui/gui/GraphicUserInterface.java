package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Status;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.ui.gui.StartingBoard.StartingBoard;
import javafx.application.Application;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

import static javafx.application.Application.launch;

/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI{


    public GraphicUserInterface(UiController controller){
        super(controller);
        new Thread(()->Application.launch(StartingBoard.class)).start();
        StartingBoard.waitFor();
    }

    @Override
    public void showNetworkMenu(){


    }

    @Override
    public void showLoginMenu() {

    }

    @Override
    public void notifyLoginError() {

    }

    @Override
    public void notifyLoginSuccess() {

    }

    @Override
    public void showRoomMenu() {

    }

    @Override
    public void notifyCreatingRoomSuccess() {

    }

    @Override
    public void notifyCreatingRoomFailed() {

    }

    @Override
    public void notifyJoinRoomSuccess() {

    }

    @Override
    public void notifyJoinRoomFailed() {

    }

    @Override
    public void showGameConfigurationMenu() {

    }

    @Override
    public void notifyGameConfiguationDone() {

    }

    @Override
    public void notifyGameConfigurationError() {

    }

    @Override
    public void notifyGameBeginning() {

    }

    @Override
    public void notifyTurnBeginning(String nickname) {

    }

    @Override
    public void notifyTurnEnd(String nickname) {

    }

    public void showMainBoard(Status update) {

    }

    public void showPersonalBoard(String nickname, Status update) {

    }

    @Override
    public void showPersonalBonusTile(String nickname) {

    }

    @Override
    public void showDevelopmentCard() {

    }

    public void showTower(Status update) {

    }

    public void showCouncilPalace(Status update) {

    }

    public void showMarket(Status update) {

    }

    public void showProductionArea(Status update) {

    }

    public void showHarvestArea(Status update) {

    }

    public void showDices(Status update) {

    }

    @Override
    public void showImmediateEffect() {

    }

    @Override
    public void showMoveRequirements() {

    }

    @Override
    public void showAllMyCards() {

    }

    @Override
    public void showLeader() {

    }

    @Override
    public void showResources(String nickname) {

    }

    @Override
    public void showCouncilPrivilegeChoises() {

    }

    @Override
    public void notifyEnoughCardsError() {

    }
    
    public void showPoints(String nickname, Status update) {

    }

    @Override
    public void notifyImmediateEffect() {

    }

    @Override
    public void notifyPermanentEffect() {

    }

    @Override
    public void notifyUnsuccessMove() {

    }

    @Override
    public void notifyOccupiedTower() {

    }

    @Override
    public void notifyOccupiedActionSpace() {

    }

    @Override
    public void showExcommunicationMenu() {

    }

    @Override
    public void notifyVaticanAction() {

    }


}
