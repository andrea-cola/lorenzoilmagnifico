package it.polimi.ingsw.cli;

/**
 * Class used to format a message and print it on the standard output.
 */
public class Debugger {

    /**
     * Print a standard communciation.
     * @param message
     */
    public static void printStandardMessage(String message){
        System.out.println("[Message] > " + message);
    }

    /**
     * Print a debug message.
     * @param message
     */
    public static void printDebugMessage(String message){
        System.out.println("[Debug] > " + message);
    }

    /**
     * Print a debug message.
     * @param message
     */
    public static void printDebugMessage(String message, Throwable throwable){
        System.out.println("[Debug] > " + message + " " + throwable.getMessage());
    }

}
