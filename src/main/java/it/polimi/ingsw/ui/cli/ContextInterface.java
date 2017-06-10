package it.polimi.ingsw.ui.cli;

/**
 * This is the callback interface for every cli context
 */

/*
It has to have visibility only in the current package
 */
 interface ContextInterface {
    /**
     * Print something on the display
     * @param message to print
     */
     void print(String message);
}
