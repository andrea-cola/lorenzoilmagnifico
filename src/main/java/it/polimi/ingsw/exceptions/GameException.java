package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * This exception is thrown when errors occur in game.
 */
public class GameException extends IOException {

    /**
     * Class constructor.
     */
    public GameException(){
        super();
    }

    /**
     * Class constructor.
     * @param message of the error.
     */
    public GameException(String message){
        super(message);
    }

    /**
     * Class constructor.
     * @param cause of the error.
     */
    public GameException(Throwable cause){
        super(cause);
    }

    /**
     * Class constructor.
     * @param message of the error.
     * @param cause of the error.
     */
    public GameException(String message, Throwable cause){
        super(message, cause);
    }

}
