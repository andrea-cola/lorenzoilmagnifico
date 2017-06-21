package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Debugger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChoosePersonalBoardTileScreen extends BasicScreen{

    private ICallback callback;

    private List<PersonalBoardTile> personalBoardTileList;

    private List<CLIMessages> cliMessages = new ArrayList<>();

    ChoosePersonalBoardTileScreen(ICallback callback, List<PersonalBoardTile> personalBoardTileList){
        this.callback = callback;

        cliMessages.add(CLIMessages.PERSONAL_TILE_CHOICE);
        printScreenTitle("PERSONAL BOARD CHOICE");
        this.personalBoardTileList = personalBoardTileList;
        print(cliMessages);
        choosePersonalBoardTile();
    }

    private void printTiles(){
        int i = 1;
        for(PersonalBoardTile personalBoardTile : personalBoardTileList){
            print("[" + i + "] " + personalBoardTile.getProductionEffect().getDescription() + " " + personalBoardTile.getHarvestEffect().getDescription());
            i++;
        }
    }

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
            Debugger.printDebugMessage("Error while reading from keyboard.");
        }
    }

    @FunctionalInterface
    public interface ICallback{
        void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile);
    }

}
