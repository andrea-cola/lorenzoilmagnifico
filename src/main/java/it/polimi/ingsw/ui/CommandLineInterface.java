package it.polimi.ingsw.ui;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;

/**
 * This class manages the command line interface of the game.
 */

public class CommandLineInterface extends AbstractUI {


    private static final String TITLE=  "     __     _____     ______     ________  __      ___ _________  ______\n" +
                                        "    /  /   /     \\   /  _   \\   /  ______//  \\    /  //_____  _/ /      \\ \n" +
                                        "   /  /   /   _   \\ /  (_)  /  /  /___   /    \\  /  / _____/ /  /   _    \\ \n" +
                                        "  /  /   /   (_)  //  __   /  /   ___/  /  /\\  \\/  / /_  ___/  /   (_)   / \n" +
                                        " /  /___ \\       //  /  \\ \\  /   /____ /  /  \\    /   / /______\\        / \n" +
                                        " \\______/ \\_____//__/    \\_\\/________//__/    \\__/   /________/ \\______/ \n" +
                                        "                                                                              \n" +
                                        "                         _  _     _   __           __    __  __             \n" +
                                        "                 / /    / \\/ \\   /_\\ / __ /\\  / / /_  / /   /  \\              \n"+
                                        "                / /__  /      \\ /   \\__//  \\/ / /   /  \\__ \\__/              \n";



    private PrintWriter console= new PrintWriter(new OutputStreamWriter(System.out));
    private BufferedReader keyboard= new BufferedReader(new InputStreamReader(System.in));
    private ContextInterface contextInterface;

    private BaseContext context;

    public CommandLineInterface(UiController controller){
        super(controller);
        console.println(TITLE);
    }

    private void read(){
        try {
            String line = keyboard.readLine();
            if (context != null) {
                context.handle(line);
            }
        } catch (UnknownCommandException e) {
            e.printStackTrace();
        } catch (IOException g) {
            g.printStackTrace();
        }
    }

    @Override
    public void showNetworkMenu(){
        console.println("Loading Network Menu");
        read();
        context= new NetworkContext(contextInterface, ((networkType, address, port) -> getController().setNetworkSetting(networkType, address, port)));
    }


    @Override
    public void showLoginMenu() {
        console.println("You need to enter 'login' before start playing");
        read();
        context= new LoginContext(contextInterface, (nickname, password) -> getController().loginPlayer(nickname, password));
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
    // public void showMainBoard(Stato update) {
    }

    @Override
    public void showPersonalBoard(String nickname, Stato update) {
    // public void showPersonalBoard(String nickname, Stato update) {

    }

    @Override
    public void showPersonalBonusTile(String nickname) {

    }

    @Override
    public void showDevelopmentCard() {

    }

    @Override
    public void showTower(Status update) {
    //  public void showTower(Status update) {

    }

    @Override
    public void showCouncilPalace(Status update) {
    // public void showCouncilPalace(Status update) {
    }

    @Override
    public void showMarket(Status update) {
    // public void showMarket(Status update) {

    }

    @Override
    public void showProductionArea(Status update) {
    // public void showProductionArea(Status update) {

    }

    @Override
    public void showHarvestArea(Status update) {
    // public void showHarvestArea(Status update) {

    }

    @Override
    public void showDices(Status update) {
    // public void showDices(Status update) {

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
