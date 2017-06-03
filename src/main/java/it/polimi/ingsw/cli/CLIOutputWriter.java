package it.polimi.ingsw.cli;

/**
 * Class used to format a message and print it on the standard output.
 */
public class CLIOutputWriter {

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

    /**
     * Print a message and the cause after an exception.
     * @param message
     * @param throwable
     */
    public static void printExceptionMessage(String message, Throwable throwable){
        System.out.println("[Exception] > " + message + " " + throwable.getMessage());
    }

}
