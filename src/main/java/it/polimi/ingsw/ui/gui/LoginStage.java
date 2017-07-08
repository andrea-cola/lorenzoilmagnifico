package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.exceptions.LoginException;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
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

import java.awt.*;

/**
 * This is the Graphic User Interface login board
 */
public class LoginStage extends JFXPanel {


    private CallbackInterface callback;
    /**
     * Data player
     */
    private String username;
    private String password;
    private Label message;

    /**
     * Constants
     */
    private final static int HBOX_SPACING = 10;
    private final static int VBOX_SPACING = 10;
    private final static int GROUP_HEIGHT = 300;
    private final static int GROUP_WIDTH = 300;
    private final static int INSETS = 20;
    private static final int FRAME_HEIGHT = 750;
    private static final int FRAME_WIDTH = 1000;
    /**
     * Constructor
     * @param callback
     */
    LoginStage(CallbackInterface callback) {
        this.callback = callback;

        Label login = new Label("LOGIN");
        login.setAlignment(Pos.CENTER);
        message = new Label("Your data are not valid");
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
                this.hide();
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
                setVisible(false);
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
        VBox vBox = new VBox(VBOX_SPACING);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(login, grid, message, hBox);
        BorderPane root = new BorderPane();
        root.setCenter(vBox);
        root.setMargin(vBox, new Insets(INSETS));
        root.autosize();
        Scene scene = new Scene(root);
        this.setPreferredSize( new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setScene(scene);
    }


    private void login() {
        System.out.println(username + " " + password );
        try {
            this.callback.loginPlayer(username, password, false);
        } catch (LoginException e) {
            message.setText("Your username or password are not correct");
            message.setVisible(true);
        }
    }

    private void signIn() {
        System.out.println(username + " " + password );
        try {
            this.callback.loginPlayer(username, password, true);
        } catch (LoginException e) {
            message.setText("Your username or password are not correct");
            message.setVisible(true);
        }
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
        void loginPlayer(String username, String password, boolean flag) throws LoginException;
    }
}
