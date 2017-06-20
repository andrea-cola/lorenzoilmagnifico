package it.polimi.ingsw.lorenzo;

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

        System.out.println("LORENZO IL MAGNIFICO");
        do{
            System.out.println("Choose your user interface to play:");
            System.out.println("Type 1 to use COMMAND LINE INTERFACE");
            System.out.println("Type 2 to use  INTERFACE");
            choise = Integer.parseInt(scanner.nextLine());
        }while(choise != 1 && choise != 2);
        return choise;
    }

}
