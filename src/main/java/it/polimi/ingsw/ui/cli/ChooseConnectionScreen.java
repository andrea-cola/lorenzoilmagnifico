package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.ui.ConnectionType;
import it.polimi.ingsw.utility.Debugger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*package-local*/ class ChooseConnectionScreen extends BasicScreen {

    private static final int STD_PORT_SOCKET = 3031;
    private static final int STD_PORT_RMI = 3032;
    private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private List<String> cliMessages = new ArrayList<>();

    private final ICallback callback;
    private ConnectionType connectionType;
    private String address;
    private int port;
    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    ChooseConnectionScreen(ICallback callback){
        this.callback = callback;
        cliMessages.add("Connection based on Sockets.");
        cliMessages.add("Connection based on RMI.");
        printScreenTitle("NETWORK CONFIGURATION");
        print(cliMessages);
        readCommand();
        connect();
    }

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
            Debugger.printDebugMessage("Error while reading from keyboard.");
        }
    }

    private void readAddress() throws IOException{
        print("Server IP address");
        address = keyboardReader.readLine();
        try {
            validateIpAddress(address);
        } catch (WrongCommandException e){
            readAddress();
        }
    }

    private void validateIpAddress(String address) throws WrongCommandException{
        boolean flag = PATTERN.matcher(address).matches();
        if(!flag)
            throw new WrongCommandException();
    }

    private void connect() {
        try {
            this.callback.setNetworkSettings(connectionType, address, port);
        } catch (ConnectionException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error during connection.");
        }
    }

    @FunctionalInterface
    interface ICallback {
        void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException;
    }
}