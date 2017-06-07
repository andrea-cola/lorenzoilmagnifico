package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.exceptions.ServerException;
import it.polimi.ingsw.rmiClient.RMIClientInterface;
import it.polimi.ingsw.server.AbstractPlayer;
import it.polimi.ingsw.server.AbstractServer;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.exceptions.LoginException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;

public class RMIServer extends AbstractServer implements RMIServerInterface {

    /**
     * RMI registry.
     */
    private Registry registry;

    /**
     * Client interface.
     */
    private RMIClientInterface RMIClientInterface;

    /**
     * Logged in users cache.
     */
    private final HashMap<String, String> userCache;

    private static final String RMIServerInterfaceName = "RMIServerInterface";

    /**
     * Class constructor.
     * @param serverInterface
     */
    public RMIServer(ServerInterface serverInterface){
        super(serverInterface);
        userCache = new HashMap<>();
    }

    public void startServer(int port) throws IOException{
        registry = createOrLoadRegistry(port);
        publishObject(port);
    }

    private Registry createOrLoadRegistry(int port) throws ServerException{
        try{
            return LocateRegistry.createRegistry(port);
        }catch(RemoteException e){
            Debugger.printDebugMessage("[" + this.getClass().getName() + "] : RMI registry already exists.");
        }
        try{
            return LocateRegistry.getRegistry(port);
        }catch(RemoteException e){
            Debugger.printDebugMessage("[" + this.getClass().getName() + "] : RMI registry cannot be loaded.");
        }
        throw new ServerException("[" + this.getClass().getName() + "] : RMI interface cannot be loaded.");
    }

    private void publishObject(int port) throws IOException{
        try {
            registry.rebind(RMIServerInterfaceName, this);
            UnicastRemoteObject.exportObject(this, port);
        }catch(RemoteException e){
            e.printStackTrace();
            throw new ServerException("[" + this.getClass().getName() + "] : Fail during server interface loading. RMI Server is not working.");
        }
    }

    public AbstractPlayer getPlayer(String uniqueID){
        return getServer().getUser(userCache.get(uniqueID));
    }

    @Override
    public String loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException{
        getServer().loginPlayer(new RMIPlayer(rmiPlayer), username, password);
        String uniqueID = UUID.randomUUID().toString();
        userCache.put(uniqueID, username);
        return uniqueID;
    }

    @Override
    public void signInPlayer(String username, String password) throws IOException {
        getServer().signInPlayer(username, password);
    }


}