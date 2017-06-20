package it.polimi.ingsw.exceptions;

import org.omg.CORBA.UserException;

import java.io.IOException;

/**
 * This exception is thrown when errors occur in game.
 */
public class GameException extends UserException {

    /**
     * Game error type.
     */
    private GameErrorType gameErrorType;

    /**
     * Class constructor.
     */
    public GameException(){
        super();
    }

    /**
     * Class constructor.
     * @param gameErrorType of the error.
     */
    public GameException(GameErrorType gameErrorType){
        super();
        this.gameErrorType = gameErrorType;
    }

    /**
     * Class constructor.
     * @param message of the error.
     */
    public GameException(GameErrorType gameErrorType, String message){
        super(message);
        this.gameErrorType = gameErrorType;
    }

    /**
     * Method that return GameErrorType.
     * @return return the game error type.
     */
    public GameErrorType getError(){
        return this.gameErrorType;
    }


}
