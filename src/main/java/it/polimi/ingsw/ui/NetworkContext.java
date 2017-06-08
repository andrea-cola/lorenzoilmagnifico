package it.polimi.ingsw.ui;

import java.io.IOException;

/**
 * This class represents the network context used by the command line user interface
 */
public class NetworkContext extends BaseContext{

    private final NetworkCallback callback;
    private NetworkType networkType;
    private String address;
    private int port;

    /**
     * Constructor for NetworkContext
     * @param contextInterface passed in Command Line Interface
     * @param callback for setting network options
     */

    NetworkContext(ContextInterface contextInterface, NetworkCallback callback){
        super(contextInterface);
        this.callback=callback;
        this.networkType=NetworkType.SOCKET;
        this.address= "localhost";
        this.port=3031;
        addCommand("show-configuration", arguments -> printConfig());
        addCommand("set-networktype", arguments ->setNetworkType(arguments));
        addCommand("set-address", arguments -> setAddress(arguments));
        addCommand("set-port", arguments -> setPort(arguments));
        addCommand("connect", arguments -> connect());
    }

    /**
     * It prints the network configuration
     */
    private void printConfig(){
        console.println("You are now connected to "+ address +" a the port " + port);
    }

    /**
     * It allows the user to set or change the network type
     * @param arguments network type
     * @throws CommandNotValid if the command is not valid
     */
    private void setNetworkType(String[] arguments) throws CommandNotValid {
        if(arguments.length==1){
            if(arguments[0]=="socket"){
                networkType= NetworkType.SOCKET;
                port=3031;
            }else if(arguments[0]== "rmi"){
                networkType= NetworkType.RMI;
                port=3032;
            }else {
                throw new CommandNotValid();
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
            throw new CommandNotValid();
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
                throw new CommandNotValid();
            }
        }else{
            throw new CommandNotValid();
        }
    }

    /**
     * Connect function calls the callback setNetworkSettings funcion
     * @throws IOException
     */
    private void connect() {
        try {
            this.callback.setNetworkSettings(networkType, address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * This callback interface represents the main network context functions
 */
@FunctionalInterface
interface NetworkCallback{
    /**
     * Let the user to set the network settings
     * @param networkType
     * @param address
     * @param port
     */
    void setNetworkSettings(NetworkType networkType, String address, int port) throws IOException;
}