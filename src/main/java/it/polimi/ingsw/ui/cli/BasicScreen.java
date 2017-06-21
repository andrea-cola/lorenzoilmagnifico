package it.polimi.ingsw.ui.cli;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class BasicScreen {

    protected BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    /*package-local*/ void printScreenTitle(String title){
        System.out.println("\n[ " + title + " ]");
    }

    /*package-local*/ void print(String message){
        System.out.println("-> " + message);
    }

    /*package-local*/ void print(List<CLIMessages> messagesList){
        int i = 1;
        for(CLIMessages message : messagesList){
            System.out.println("(" + i + ") " + message.toString());
            i++;
        }
    }

}
