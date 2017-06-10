package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.cli.Debugger;

import java.io.IOException;

/**
 * This class represents the network context used by the command line user interface
 */
public class NetworkMenuContext extends BaseContext{

    private final NetworkCallback callback;
    private NetworkType networkType;
    private String address;
    private int port;

    /**
     * Constructor for NetworkMenuContext
     * @param contextInterface passed from Command Line Interface class
     * @param callback for setting network options
     */

    NetworkMenuContext(ContextInterface contextInterface, NetworkCallback callback){
        super(contextInterface);
        this.callback=callback;
        this.networkType=NetworkType.SOCKET;
        this.address= "localhost";
        this.port=3031;
        printConfig();
        addPrintCommand("show-configuration", arguments -> printConfig());
        addPrintCommand("set-networkType", arguments ->setNetworkType(arguments));
        addPrintCommand("set-address", arguments -> setAddress(arguments));
        addPrintCommand("set-port", arguments -> setPort(arguments));
        addPrintCommand("connect", arguments -> connect());
        read();
    }

    /**
     * It prints the network configuration
     */
    private void printConfig(){
        console.println("You are now connected to "+ address +" at port " + port);
    }

    /**
     * It allows the user to set or change the network type
     * @param arguments network type
     * @throws CommandNotValid if the command is not valid
     */
    private void setNetworkType(String[] arguments) throws CommandNotValid {
        if(arguments.length==1){
            switch (arguments[0]){
                case "socket":
                        networkType= NetworkType.SOCKET;
                        port=3031;
                        break;
                case "rmi":
                    networkType= NetworkType.RMI;
                    port=3032;
                    break;
                default:
                    throw new CommandNotValid("The network type is not valid");
            }
        }else {
            throw new CommandNotValid();
        }
        printConfig();
    }

    /**
     * It allows the user to set or change the network address
     * @param arguments address
     * @throws CommandNotValid if the command is not valid
     */
    private void setAddress(String[] arguments) throws CommandNotValid {
        if(arguments.length==1){
            address=arguments[0];
            printConfig();
        }else{
            throw new CommandNotValid("The network address is not valid");
        }
    }

    /**
     * It allows the user to set or change the network port
     * @param arguments port
     * @throws CommandNotValid if the command is not valid
     */
    private void setPort(String[] arguments) throws CommandNotValid {
        if(arguments.length==1){
            int p=Integer.parseInt(arguments[0]);
            if(p>1024 && p<=65535){
                this.port=p;
                printConfig();
            }else{
                throw new CommandNotValid("The network port is not valid");
            }
        }else{
            throw new CommandNotValid();
        }
    }

    /**
     * Connect function calls the callback setNetworkSettings function
     * @throws IOException
     */
    private void connect() {
        try {
            this.callback.setNetworkSettings(networkType, address, port);
        } catch (IOException e) {
            Debugger.printDebugMessage(this.getClass().getName(), e);
        }
    }
}

/**
 * This callback interface represents the main network context function
 */
@FunctionalInterface
interface NetworkCallback{
    /**
     * It set the network settings previously decided by the user
     * @param networkType is the type of network
     * @param address is the network address
     * @param port is the network port
     */
    void setNetworkSettings(NetworkType networkType, String address, int port) throws IOException;
}