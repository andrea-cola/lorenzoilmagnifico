package it.polimi.ingsw.exceptions;

/**
 * This exception is thrown when an error occurs while a player is trying
 * to access in or create a room.
 */
public class RoomException extends Exception{

    /**
     * Class constructor.
     */
    public RoomException(){
        super();
    }

    /**
     * Class constructor.
     * @param message
     */
    public RoomException(String message){
        super(message);
    }

}
