package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Create room screen.
 */
/*package-local*/ class CreateRoomScreen extends BasicScreen{

    /**
     * Callback interface.
     */
    private ICallback callback;

    /**
     * List of messages to be printed.
     */
    private List<String> cliMessages = new ArrayList<>();

    /**
     * Keyboard listener.
     */
    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    /**
     * Class constructor.
     * @param callback
     */
    /*package-local*/ CreateRoomScreen(ICallback callback){
        this.callback = callback;
        cliMessages.add("No room available. You need to create a new room.");
        printScreenTitle("CREATE ROOM");
        print(cliMessages);
        createRoom();
    }

    /**
     * Create room function.
     */
    private void createRoom(){
        print("Number of player allowed in the room");
        try{
            int key = Integer.parseInt(keyboardReader.readLine());
            while(key < 2 || key > 4){
                print("Number of player not valid, please retry...");
                key = Integer.parseInt(keyboardReader.readLine());
            }
            print("Creating new room. Please wait...");
            this.callback.createRoom(key);
        } catch (ClassCastException | NumberFormatException e){
            createRoom();
        } catch (IOException e){
            Printer.printDebugMessage("Error while reading from keyboard.");
        }
    }

    /**
     * Callback interface.
     */
    @FunctionalInterface
    public interface ICallback{
        void createRoom(int maxPlayer);
    }

}