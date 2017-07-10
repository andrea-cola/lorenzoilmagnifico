package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.utility.Printer;

import java.util.List;

/**
 * Basic screen in game configuration.
 */
/*package-local*/ class BasicScreen {

    /**
     * Print screen title.
     * @param title of the screen.
     */
    /*package-local*/ void printScreenTitle(String title){
        Printer.printInformationMessage("\n[ " + title + " ]");
    }

    /**
     * Print message on the cli.
     * @param message to be printed.
     */
    /*package-local*/ void print(String message){
        Printer.printInformationMessage("-> " + message);
    }

    /**
     * Print list of messages.
     * @param messagesList to be printed.
     */
    /*package-local*/ void print(List<String> messagesList){
        int i = 1;
        for(String message : messagesList){
            Printer.printInformationMessage("(" + i + ") " + message);
            i++;
        }
    }

}
