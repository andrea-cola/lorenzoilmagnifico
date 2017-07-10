package it.polimi.ingsw.ui.cli;

import java.util.ArrayList;
import java.util.List;


public class JoinRoomScreen extends BasicScreen{

    private ICallback callback;

    private List<String> cliMessages = new ArrayList<>();

    JoinRoomScreen(ICallback callback) {
        this.callback = callback;

        cliMessages.add("Joining first room available. Please wait...");
        printScreenTitle("JOIN ROOM");
        print(cliMessages);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        joinRoom();
    }

    private void joinRoom(){
        this.callback.joinRoom();
    }

    @FunctionalInterface
    public interface ICallback {
        void joinRoom();
    }
}
