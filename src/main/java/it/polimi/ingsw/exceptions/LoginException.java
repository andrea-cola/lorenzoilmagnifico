package it.polimi.ingsw.exceptions;


public class LoginException extends ConnectionException{

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
    public LoginException(ExceptionsEnum message){
        super(message.toString());
    }

}
