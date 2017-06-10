package it.polimi.ingsw.ui.cli;

/**
 * This Exception is thrown if the commands are not valid
 */
class CommandNotValid extends Exception{

    /**
     * Contructor
     */
    public CommandNotValid(){
        super();
    }

    /**
     * Contractor with error message
     * @param message error messagge
     */
    public CommandNotValid(String message){
        super(message);
    }

}
