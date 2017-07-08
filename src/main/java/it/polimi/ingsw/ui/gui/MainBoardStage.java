package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.UserInterface;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.List;


/**
 * This is the Graphic User Interface main board class
 */
public class MainBoardStage extends JFXPanel{

    /**
     * Family member related values
     */
    private int redValue;
    private int whiteValue;
    private int blackValue;
    private int neutralValue;
    private Integer servants = new Integer(0);

    private CallbackInterface callback;
    private InformationCallback informationCallback;
    private Game game;
    private Player player;
    private UserInterface client;
    private boolean turn;
    private boolean usedMember;

    /**
     * Data related to the player
     */
    private int militaryPoints;
    private int victoryPoints;
    private int faithPoints;
    private String username;
    private int coinsValue;
    private int woodValue;
    private int stonesValue;
    private int servantValue;

    /**
     * Constants
     */
    private static final int VBOX_SPACING = 10;
    private static final int FAMILY_RADIUS = 20;
    private static final int INSETS = 20;

    /**
     * Main gui MainBoardStage objects
     */
    private Button personalBoardButton;
    private Button personalTileButton;
    private Button leaderCardsButton;
    private Parent leftPane;
    private Scene scene;
    private BorderPane rightPane;
    private HBox root;

    /**
     * Constructor for Main Board class
     */
    MainBoardStage(CallbackInterface callback, UserInterface client, InformationCallback informationCallback, boolean turn, boolean usedMember) {
        this.game = client.getGameModel();
        this.player = client.getPlayer();
        this.client = client;
        this.turn = turn;
        this.usedMember = usedMember;
        System.out.println(usedMember);

        this.callback = callback;
        this.informationCallback = informationCallback;

        this.redValue = game.getDices().getValues().get(FamilyMemberColor.ORANGE);
        this.blackValue = game.getDices().getValues().get(FamilyMemberColor.BLACK);
        this.whiteValue = game.getDices().getValues().get(FamilyMemberColor.WHITE);
        this.neutralValue = game.getDices().getValues().get(FamilyMemberColor.NEUTRAL);

        this.username = player.getUsername();
        this.militaryPoints = player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY);
        this.victoryPoints = player.getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY);
        this.faithPoints = player.getPersonalBoard().getValuables().getPoints().get(PointType.FAITH);

        this.coinsValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.COIN);
        this.woodValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.WOOD);
        this.stonesValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.STONE);
        this.servantValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT);


        root = new HBox();

        showLeftPane();

        showRightPane();

        scene = new Scene(root);

        this.setScene(scene);
    }

    /**
     * Create the left pane of the split pane
     * @return the left pane
     */
    public void showLeftPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/mainboard.fxml"));
            leftPane = (Parent) loader.load();
            root.getChildren().add(0, leftPane);
            LeftPaneController controller = loader.getController();
            controller.initData(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Create the right pane of the split pane
     * @return the right pane
     */
    public void showRightPane() {
        rightPane = new BorderPane();

        VBox vBox = new VBox(VBOX_SPACING);
        vBox.setAlignment(Pos.CENTER);

        personalBoardButton = new Button("PERSONAL BOARD");
        personalBoardButton.setAlignment(Pos.CENTER);
        personalBoardButton.setOnAction(event -> this.callback.showPersonalBoardStage(player));

        personalTileButton = new Button("PERSONAL TILE");
        personalTileButton.setAlignment(Pos.CENTER);
        personalTileButton.setOnAction(event -> this.callback.showPersonalTileBoardStage(player));

        leaderCardsButton = new Button("LEADER CARDS");
        leaderCardsButton.setAlignment(Pos.CENTER);
        if(turn)
            leaderCardsButton.setOnAction(event -> callback.showLeaderCards(player));

        Circle redMember = new Circle(FAMILY_RADIUS);
        redMember.setFill(Color.rgb(200, 110, 34));
        Label redLabel = new Label();
        StackPane redPane = new StackPane();
        redPane.getChildren().addAll(redMember, redLabel);

        Circle blackMember = new Circle(FAMILY_RADIUS);
        blackMember.setFill(Color.BLACK);
        Label blackLabel = new Label();
        StackPane blackPane = new StackPane();
        blackPane.getChildren().addAll(blackMember, blackLabel);

        Circle whiteMember = new Circle(FAMILY_RADIUS);
        whiteMember.setFill(Color.WHITE);
        Label whiteLabel = new Label();
        StackPane whitePane = new StackPane();
        whitePane.getChildren().addAll(whiteMember, whiteLabel);

        Circle neutralMember = new Circle(FAMILY_RADIUS);
        neutralMember.setFill(Color.GREY);
        Label neutralLabel = new Label();
        StackPane neutralPane = new StackPane();
        neutralPane.getChildren().addAll(neutralMember, neutralLabel);

        if(turn && !usedMember){
           if(!player.getPersonalBoard().getFamilyMembersUsed().contains(FamilyMemberColor.BLACK))
               manageSourceEvent(blackMember);
           if (!player.getPersonalBoard().getFamilyMembersUsed().contains(FamilyMemberColor.ORANGE))
               manageSourceEvent(redMember);
           if(!player.getPersonalBoard().getFamilyMembersUsed().contains(FamilyMemberColor.WHITE))
               manageSourceEvent(whiteMember);
           if(!player.getPersonalBoard().getFamilyMembersUsed().contains(FamilyMemberColor.NEUTRAL))
               manageSourceEvent(neutralMember);
        }

        redLabel.textProperty().bind(new SimpleIntegerProperty(redValue).asString());
        blackLabel.textProperty().bind(new SimpleIntegerProperty(blackValue).asString());
        whiteLabel.textProperty().bind(new SimpleIntegerProperty(whiteValue).asString());
        neutralLabel.textProperty().bind(new SimpleIntegerProperty(neutralValue).asString());

        HBox turnBox = new HBox();
        turnBox.setAlignment(Pos.CENTER);
        Label userTurn = new Label("Player = ");
        Label usernameLabel = new Label();
        usernameLabel.textProperty().bind(new SimpleStringProperty(username));
        turnBox.getChildren().addAll(userTurn, usernameLabel);

        GridPane pointsTable = new GridPane();
        pointsTable.setAlignment(Pos.CENTER);

        pointsTable.add(new Label("Military points: "), 0, 0);
        pointsTable.add(new Label(Integer.toString(militaryPoints)), 1, 0);

        pointsTable.add(new Label("Victory points: "), 0, 1);
        pointsTable.add(new Label(Integer.toString(victoryPoints)), 1, 1);

        pointsTable.add(new Label("Faith points: "), 0, 2);
        pointsTable.add(new Label(Integer.toString(faithPoints)), 1, 2);

        pointsTable.add(new Label("Coins: "),0, 3);
        pointsTable.add(new Label(new Integer(coinsValue).toString()), 1, 3);

        pointsTable.add(new Label("Woods: "), 0, 4);
        pointsTable.add(new Label(new Integer(woodValue).toString()), 1, 4);

        pointsTable.add(new Label("Stones: "), 0, 5);
        pointsTable.add(new Label(new Integer(stonesValue).toString()), 1, 5);

        pointsTable.add(new Label("Servants: "), 0, 6);
        pointsTable.add(new Label(new Integer(servantValue).toString()), 1, 6);

        pointsTable.add(new Label("Excommunicated Period: "), 0, 7);
        List<ExcommunicationCard> exCards = player.getPersonalBoard().getExcommunivationCards();
        StringBuilder cardsID = new StringBuilder();
        if (exCards.size()>0) {
            for(int i=0; i< exCards.size(); i++)
                cardsID.append(" " + exCards.get(i).getPeriod() +  ", ");
        }
        pointsTable.add(new Label(cardsID.toString()), 1 ,7);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        Separator separator3 = new Separator(Orientation.HORIZONTAL);

        Button plus = new Button("+");
        plus.setAlignment(Pos.CENTER);
        Label number = new Label("n° servants " +servants);
        number.setAlignment(Pos.CENTER);
        Label message = new Label("Not valid");
        message.setVisible(false);
        message.setAlignment(Pos.CENTER);
        Button minus = new Button("-");
        minus.setAlignment(Pos.CENTER);

        if(turn) {
            plus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (servantValue > servants) {
                        servants++;
                        message.setVisible(false);
                        number.setText("n° servants " + new Integer(servants).toString());
                        minus.setVisible(true);
                    } else {
                        plus.setVisible(false);
                    }
                }
            });

            minus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (servants > 0) {
                        servants--;
                        plus.setVisible(true);
                        number.setText("n° servants " + new Integer(servants).toString());
                    } else {
                        minus.setVisible(false);
                    }
                }
            });
        }

        VBox servantsBox = new VBox();
        servantsBox.setAlignment(Pos.CENTER);
        servantsBox.setSpacing(VBOX_SPACING);
        servantsBox.getChildren().addAll(plus, number, minus, message);

        Button endTurnButton = new Button("END TURN");
        if(turn) {
            endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    callback.notifyEndTurnStage();
                }
            });
        }
        vBox.getChildren().addAll(personalBoardButton, personalTileButton, leaderCardsButton, separator, redPane, blackPane, whitePane, neutralPane, separator1, turnBox, separator2, pointsTable, separator3, servantsBox, endTurnButton);
        vBox.setAlignment(Pos.CENTER);
        rightPane.setCenter(vBox);
        rightPane.setMargin(vBox, new Insets(INSETS));
        root.getChildren().add(1, rightPane);
        return;
    }
    /**
     * Method to manage the "Drag Detected" and "Drag Done" event
     * @param source node which triggers the action during the events
     */
    private void manageSourceEvent(Circle source) {
        source.setOnDragDetected(event -> {
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(source.toString());
            db.setContent(clipboardContent);
            event.consume();
            System.out.println("onDragDetected");
        });
        source.setOnDragDone(event -> {
            System.out.println("onDragDone");
            if (event.getTransferMode() == TransferMode.MOVE) {
            }
            event.consume();
        });
    }

    Integer getServants(){
        return servants;
    }

    Game getGame(){
        return this.game;
    }

    Player getPlayer(){
        return this.player;
    }

    CallbackInterface getCallback(){
        return this.callback;
    }

    UserInterface getClient(){
        return this.client;
    }

    Boolean getTurn(){
        return this.turn;
    }

    InformationCallback getInformationCallback(){
        return this.informationCallback;
    }


    interface CallbackInterface{

        void showPersonalBoardStage(Player player);

        void showPersonalTileBoardStage(Player player);

        void showLeaderCards(Player player);

        void showGameException(String message);

        void notifyEndTurnStage();

        void activeLeaderCard(String leaderName, int servants);

        void discardLeader(String leaderName);

        void updateMainBoard();

        void setUsedMember(boolean flag);

        boolean getUsedMember();

    }
}
