package it.polimi.ingsw.ui;

import java.io.IOException;

/**
 * This class represents the login context used by the command line user interface
 */

public class LoginContxt extends BaseContxt{

    private final LoginCallback callback;

    /**
     * Constructor for LoginContxt
     * @param contxtInterface passed in the Command Line Interface
     * @param callback for login function
     */
    LoginContxt(ContxtInterface contxtInterface, LoginCallback callback){
        super(contxtInterface);
        this.callback= callback;
        addCommand("login", arguments->this.login(arguments));
    }

    /**
     * Login function
     * @param arguments of the command execute function
     * @throws CommandNotValid if the command is not valid
     */
    private void login(String[] arguments) throws CommandNotValid{
        if(arguments.length==1) {
            this.callback.loginPlayer(arguments[0]);
        }else{
            throw new CommandNotValid();
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
    void loginPlayer(String nickname);
}