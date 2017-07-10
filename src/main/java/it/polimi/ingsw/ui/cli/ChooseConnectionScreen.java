package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.ui.ConnectionType;
import it.polimi.ingsw.utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Screen to handle connection type choice.
 */
/*package-local*/ class ChooseConnectionScreen extends BasicScreen {

    /**
     * Connection constants.
     */
    private static final int STD_PORT_SOCKET = 3031;
    private static final int STD_PORT_RMI = 3032;
    private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    /**
     * Message to be printed.
     */
    private List<String> cliMessages = new ArrayList<>();

    /**
     * Callback to user interface.
     */
    private final ICallback callback;

    /**
     * Connection type.
     */
    private ConnectionType connectionType;

    /**
     * Server address.
     */
    private String address;

    /**
     * Port number on the server.
     */
    private int port;

    /**
     * Keyboard reader.
     */
    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    /**
     * Class constructor.
     * @param callback to user interface.
     */
    /*package-local*/ ChooseConnectionScreen(ICallback callback){
        this.callback = callback;
        cliMessages.add("Connection based on Sockets.");
        cliMessages.add("Connection based on RMI.");
        printScreenTitle("NETWORK CONFIGURATION");
        print(cliMessages);
        readCommand();
        connect();
    }

    /**
     * Read command from keyboard.
     */
    private void readCommand(){
        try {
            int key;
            do {
                key = Integer.parseInt(keyboardReader.readLine());
            } while (key < 1 || key > cliMessages.size());
            if (key == 1) {
                this.connectionType = ConnectionType.SOCKET;
                this.port = STD_PORT_SOCKET;
            } else {
                this.connectionType = ConnectionType.RMI;
                this.port = STD_PORT_RMI;
            }
            readAddress();
        } catch (NumberFormatException | ClassCastException e) {
            readCommand();
        } catch (IOException e){
            Printer.printDebugMessage("Error while reading from keyboard.");
        }
    }

    /**
     * Read address from keyboard.
     * @throws IOException
     */
    private void readAddress() throws IOException{
        print("Server IP address");
        address = keyboardReader.readLine();
        try {
            validateIpAddress(address);
        } catch (WrongCommandException e){
            readAddress();
        }
    }

    /**
     * Validate ip address.
     * @throws WrongCommandException if address is wrong.
     */
    private void validateIpAddress(String address) throws WrongCommandException{
        boolean flag = PATTERN.matcher(address).matches();
        if(!flag)
            throw new WrongCommandException();
    }

    /**
     * Connect to the server.
     */
    private void connect() {
        try {
            this.callback.setNetworkSettings(connectionType, address, port);
        } catch (ConnectionException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "Error during connection.");
        }
    }

    /**
     * Callback interface.
     */
    @FunctionalInterface
    interface ICallback {
        void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException;
    }
}