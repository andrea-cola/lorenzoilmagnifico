package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.utility.Debugger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoginSignInScreen extends BasicScreen {

    private final ICallback callback;

    private List<String> cliMessages = new ArrayList<>();

    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));


    LoginSignInScreen(ICallback callback){
        this.callback = callback;

        cliMessages.add("Login");
        cliMessages.add("Sign in");
        printScreenTitle("LOGIN | SIGN IN");
        print(cliMessages);
        readCommand();
    }

    private void readCommand(){
        try {
            int key;
            do {
                key = Integer.parseInt(keyboardReader.readLine());
            } while (key < 1 || key > cliMessages.size());
            if (key == 1) {
                login();
            } else {
                signIn();
            }
        } catch (ClassCastException | NumberFormatException e) {
            readCommand();
        } catch (IOException e){
            Debugger.printDebugMessage("Error while reading from keyboard.");
        }
    }

    private void login() throws IOException{
        print("Username");
        String username = keyboardReader.readLine();
        print("Password");
        String password = keyboardReader.readLine();
        if(username.equals("") || password.equals(""))
            login();
        else
            this.callback.loginPlayer(username, password, false);
    }

    private void signIn() throws IOException {
        print("Username");
        String username = keyboardReader.readLine();
        print("Password");
        String password = keyboardReader.readLine();
        if(username.equals("") || password.equals(""))
            login();
        else
            this.callback.loginPlayer(username, password, true);
    }

    @FunctionalInterface
    interface ICallback {
        void loginPlayer(String username, String password, boolean flag) throws LoginException;
    }
}