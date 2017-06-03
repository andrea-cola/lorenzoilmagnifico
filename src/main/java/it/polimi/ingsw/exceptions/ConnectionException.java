package it.polimi.ingsw.exceptions;

import java.io.IOException;

public class ConnectionException extends IOException{

    public ConnectionException(){
        super();
    }

    public ConnectionException(String message){
        super(message);
    }

    public ConnectionException(Throwable cause){
        super(cause);
    }

}
