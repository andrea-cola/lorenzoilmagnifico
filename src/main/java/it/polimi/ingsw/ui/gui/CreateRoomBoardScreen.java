package it.polimi.ingsw.ui.gui;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class CreateRoomBoardScreen extends Application{

    private final static int HBOX_SPACING = 30;
    private final static int VBOX_SPACING = 30;

    private int players;
    private static boolean finished = false;

    private CreateRoomBoardInterface callback;
    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage the primary stage for this application
     */
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Create Room");
        HBox hBox = new HBox(HBOX_SPACING);
        Label players = new Label("Number of Players :");
        TextField number = new TextField();

        Button create = new Button("CREATE ROOM");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(number.getText()!= null){
                }

            }
        });


        Button exit = new Button("EXIT");


        number.getText();
    }

    private void createNewRoom(int maxPlayer){
        this.callback.createRoom(maxPlayer);
    }

    public void setFinished(boolean flag){
        finished = flag;
    }
    public boolean getFinished(){
        return finished;
    }

    @FunctionalInterface
    interface CreateRoomBoardInterface{
       public void createRoom(int maxPlayer);
    }

}

