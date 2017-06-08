package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * This exception is thrown when errors occur in network communications.
 */
public class NetworkException extends IOException{

    /**
     * Class constructor.
     */
    public NetworkException(){
        super();
    }

    /**
     * Class constructor.
     * @param message of the error.
     */
    public NetworkException(String message){
        super(message);
    }

    /**
     * Class constructor.
     * @param cause of the error.
     */
    public NetworkException(Throwable cause){
        super(cause);
    }

    /**
     * Class constructor.
     * @param message of the error.
     * @param cause of the error.
     */
    public NetworkException(String message, Throwable cause){
        super(message, cause);
    }

}
