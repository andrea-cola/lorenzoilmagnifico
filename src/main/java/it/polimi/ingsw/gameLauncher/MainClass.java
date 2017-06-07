package it.polimi.ingsw.gameLauncher;

import it.polimi.ingsw.cli.Debugger;

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args){
        GameLauncher gameLauncher = new GameLauncher(chooseInterface());
    }

    private static String chooseInterface(){
        String choise;
        Scanner scanner = new Scanner(System.in);
        while(true){
            Debugger.printStandardMessage("Select the user interface you would like to play Lorenzo Il Magnifico:");
            Debugger.printStandardMessage("--> Type 1 to use CLI");
            Debugger.printStandardMessage("--> Type 2 to use GUI");
            choise = scanner.nextLine();
            if(choise.equals("1"))
                return new String("cli");
            else if(choise.equals("2"))
                return new String("gui");
            else
                Debugger.printDebugMessage("[MainClass.java] : You typed a not valid option. Retry.");
        }
    }

}
