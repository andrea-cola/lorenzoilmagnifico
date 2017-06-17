package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.utility.Debugger;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class BasicScreen {

    /**
     * Callback interface.
     */
    private final ScreenInterface screenInterface;

    protected BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    private final HashMap<String, Command> commands;

    /*package-local*/ BasicScreen(ScreenInterface screenInterface){
        this.screenInterface = screenInterface;
        commands = new HashMap<>();
    }

    /*package-local*/ void addPrintCommand(String key, Command command){
        commands.put(key, command);
    }

    protected void readCommand(){
        try {
            String line = keyboardReader.readLine();
            if(this != null)
                this.handle(line);
        } catch (IOException e) {
            Debugger.printDebugMessage(this.getClass().getSimpleName(), e);
        }
    }

    public void handle(String line) throws WrongCommandException{
        String[] segments = line.split(" ");
        if(segments.length > 0 && commands.containsKey(segments[0])) {
            try {
                commands.get(segments[0]).run(Arrays.copyOfRange(segments, 1, segments.length));
            } catch (WrongCommandException e) {
                Debugger.printDebugMessage(this.getClass().getSimpleName(), e);
            }
        }else
            throw new WrongCommandException();
    }
}
