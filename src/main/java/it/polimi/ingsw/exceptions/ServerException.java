package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Exception thrown when server initialization problem occurs.
 */
public class ServerException extends IOException {

    /**
     * Class constructor.
     * @param message of the error.
     */
    public ServerException(String message){
        super(message);
    }

    /**
     * Class constructor.
     * @param message message of the error.
     * @param throwable cause of the error.
     */
    public ServerException(String message, Throwable throwable){
        super(message, throwable);
    }

}
