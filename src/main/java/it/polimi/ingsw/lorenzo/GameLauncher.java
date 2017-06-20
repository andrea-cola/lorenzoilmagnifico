package it.polimi.ingsw.lorenzo;

import it.polimi.ingsw.ui.cli.CLIMessages;

import java.util.Scanner;

/**
 * This class is used to launch the client side game.
 */
public class GameLauncher {

    public static void main(String[] args) throws InterruptedException {
        LorenzoIlMagnifico game = new LorenzoIlMagnifico(chooseUI());
        game.start();
    }

    /**
     * get the index of the preferred user interface.
     * @return user interface index.
     */
    private static int chooseUI(){
        int choise;
        Scanner scanner = new Scanner(System.in);

        System.out.println(CLIMessages.STARTING_ALERT.toString() + "\n");
        do{
            System.out.println("Choose your user interface:");
            System.out.println("(1) COMMAND LINE INTERFACE");
            System.out.println("(2) GRAPHICAL INTERFACE");
            choise = Integer.parseInt(scanner.nextLine());
        }while(choise != 1 && choise != 2);
        return choise;
    }

}
