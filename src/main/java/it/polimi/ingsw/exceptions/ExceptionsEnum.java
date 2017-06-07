package it.polimi.ingsw.exceptions;

/**
 * Enumation of all loginPlayer exception errors.
 */
public enum ExceptionsEnum {

    /**
     * List of all loginPlayer errors.
     */
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
    ExceptionsEnum(String message) {
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
