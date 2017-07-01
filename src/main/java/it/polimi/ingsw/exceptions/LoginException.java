package it.polimi.ingsw.exceptions;

/**
 * This exception class is thrown when loginPlayer error occurs.
 */
public class LoginException extends NetworkException {

    /**
     * Login error type.
     */
    private final LoginErrorType loginErrorType;

    /**
     * Class constructor.
     */
    public LoginException(){
        super();
        loginErrorType = LoginErrorType.USER_WRONG_PASSWORD;
    }

    /**
     * Class constructor.
     * @param loginErrorType loginPlayer error type enumeration.
     */
    public LoginException(LoginErrorType loginErrorType){
        super();
        this.loginErrorType = loginErrorType;
    }

    /**
     * Class constructor.
     * @param message of the error.
     */
    public LoginException(LoginErrorType loginErrorType, String message){
        super(message);
        this.loginErrorType = loginErrorType;
    }

    /**
     * Method that return LoginErrorType.
     * @return return the loginPlayer error type.
     */
    public LoginErrorType getError(){
        return this.loginErrorType;
    }

}
