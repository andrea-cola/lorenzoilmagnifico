package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is the Graphic User Interface login board
 */
public class LoginStage extends Application {


    private CallbackInterface callback;
    /**
     * Data player
     */
    private String username;
    private String password;

    /**
     * Constants
     */
    private final static int HBOX_SPACING = 10;
    private final static int VBOX_SPACING = 10;
    private final static int GROUP_HEIGHT = 300;
    private final static int GROUP_WIDTH = 300;
    private final static int INSETS = 20;


    /**
     * Constructor
     * @param callback
     */
    LoginStage(CallbackInterface callback) {
        this.callback = callback;
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("LoginStage");

        Label login = new Label("LOGIN");
        login.setAlignment(Pos.CENTER);
        Label message = new Label("Your data are not valid");
        message.setVisible(false);
        message.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Label userLabel = new Label("USERNAME");
        userLabel.setAlignment(Pos.CENTER);
        grid.add(userLabel, 0, 0);
        TextField userField = new TextField();
        userField.setAlignment(Pos.CENTER);
        grid.add(userField, 1, 0);
        Label passLabel = new Label("PASSWORD");
        passLabel.setAlignment(Pos.CENTER);
        grid.add(passLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        passwordField.setAlignment(Pos.CENTER);

        grid.add(passwordField, 1, 1);


        Button loginButton = new Button("LOGIN");
        loginButton.setAlignment(Pos.CENTER);

        loginButton.setOnAction(event -> {
            message.setVisible(true);
            if(userField.getText() != "" || passwordField.getText() != "" ) {
                username = userField.getText();
                password = passwordField.getText();
                login();
                primaryStage.close();
            }else{
                message.setVisible(true);
            }
        });

        Button signInButton = new Button("SIGN IN");
        signInButton.setAlignment(Pos.CENTER);
        signInButton.setOnAction((ActionEvent event) -> {
            if(!userField.getText().equals("") && passwordField.getText() != "" ) {
                message.setVisible(true);
                username = userField.getText();
                password = passwordField.getText();
                signIn();
                primaryStage.close();
            }else {
                message.setVisible(true);
            }
        });

        Button clear = new Button("CLEAR");
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                userField.clear();
                passwordField.clear();
                message.setVisible(true);
            }
        });

        HBox hBox = new HBox(HBOX_SPACING);
        hBox.getChildren().addAll(loginButton, signInButton, clear);
        hBox.setAlignment(Pos.CENTER);
        hBox.autosize();
        VBox vBox = new VBox(VBOX_SPACING);
        vBox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setCenter(grid);
        pane.setMargin(grid, new Insets(INSETS));
        pane.autosize();
        vBox.getChildren().addAll(login, pane, message, hBox);
        vBox.autosize();
        Group root = new Group();
        root.prefHeight(GROUP_HEIGHT);
        root.prefWidth(GROUP_WIDTH);
        root.getChildren().addAll(vBox);
        root.autosize();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void login() {
        System.out.println(username + " " + password +"\n");
        this.callback.loginPlayer(username, password, false);
    }

    private void signIn(){
        System.out.println(username + " " + password +"\n");
        this.callback.loginPlayer(username, password, true);

    }

    /**
     * This interface represents the login main function
     */
    @FunctionalInterface
    interface CallbackInterface {
        /**
         * It pass the parameters for the main game login function
         * @param username to use
         * @param password set by the player
         */
        void loginPlayer(String username, String password, boolean flag);
    }
}
