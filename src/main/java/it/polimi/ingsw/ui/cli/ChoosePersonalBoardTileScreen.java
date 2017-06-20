package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.PersonalBoardTile;

import java.util.List;

public class ChoosePersonalBoardTileScreen extends BasicScreen{

    private ICallback callback;
    private List<PersonalBoardTile> personalBoardTileList;

    ChoosePersonalBoardTileScreen(ScreenInterface screenInterface, ICallback callback, List<PersonalBoardTile> personalBoardTileList){
        super(screenInterface);

        System.out.println("\n\n[CHOOSE PERSONAL BOARD TILE]");
        this.callback = callback;
        this.personalBoardTileList = personalBoardTileList;
        addPrintCommand("personal-tile", arguments->choosePersonalBoardTile(arguments));
        printHelps();
        readCommand();
    }

    private void printHelps(){
        int i = 1;
        System.out.println("Helps: personal-tile [number]\n");
        for(PersonalBoardTile personalBoardTile : personalBoardTileList){
            System.out.println("<" + i + ">");
            System.out.println(personalBoardTile.getProductionEffect().getDescription());
            System.out.println(personalBoardTile.getHarvestEffect().getDescription());
            i++;
        }
    }

    private void choosePersonalBoardTile(String[] arguments){
        if(arguments.length == 1){
            int index = Integer.parseInt(arguments[0]) - 1;
            this.callback.sendPersonalBoardTileChoise(personalBoardTileList.get(index));
        }
        else
            throw new IllegalArgumentException();
    }

    @FunctionalInterface
    public interface ICallback{
        void sendPersonalBoardTileChoise(PersonalBoardTile personalBoardTile);
    }

}
