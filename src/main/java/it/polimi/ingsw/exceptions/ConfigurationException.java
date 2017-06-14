package it.polimi.ingsw.exceptions;

/**
 * This class represents configuration exception that is thrown when errors occur
 * during configuration and parsing proceedings.
 */
public class ConfigurationException extends NetworkException{

    /**
     * Class constructor.
     */
    public ConfigurationException(){
        super();
    }

    /**
     * Class constructor.
     */
    public ConfigurationException(String message){
        super(message);
    }

    /**
     * Class constructor.
     */
    public ConfigurationException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Class constructor.
     */
    public ConfigurationException(Throwable cause){
        super(cause);
    }

}
