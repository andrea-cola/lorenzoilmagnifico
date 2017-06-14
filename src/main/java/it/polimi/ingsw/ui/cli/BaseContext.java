package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.cli.Debugger;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class BaseContext {

    /**
     * Callback interfaces will contain the main context functions
     */
    private final ContextInterface callback;

    /**
     * Buffer reader for reading input
     */
    protected BufferedReader keyboard= new BufferedReader((new InputStreamReader(System.in)));

    /**
     * Print writer for writing on the shell
     */
    protected PrintWriter console= new PrintWriter(new OutputStreamWriter(System.out));

    /**
     * Hash map associates the commands to a string value
     */
    private final HashMap<String, Command> commands;

    /**
     * Construct of the class
     * @param contextInterface with the callback
     */
    BaseContext(ContextInterface contextInterface){
        callback=contextInterface;
        commands= new HashMap<>();
    }

    /**
     * Get the context
     * @return the context needed
     */
    ContextInterface getContext(){
        return callback;
    }

    /**
     * Get the command
     * @param key of the command
     * @return the command needed
     */
    Command getCommand(String key){
        return commands.get(key);
    }

    /**
     * Print the command
     * @param key associated
     */
    void printCommandKey(String key){
        console.println(key);
    }

    /**
     * Add a command to the commands hash map
     * @param key of the command
     * @param command what is going to be execute
     */
    void addPrintCommand(String key, Command command){
        commands.put(key, command);
        printCommandKey(key);
    }

    protected void read(){
        try {
            String line = keyboard.readLine();
            if (this != null) {
                this.handle(line);
            }
        } catch (UnknownCommandException e) {
            Debugger.printDebugMessage(this.getClass().getSimpleName(), e);
        } catch (IOException g) {
            Debugger.printDebugMessage(this.getClass().getSimpleName(), g);
        }
    }
    /**
     * After having read the command line, the command has to be caught and then execute
     * @param line from the scanner
     * @throws UnknownCommandException if the command is not found
     */
    public void handle(String line) throws UnknownCommandException{
        String[] segments= line.split(" ");
        if(segments.length>0 && commands.containsKey(segments[0])) {
            try {
                commands.get(segments[0]).execute(Arrays.copyOfRange(segments, 1, segments.length));
            } catch (CommandNotValid e) {
                System.out.println(e);
            }
        }else
            throw new UnknownCommandException();
    }







}
