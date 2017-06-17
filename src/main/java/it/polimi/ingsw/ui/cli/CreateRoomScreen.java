package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;

public class CreateRoomScreen extends BasicScreen{

    private ICallback callback;

    CreateRoomScreen(ScreenInterface screenInterface, ICallback callback){
        super(screenInterface);

        System.out.println("\n\n[CREATE ROOM]");
        this.callback = callback;
        addPrintCommand("create-room", arguments->createRoom(arguments));
        printHelps();
        readCommand();
    }

    private void printHelps(){
        System.out.println("Helps: create-room [max players number]");
    }

    private void createRoom(String[] arguments) throws WrongCommandException{
        if(arguments.length == 1)
            this.callback.createRoom(Integer.parseInt(arguments[0]));
        else
            throw new WrongCommandException();
    }

    @FunctionalInterface
    public interface ICallback{
        void createRoom(int maxPlayer);
    }

}