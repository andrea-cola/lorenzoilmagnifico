package it.polimi.ingsw.gameLauncher;

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args){
        GameLauncher gameLauncher = new GameLauncher(chooseInterface());
    }

    private static String chooseInterface(){
        String choise;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Select the user interface you would like to play Lorenzo Il Magnifico:");
            System.out.println("--> Type 1 to use CLI");
            System.out.println("--> Type 2 to use GUI");
            choise = scanner.nextLine();
            if(choise.equals("1"))
                return new String("cli");
            else if(choise.equals("2"))
                return new String("gui");
            else
                System.out.println("You typed a not valid option. Retry.");
        }
    }

}
