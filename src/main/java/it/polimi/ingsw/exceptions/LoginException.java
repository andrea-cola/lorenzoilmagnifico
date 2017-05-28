package it.polimi.ingsw.exceptions;

import java.io.IOException;


public class LoginException extends IOException{

    /**
     * Class exception constructor.
     */
    public LoginException(){
        super();
    }

    /**
     * Class exception constructor.
     * @param message about error.
     */
    public LoginException(String message){
        super(message);
    }

    /**
     * Class exception constructor.
     * @param message
     */
    public LoginException(LoginEnum message){
        super(message.toString());
    }

}
