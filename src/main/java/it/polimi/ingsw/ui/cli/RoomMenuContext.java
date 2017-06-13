package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.utility.Debugger;

import java.io.IOException;

/**
 * This class represents the room context used by the command line user interface
 */
public class RoomMenuContext extends BaseContext {
    private final RoomMenuCallback callback;


    /**
     * Constructor for RoomMenuContext
     * @param contextInterface passed from the Command Line Interface class
     * @param callback for setting game room options
     */
    RoomMenuContext(ContextInterface contextInterface, RoomMenuCallback callback) {
        super(contextInterface);
        this.callback=callback;
        addPrintCommand("set-players",  arguments ->setMaxPlayers());
        read();
    }

    /**
     * It allows the user to set the max number of player in a game room
     * @throws CommandNotValid if the command is not valid
     */
    private void setMaxPlayers() throws CommandNotValid {
        Debugger.printStandardMessage("-->Type 'max number of player' for the game room");
        try {
            int maxPlayer = Integer.parseInt(keyboard.readLine());
            if (maxPlayer > 1 && maxPlayer < 5) {
                this.callback.setMaxPlayer(maxPlayer);
            }else{
                throw new CommandNotValid("The number of player must be >= 2 and <= 4");
            }
        }catch (IOException e){
            Debugger.printDebugMessage(this.getClass().getName(), e);
        }
    }
}



/**
 * This callback interface represents the main game room context function
 */
@FunctionalInterface
interface RoomMenuCallback{
    /**
     * It set the game room with a max number of player
     * @param maxPlayer is the max number of player
     */
    void setMaxPlayer(int maxPlayer);

}