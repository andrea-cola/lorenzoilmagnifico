package it.polimi.ingsw.exceptions;

public enum LoginErrorType {

    /**
     * List of all loginPlayer errors.
     */
    USER_NOT_EXISTS("User is not registered."),
    USER_ALREADY_EXISTS("User already registered."),
    USER_ALREADY_LOGGEDIN("User already logged in."),
    USER_WRONG_PASSWORD("Password is wrong."),
    GENERIC_SQL_ERROR("Errore SQL generico")
    ;

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
    public String toString(){
        return this.error;
    }

}
