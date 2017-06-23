package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.utility.Debugger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CreateRoomScreen extends BasicScreen{

    private ICallback callback;

    private List<String> cliMessages = new ArrayList<>();

    private BufferedReader keyboardReader = new BufferedReader((new InputStreamReader(System.in)));

    CreateRoomScreen(ICallback callback){
        this.callback = callback;

        cliMessages.add("No room available. You need to create a new room.");
        printScreenTitle("CREATE ROOM");
        print(cliMessages);
        createRoom();
    }

    private void createRoom(){
        print("Number of player allowed in the room");
        try{
            int key;
            do{
                key = Integer.parseInt(keyboardReader.readLine());
                print("Number of player not valid");
            } while (key < 2 || key > 4);
            print("Creating new room. Please wait...");
            Thread.sleep(2000);
            this.callback.createRoom(key);
        } catch (InterruptedException | ClassCastException | NumberFormatException e){
            createRoom();
        } catch (IOException e){
            Debugger.printDebugMessage("Error while reading from keyboard.");
        }
    }

    @FunctionalInterface
    public interface ICallback{
        void createRoom(int maxPlayer);
    }

}