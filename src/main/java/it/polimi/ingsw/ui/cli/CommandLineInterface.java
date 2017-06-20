package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Debugger;

import java.io.*;
import java.util.List;

/**
 * This class manages the command line interface of the game.
 */

public class CommandLineInterface extends AbstractUI {

    /**
     * Title printed on the shell
     */
    private static final String TITLE = "\n\n\n\n\n\n     __     _____     ______     ________  __      ___ _________  ______\n" +
                                        "    /  /   /     \\   /  _   \\   /  ______//  \\    /  //_____  _/ /      \\ \n" +
                                        "   /  /   /   _   \\ /  (_)  /  /  /___   /    \\  /  / _____/ /  /   _    \\ \n" +
                                        "  /  /   /   (_)  //  __   /  /   ___/  /  /\\  \\/  / /_  ___/  /   (_)   / \n" +
                                        " /  /___ \\       //  /  \\ \\  /   /____ /  /  \\    /   / /______\\        / \n" +
                                        " \\______/ \\_____//__/    \\_\\/________//__/    \\__/   /________/ \\______/ \n" +
                                        "                                                                              \n" +
                                        "                         _  _     _   __           __    __  __             \n" +
                                        "                 / /    / \\/ \\   /_\\ / __ /\\  / / /_  / /   /  \\              \n"+
                                        "                / /__  /      \\ /   \\__//  \\/ / /   /  \\__ \\__/              \n\n\n";

    /**
     * Buffer reader for reading from the input
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private ScreenInterface screenInterface;

    private BasicScreen screen;

    public CommandLineInterface(UiController controller){
        super(controller);
        System.out.println(TITLE);
    }

    @Override
    public void chooseConnectionType(){
        screen = new ChooseConnectionScreen(screenInterface, getController()::setNetworkSettings);
    }

    @Override
    public void loginScreen() {
        screen = new LoginSignInScreen(screenInterface, getController()::loginPlayer);
    }

    @Override
    public void joinRoomScreen() {
        screen = new JoinRoomScreen(screenInterface, getController()::joinRoom);
        Debugger.printStandardMessage("Room join OK.");
        screen = null;
    }

    @Override
    public void createRoomScreen(){
        screen = new CreateRoomScreen(screenInterface, getController()::createRoom);
        Debugger.printStandardMessage("Room creation OK.");
        screen = null;
    }

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {
        screen = new ChoosePersonalBoardTileScreen(screenInterface, getController()::sendPersonalBoardTileChoice, personalBoardTileList);
    }

}
