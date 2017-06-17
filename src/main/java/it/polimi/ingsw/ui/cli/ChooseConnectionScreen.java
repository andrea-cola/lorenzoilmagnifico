package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.utility.Debugger;

public class ChooseConnectionScreen extends BasicScreen {

    private static final String STD_ADDRESS = "127.0.0.1";
    private static final int STD_PORT_SOCKET = 3031;
    private static final int STD_PORT_RMI = 3032;
    private static final ConnectionType STD_CONN_TYPE = ConnectionType.SOCKET;

    private final ICallback callback;
    private ConnectionType connectionType;
    private String address;
    private int port;

    ChooseConnectionScreen(ScreenInterface screenInterface, ICallback callback){
        super(screenInterface);
        this.callback = callback;
        this.connectionType = STD_CONN_TYPE;
        this.address = STD_ADDRESS;
        this.port = STD_PORT_SOCKET;
        addPrintCommand("set-conn", arguments -> setConnection(arguments));
        addPrintCommand("connect", arguments -> connect());
        printHelps();
        readCommand();
    }

    private void printHelps(){
        System.out.println("set-conn [SOCKET | RMI] [address] [port]");
        System.out.println("connect");
    }

    private void printConfig(){
        System.out.println("Type: " + connectionType.toString() + " @ " + address +":" + port);
    }

    private void setConnection(String[] arguments) throws WrongCommandException {
        if(arguments.length == 3){
            setConnectionType(arguments[0]);
            this.address = arguments[1];
            this.port = Integer.parseInt(arguments[2]);
        }else
            throw new WrongCommandException();
        printConfig();
        connect();
    }

    private void setConnectionType(String type) throws WrongCommandException{
        switch (type) {
            case "socket":
                connectionType = ConnectionType.SOCKET;
                break;
            case "rmi":
                connectionType = ConnectionType.RMI;
                break;
            default:
                throw new WrongCommandException();
        }
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