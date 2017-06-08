package it.polimi.ingsw.ui;

import java.io.IOException;

/**
 * This class represents the login context used by the command line user interface
 */

public class LoginContext extends BaseContext{

    private final LoginCallback callback;
    String nickname;
    String password;

    /**
     * Constructor for LoginContext
     * @param contextInterface passed in the Command Line Interface
     * @param callback for login function
     */
    LoginContext(ContextInterface contextInterface, LoginCallback callback){
        super(contextInterface);
        this.callback= callback;
        addCommand("login", arguments->login());
    }

    private void printCommands(LoginContext loginContext){

    }

    /**
     * Login function calls the callback loginPlayer function
     * @throws CommandNotValid if the command is not valid
     */
    private void login(){
        try {
            this.callback.loginPlayer(nickname, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * This callback interface represents the main login context functions
 */
@FunctionalInterface
    interface LoginCallback{
    /**
     * Let the player login to the game
     * @param nickname to use
     */
    void loginPlayer(String nickname, String password) throws IOException;
}