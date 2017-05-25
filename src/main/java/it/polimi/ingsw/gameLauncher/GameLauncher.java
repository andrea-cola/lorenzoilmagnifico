package it.polimi.ingsw.gameLauncher;

import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.ui.AbstractUI;

public class GameLauncher implements ClientInterface {

    private AbstractUI abstractUI;

    private String username;

    private String password;

    GameLauncher(String ui){

        if(ui.equals("cli")){
            // abstractUI = new CLI();
        }
        else if(ui.equals("gui")){
            // abstractUI = new GUI();
        }
        else{
            // throw exception
        }

    }

}
