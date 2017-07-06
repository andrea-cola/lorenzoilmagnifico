package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.utility.Printer;

import java.util.List;

/*package-local*/ class BasicScreen {

    /*package-local*/ void printScreenTitle(String title){
        System.out.println("\n[ " + title + " ]");
    }

    /*package-local*/ void print(String message){
        System.out.println("-> " + message);
    }

    /*package-local*/ void print(List<String> messagesList){
        int i = 1;
        for(String message : messagesList){
            Printer.printInformationMessage("(" + i + ") " + message);
            i++;
        }
    }

}
