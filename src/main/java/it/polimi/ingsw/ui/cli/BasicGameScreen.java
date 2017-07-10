package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.utility.Printer;

import java.util.Arrays;
import java.util.HashMap;


/*package-local*/ class BasicGameScreen {

    private HashMap<String, Function> options;

    /**
     * Class constructor
     */
    BasicGameScreen(){
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

    void addOption(String string, String message, Function function){
        Printer.printInformationMessage(string + " -> " + message);
        options.put(string, function);
    }

    /*package-local*/ void printScreenTitle(String title){
        Printer.printInformationMessage("\n[ " + title + " ]");
    }

}
