package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;

import java.util.Arrays;
import java.util.HashMap;

public class BasicGameScreen {

    private HashMap<String, Function> options;

    BasicGameScreen(){
        options = new HashMap<>();
    }

    /**
     * Receive parameters from keyboard handler.
     * @param arguments of the command typed.
     * @throws WrongCommandException if command is wrong.
     */
    void sendParameters(String[] arguments) throws WrongCommandException{
        if(options.containsKey(arguments[0])){
            Function function = options.get(arguments[0]);
            String[] subArray = Arrays.copyOfRange(arguments, 1, arguments.length);
            function.doAction(subArray);
        }
        else
            throw new WrongCommandException();
    }

    void addOption(String string, String message, Function function){
        System.out.println(string + " -> " + message);
        options.put(string, function);
    }

    /*package-local*/ void printScreenTitle(String title){
        System.out.println("\n[ " + title + " ]");
    }

}
