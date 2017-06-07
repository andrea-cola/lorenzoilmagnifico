package it.polimi.ingsw.ui;

/**
 * This class represents the network context used by the command line user interface
 */
public class NetworkContext extends BaseContxt{

    private final NetworkCallback callback;
    private NetworkType networkType;
    private String address;
    private int port;



    NetworkContext(ContxtInterface contxtInterface, NetworkCallback callback){
        super(contxtInterface);
        this.callback=callback;
        this.address= "localhost";
        this.port=3031;
        addCommand("show-configuration", arguments -> printConfig());
        addCommand("set-newtworktype", arguments -> );
    }

    private void printConfig(){
        console.println("You are now connected to "+ address +" a the port " + port);
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
    void setNetworkSettings(NetworkType networkType, String address, int port);
}