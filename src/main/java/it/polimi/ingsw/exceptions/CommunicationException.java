package it.polimi.ingsw.exceptions;

import java.io.IOException;

public class CommunicationException extends IOException{

    public CommunicationException(){
        super();
    }

    public CommunicationException(String message){
        super(message);
    }

    public CommunicationException(Throwable cause){
        super(cause);
    }

}
