package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Choose personal board tile screen.
 */
public class ChoosePersonalBoardTileScreen extends BasicScreen{

    /**
     * Callback interface.
     */
    private ICallback callback;

    /**
     * Personal tiles list.
     */
    private List<PersonalBoardTile> personalBoardTileList;

    /**
     * Messages to be printed.
     */
    private List<String> cliMessages = new ArrayList<>();

    /**
     * Keyboard handler.
     */
    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    /**
     * Class constructor.
     * @param callback interface.
     * @param personalBoardTileList to be chosen.
     */
    /*package-local*/ ChoosePersonalBoardTileScreen(ICallback callback, List<PersonalBoardTile> personalBoardTileList){
        this.callback = callback;

        cliMessages.add("Choose your personal board tile.");
        printScreenTitle("PERSONAL BOARD CHOICE");
        this.personalBoardTileList = personalBoardTileList;
        print(cliMessages);
        choosePersonalBoardTile();
    }

    /**
     * Print all tiles.
     */
    private void printTiles(){
        int i = 1;
        for(PersonalBoardTile personalBoardTile : personalBoardTileList){
            print("[" + i + "] " + personalBoardTile.getProductionEffect().toString() + " " + personalBoardTile.getHarvestEffect().toString());
            i++;
        }
    }

    /**
     * Handle personal board tile choice.
     */
    private void choosePersonalBoardTile(){
        try {
            int key;
            do {
                printTiles();
                key = Integer.parseInt(keyboardReader.readLine());
            } while (key < 1 || key > personalBoardTileList.size());
            key = key - 1;
            this.callback.sendPersonalBoardTileChoice(personalBoardTileList.get(key));
        } catch (ClassCastException e) {
            choosePersonalBoardTile();
        } catch (IOException e){
            Printer.printDebugMessage("Error while reading from keyboard.");
        }
    }

    /**
     * Callback interface.
     */
    @FunctionalInterface
    public interface ICallback{
        void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile);
    }

}
