package it.polimi.ingsw.exceptions;

/**
 * Created by andrea on 03/06/17.
 */
public class SigninException extends ConnectionException{

    /**
     * Class exception constructor.
     */
    public SigninException(){
        super();
    }

    /**
     * Class exception constructor.
     * @param message about error.
     */
    public SigninException(String message){
        super(message);
    }

    /**
     * Class exception constructor.
     * @param message
     */
    public SigninException(ExceptionsEnum message){
        super(message.toString());
    }

}
