package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.utility.Printer;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Basic screen in game logic.
 */
/*package-local*/ class BasicGameScreen {

    /**
     * Map of the options.
     */
    private HashMap<String, Function> options;

    /**
     * Class constructor.
     */
    /*package-local*/ BasicGameScreen(){
        options = new HashMap<>();
    }

    /**
     * Receive parameters from keyboard handler.
     * @param parameters of the command typed.
     * @throws WrongCommandException if command is wrong.
     */
    void sendParameters(String[] parameters) throws WrongCommandException{
        String option = parameters[0].toLowerCase();
        if(options.containsKey(option)){
            Function function = options.get(parameters[0]);
            String[] subArray = Arrays.copyOfRange(parameters, 1, parameters.length);
            function.doAction(subArray);
        }
        else
            throw new WrongCommandException();
    }

    /**
     * Add option in map.
     * @param string as key.
     * @param message to be printed.
     * @param function to be called.
     */
    void addOption(String string, String message, Function function){
        Printer.printInformationMessage(string + " -> " + message);
        options.put(string, function);
    }

    /**
     * Print title of the screen.
     * @param title of the screen.
     */
    /*package-local*/ void printScreenTitle(String title){
        Printer.printInformationMessage("\n[ " + title + " ]");
    }

}
