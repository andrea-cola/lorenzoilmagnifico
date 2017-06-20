package it.polimi.ingsw.ui.cli;

public enum CLIMessages {

    /**
     * Cli Titles
     */
    NETWORK_TITLE("NETWORK CONFIGURATION"),

    /**
     * CLI Messages
     */
    STARTING_ALERT("To use the Command Line Interface you need to insert the number\n" +
            "correspondent to command and follow the instructions."),

    SOCKET_CONNECTION("Connection based on Sockets."),
    RMI_CONNECTION("Connection based on RMI."),
    LOGIN("Login."),
    SIGNIN("Sign in."),
    JOIN_ROOM("Joining first room available. Please wait..."),
    CREATE_ROOM("No room available. You need to create a new room."),
    PERSONAL_TILE_CHOICE("Choose your personal board tile."),
    LEADER_CARD_CHOICE("Choose your leader card");

    private final String message;

    CLIMessages(String message){
        this.message =  message;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
