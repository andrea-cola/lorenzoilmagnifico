package it.polimi.ingsw.ui.cli;

public class JoinRoomScreen extends BasicScreen{

    private ICallback callback;

    JoinRoomScreen(ScreenInterface screenInterface, ICallback callback) {
        super(screenInterface);

        System.out.println("\n\n[JOIN ROOM]");
        this.callback = callback;
        addPrintCommand("join-room", arguments->joinRoom());
        printHelps();
        readCommand();
    }

    private void joinRoom(){
        this.callback.joinRoom();
    }

    private void printHelps(){
        System.out.println("Help: join-room");
    }

    @FunctionalInterface
    public interface ICallback {
        void joinRoom();
    }
}
