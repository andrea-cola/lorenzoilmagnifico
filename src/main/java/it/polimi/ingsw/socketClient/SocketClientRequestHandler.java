package it.polimi.ingsw.socketClient;

import it.polimi.ingsw.cli.CLIOutputWriter;
import it.polimi.ingsw.client.ClientInterface;
import sun.security.ssl.Debug;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class used to handle responses from the server.
 */
/*package-local*/ class SocketClientRequestHandler extends Thread {

    private ObjectInputStream objectInputStream;

    /*package-local*/ SocketClientRequestHandler(ObjectInputStream objectInputStream){
        this.objectInputStream = objectInputStream;
    }

   @Override
    public void run(){

       boolean flag = true;

       while(flag){
           try{
                Object object = objectInputStream.readObject();
           } catch (IOException | ClassNotFoundException e){
               CLIOutputWriter.printDebugMessage("[SocketClientRequestHandler.java] : Cannot read response.", e);
               flag = false;
           }
       }
       closeConnection(objectInputStream);
   }

    /**
     * Method to close the stream passed as argument.
     * @param connection
     */
    private void closeConnection(Closeable connection){
        try {
            connection.close();
        }catch(IOException e){
            CLIOutputWriter.printDebugMessage("[SocketClientRequestHandler.java] : Error while closing connections.", e);
        }
    }

}
