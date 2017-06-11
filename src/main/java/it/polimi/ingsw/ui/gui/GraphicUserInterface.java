package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import javafx.application.Application;

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
    public void showNetworkMenu() {
        NetworkBoard networkBoard= new NetworkBoard((networkType, address, port)->getController().setNetworkSettings(networkType, address, port));
        new Thread(()-> Application.launch(NetworkBoard.class)).start();
        networkBoard.waitFor();
    }

    @Override
    public void showLoginMenu() {
        LoginBoard loginBoard= new LoginBoard(((username, password) -> getController().loginPlayer(username, password)));
        new Thread(()-> Application.launch(LoginBoard.class)).start();
        loginBoard.waitFor();
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

    @Override
    public void showMainBoard(Stato update) {

    }

    @Override
    public void showPersonalBoard(String nickname, Stato update) {

    }

    @Override
    public void showPersonalBonusTile(String nickname) {

    }

    @Override
    public void showDevelopmentCard() {

    }

    @Override
    public void showTower(Status update) {

    }

    @Override
    public void showCouncilPalace(Status update) {

    }

    @Override
    public void showMarket(Status update) {

    }

    @Override
    public void showProductionArea(Status update) {

    }

    @Override
    public void showHarvestArea(Status update) {

    }

    @Override
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

    @Override
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
