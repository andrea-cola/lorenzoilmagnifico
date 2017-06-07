package it.polimi.ingsw.exceptions;

import java.io.IOException;

public class NetworkException extends IOException{

    public NetworkException(){
        super();
    }

    public NetworkException(String message){
        super(message);
    }

    public NetworkException(Throwable cause){
        super(cause);
    }

    public NetworkException(String message, Throwable cause){
        super(message, cause);
    }

}
