package it.polimi.ingsw.utility;

/**
 * Class used to print messages on the standard output in order to debug.
 * Three type of debug message:
 * standard: used to communicate simple information, often successful ones.
 * debug: used to communicate important information, like error or particular exceptions.
 * information: used to communicate game information.
 */
public class Printer {

    public static void printInformationMessage(String message){
        System.out.println(message);
    }

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
