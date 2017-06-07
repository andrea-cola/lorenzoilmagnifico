package it.polimi.ingsw.ui;

/**
 * This interface represents the single command to execute
 */
@FunctionalInterface
 interface Command {
    /**
     * Execute the command
     * @param arguments passed by the player
     * @throws CommandNotValid
     */
    void execute(String[] arguments) throws CommandNotValid;

}
