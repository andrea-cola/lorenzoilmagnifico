package it.polimi.ingsw.controller.exceptions;

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

}
