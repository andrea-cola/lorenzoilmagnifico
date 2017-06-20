package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

/**
 * This is the Graphic User Interface login board
 */
public class LoginBoardScreen extends Application {


    private LoginBoardInterface callback;
    /**
     * Constants
     */
    private final static int HBOX_SPACING = 10;
    private final static int VBOX_SPACING = 10;


    /**
     * Data player
     */
    private String username;
    private String password;

    /**
     * Constructor
     * @param callback
     */
    public LoginBoardScreen(LoginBoardInterface callback) {
        this.callback = callback;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LoginBoardScreen");

        Label login = new Label("LOGIN");
        login.setAlignment(Pos.CENTER);
        final Label message = new Label("");
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
            if (userField.getText() != null && passwordField.getText() != null){
                username = userField.getText();
                password = passwordField.getText();
                login(username, password, true);
            }else{
                message.setText("Your data are invalid");
            }
        });
        Button signInButton = new Button("SIGN IN");
        signInButton.setAlignment(Pos.CENTER);
        signInButton.setOnAction(event ->{
            if (userField.getText() != null && passwordField.getText() != null){
                username = userField.getText();
                password = passwordField.getText();
                login(username, password, false);
                message.setText("Checking your data");
            }else{
                message.setText("Your data are invalid");
            }
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(loginButton, signInButton);
        hBox.autosize();
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(login, grid, message, hBox);
        vBox.autosize();

        Group root = new Group();
        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void login(String username, String password, boolean flag) {
        this.callback.loginPlayer(username, password, flag);
    }

    /**
     * This interface represents the login main function
     */
    @FunctionalInterface
    interface LoginBoardInterface {
        /**
         * It pass the parameters for the main game login function
         * @param username to use
         * @param password set by the player
         */
        void loginPlayer(String username, String password, boolean flag);
    }
}
