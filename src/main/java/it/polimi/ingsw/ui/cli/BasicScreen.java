package it.polimi.ingsw.ui.cli;

import java.util.List;

public class BasicScreen {

    /*package-local*/ void printScreenTitle(String title){
        System.out.println("\n[ " + title + " ]");
    }

    /*package-local*/ void print(String message){
        System.out.println("-> " + message);
    }

    /*package-local*/ void print(List<String> messagesList){
        int i = 1;
        for(String message : messagesList){
            System.out.println("(" + i + ") " + message.toString());
            i++;
        }
    }

}
