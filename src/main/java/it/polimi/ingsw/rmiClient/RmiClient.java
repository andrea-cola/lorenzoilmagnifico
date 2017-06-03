package it.polimi.ingsw.rmiClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import it.polimi.ingsw.rmiServer.RmiServerInterface;

public class RmiClient implements RmiClientInterface {

    private Registry registry;
    private RmiServerInterface server;

    public RmiClient(){
        super();
    }

    public void connectToServer(){
        try {
            registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            server = (RmiServerInterface) registry.lookup("RmiServerInterface");
            UnicastRemoteObject.exportObject(this, 0);
            registry.rebind("RmiClientInterface", this);
            //server.clientConnected();
        }catch(RemoteException e){
            System.out.println("Cannot connect the server - RemoteException");
        }catch(NotBoundException e) {
            System.out.println("Cannot connect the server - NotBoundException");
        }
    }

    public void serverConnected() throws RemoteException{
        System.out.println("Sei connesso al server.");
    }

}
