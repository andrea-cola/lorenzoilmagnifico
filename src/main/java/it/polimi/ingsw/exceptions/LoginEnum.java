package it.polimi.ingsw.exceptions;

/**
 * Enumation of all login exception errors.
 */
public enum LoginEnum{

    /**
     * List of all login errors.
     */
    USER_NOT_REGISTERED("User is not registered."),
    USER_ALREADY_REGISTERED("User already registered."),
    WRONG_PASSWORD("Password is incorrect."),
    CANNOT_CONNECT_TO_DATABASE("Cannot connect to database."),
    SQL_PROBLEM("SQL query problem.")
    ;

    /**
     * Enumeration message.
     */
    private final String text;

    /**
     * Enumeration constructor.
     * @param message
     */
    LoginEnum(String message) {
        this.text = message;
    }

    /**
     * Give back the error message.
     * @return
     */
    public String toString(){
        return this.text;
    }
}
