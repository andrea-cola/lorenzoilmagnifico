package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

/**
 * This is the Graphic User Interface login board
 */
public class LoginBoard extends Application {

    private LoginBoardInterface callback;

    private static final CountDownLatch latch = new CountDownLatch(1);

    private static LoginBoard loginBoard = null;

    private TextField username;

    private PasswordField password;

    /**
     * It is called by the graphic user interface to wait and then continue its functions
     * after the end of start function
     *
     * @return the network board
     */
    public static LoginBoard waitFor() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return loginBoard;
    }

    /**
     * @param callback
     */
    public LoginBoard(LoginBoardInterface callback) {
        this.callback = callback;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LoginBoard");
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        Label login = new Label("LOGIN");
        login.setAlignment(Pos.CENTER);
        final Label message = new Label("");
        message.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Label userLabel = new Label("USERNAME");
        userLabel.setAlignment(Pos.CENTER);
        grid.add(userLabel, 0, 0);
        username = new TextField();
        username.setAlignment(Pos.CENTER);
        grid.add(username, 1, 0);
        Label passLabel = new Label("PASSWORD");
        passLabel.setAlignment(Pos.CENTER);
        grid.add(passLabel, 0, 1);
        password = new PasswordField();
        password.setAlignment(Pos.CENTER);
        password.setOnAction(event -> {
            if (passLabel.getText().isEmpty()) {
                message.setText("Your password is incorrect");
            } else {
                message.setText("Your password is valid");
            }
        });
        grid.add(password, 1, 1);
        Button enter = new Button("ENTER");
        enter.setAlignment(Pos.CENTER);
        enter.setOnAction(event -> this.login());
        vBox.getChildren().addAll(login, grid, message, enter);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void login() {
        String username = this.username.getText();
        String password = this.password.getText();
        this.callback.loginPlayer(username, password);
    }

    /**
     * This interface represents the login main function
     */
    @FunctionalInterface
    interface LoginBoardInterface {
        /**
         * It pass the parameters for the main game login function
         *
         * @param username to use
         * @param password set by the player
         */
        void loginPlayer(String username, String password);
    }
}
