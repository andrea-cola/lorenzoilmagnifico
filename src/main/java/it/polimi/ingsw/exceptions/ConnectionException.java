package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * This exception is thrown if errors occur during connection proceedings.
 */
public class ConnectionException extends IOException{

    /**
     * Class constructor.
     */
    public ConnectionException(){
        super();
    }

    /**
     * Class constructor.
     * @param message to describe the error.
     */
    public ConnectionException(String message){
        super(message);
    }

    /**
     * Class constructor.
     * @param cause of the exception.
     */
    public ConnectionException(Throwable cause){
        super(cause);
    }

}
