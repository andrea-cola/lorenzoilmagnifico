package it.polimi.ingsw.exceptions;

/**
 * Enumerations of possible login errors.
 */
public enum LoginErrorType {

    /**
     * List of all loginPlayer errors.
     */
    GENERIC_SQL_ERROR("Errore SQL generico"),
    USER_ALREADY_EXISTS("User already registered."),
    USER_ALREADY_LOGGEDIN("User already logged in."),
    USER_WRONG_PASSWORD("Password is wrong."),
    USER_NOT_EXISTS("Password is wrong.");

    /**
     * Enumeration message.
     */
    private final String error;

    /**
     * Enumeration constructor.
     * @param error error message.
     */
    LoginErrorType(String error) {
        this.error = error;
    }

    /**
     * Give back the error message.
     * @return error message.
     */
    @Override
    public String toString(){
        return this.error;
    }

}
