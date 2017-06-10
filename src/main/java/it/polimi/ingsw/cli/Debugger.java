package it.polimi.ingsw.cli;

/**
 * Class used to print messages on the standard output in order to debug.
 * Two type of debug message:
 * standard: used to communicate simple information, often successful ones.
 * debug: used to communicate important information, like error or particular exceptions.
 */
public class Debugger {

    /**
     * Print a standard message.
     * @param message to print.
     */
    public static void printStandardMessage(String message){
        System.out.println("[Message] > " + message);
    }

    /**
     * Print a debug message.
     * @param message to print.
     */
    public static void printDebugMessage(String message){
        System.out.println("[Debug] > " + message);
    }

    /**
     * Print a debug message with exception cause.
     * @param message to print.
     */
    public static void printDebugMessage(String message, Throwable throwable){
        System.out.println("[Debug] > " + message + " " + throwable.getMessage());
    }

    /**
     * Print a debug message with exception cause and the class who has thrown it.
     * @param message to print.
     */
    public static void printDebugMessage(String className, String message){
        System.out.println("[Debug] > [" + className + "] > " + message);
    }

    /**
     * Print a debug message with the class who has thrown it.
     * @param message to print.
     */
    public static void printDebugMessage(String className, String message, Throwable throwable){
        System.out.println("[Debug] > [" + className + "] > " + message + " " + throwable.getMessage());
    }

}
