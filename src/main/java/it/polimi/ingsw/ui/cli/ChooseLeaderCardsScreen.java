package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Choose leader card screen.
 */
public class ChooseLeaderCardsScreen extends BasicScreen{

    /**
     * Callback interface.
     */
    private ICallback callback;

    /**
     * Leader card to be chosen list.
     */
    private List<LeaderCard> leaderCards;

    /**
     * List of messages to be printed.
     */
    private List<String> cliMessages;

    /**
     * Keyboard listener.
     */
    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    /**
     * Class constructor.
     * @param callback interface.
     * @param leaderCardList to be chosen.
     */
    /*package-local*/ ChooseLeaderCardsScreen(ICallback callback, List<LeaderCard> leaderCardList) {
        this.callback = callback;

        cliMessages = new ArrayList<>();
        cliMessages.add("Choose your leader card.");
        printScreenTitle("LEADER CARD CHOICE");
        this.leaderCards = leaderCardList;
        print(cliMessages);
        chooseLeaderCard();
    }

    /**
     * Print leader cards.
     */
    private void printLeaderCards(){
        int i = 1;
        for(LeaderCard leaderCard : leaderCards){
            print("[" + i + "] " + leaderCard.getLeaderCardName());
            i++;
        }
    }

    /**
     * Handle the choice.
     */
    private void chooseLeaderCard(){
        try {
            int key;
            do {
                printLeaderCards();
                key = Integer.parseInt(keyboardReader.readLine());
            } while (key < 1 || key > leaderCards.size());
            key = key - 1;
            this.callback.sendLeaderCardsChoose(leaderCards.get(key));
        } catch (ClassCastException | NumberFormatException e) {
            chooseLeaderCard();
        } catch (IOException e){
            Printer.printDebugMessage("Error while reading from keyboard.");
        }
    }

    /**
     * Callback interface.
     */
    @FunctionalInterface
    public interface ICallback{
        void sendLeaderCardsChoose(LeaderCard leaderCard);

    }
}
