package it.polimi.ingsw.client;

import it.polimi.ingsw.utility.Printer;

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
     * Get the index of the preferred user interface.
     * @return user interface index.
     */
    private static int chooseUI(){
        int choice = 0;
        Scanner scanner = new Scanner(System.in);

        Printer.printInformationMessage("To use the Command Line Interface you need to insert the number\ncorrespondent to command and follow the instructions.");
        do{
            try {
                Printer.printInformationMessage("Choose your user interface:");
                Printer.printInformationMessage("(1) COMMAND LINE INTERFACE");
                Printer.printInformationMessage("(2) GRAPHICAL INTERFACE");
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                return chooseUI();
            }
        } while (choice != 1 && choice != 2);
        return choice;
    }

}
