package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.cli.Debugger;

import java.io.IOException;

/**
 * This class represents the login context used by the command line user interface
 */

public class LoginMenuContext extends BaseContext{

    private final LoginCallback callback;
    String username;
    String password;

    /**
     * Constructor for LoginMenuContext
     * @param contextInterface passed from the Command Line Interface class
     * @param callback for login function
     */
    LoginMenuContext(ContextInterface contextInterface, LoginCallback callback){
        super(contextInterface);
        this.callback= callback;
        addPrintCommand("login", arguments->login());
        read();
    }


    /**
     * Login function calls the callback loginPlayer function
     * @throws CommandNotValid if the command is not valid
     */
    private void login(){
        try {
            this.callback.loginPlayer(username, password);
        } catch (IOException e) {
            Debugger.printDebugMessage(this.getClass().getName(), e);
        }
    }
}

/**
 * This callback interface represents the main login context function
 */
@FunctionalInterface
    interface LoginCallback{
    /**
     * Let the player login to the game
     * @param username to use
     */
    void loginPlayer(String username, String password) throws IOException;
}