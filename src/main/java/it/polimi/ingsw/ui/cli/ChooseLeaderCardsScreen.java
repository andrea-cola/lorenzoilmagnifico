package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.utility.Debugger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChooseLeaderCardsScreen extends BasicScreen{

    private ICallback callback;

    private List<LeaderCard> leaderCards;

    private List<String> cliMessages;

    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));


    ChooseLeaderCardsScreen(ICallback callback, List<LeaderCard> leaderCardList) {
        this.callback = callback;

        cliMessages = new ArrayList<>();
        cliMessages.add("Choose your leader card.");
        printScreenTitle("LEADER CARD CHOICE");
        this.leaderCards = leaderCardList;
        print(cliMessages);
        chooseLeaderCard();
    }

    private void printLeaderCards(){
        int i = 1;
        for(LeaderCard leaderCard : leaderCards){
            print("[" + i + "] " + leaderCard.getLeaderCardName());
            i++;
        }
    }

    private void chooseLeaderCard(){
        try {
            int key;
            do {
                printLeaderCards();
                key = Integer.parseInt(keyboardReader.readLine());
            } while (key < 1 || key > leaderCards.size());
            key = key - 1;
            this.callback.sendLeaderCardsChoose(leaderCards.get(key));
        } catch (ClassCastException e) {
            chooseLeaderCard();
        } catch (IOException e){
            Debugger.printDebugMessage("Error while reading from keyboard.");
        }
    }

    @FunctionalInterface
    public interface ICallback{

        void sendLeaderCardsChoose(LeaderCard leaderCard);

    }
}
