package it.polimi.ingsw.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class BaseContxt {

    /**
     * Callback interfaces will contain the main context functions
     */
    private final ContxtInterface callback;

    protected BufferedReader keyboard= new BufferedReader((new InputStreamReader(System.in)));

    protected PrintWriter console= new PrintWriter(new OutputStreamWriter(System.out));

    private final HashMap<String, Command> commands;

    /**
     * Construct
     * @param contextInterface with the callback
     */
    BaseContxt(ContxtInterface contextInterface){
        callback=contextInterface;
        commands= new HashMap<>();
    }

    /**
     * Get the context
     * @return the context needed
     */
    ContxtInterface getContxt(){
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
    void addCommand(String key, Command command){
        commands.put(key, command);
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
