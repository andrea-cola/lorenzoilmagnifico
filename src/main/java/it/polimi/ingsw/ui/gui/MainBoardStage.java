package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.*;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import jdk.nashorn.internal.codegen.CompilerConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * This is the Graphic User Interface main board class
 */
public class MainBoardStage extends JFXPanel implements MainBoardSettings {
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

    /**
     * Data related to the player
     */
    private int militaryPoints;
    private int victoryPoints;
    private int faithPoints;
    private String username;

    /**
     * Constants
     */
    private static final int STAGE_WIDTH = 800;
    private static final int STAGE_HEIGHT = 1000;
    private static final int BACK_WIDTH = 425;
    private static final int BACK_HEIGHT = 700;
    private static final int GRID_TOWER_X = 20;
    private static final int GRID_TOWER_Y = 20;
    private static final int GRID_TOWER_HGAP = 15;
    private static final int GRID_TOWER_VGAP = 30;
    private static final int GRID_ACTION_X = 20;
    private static final int GRID_ACTION_Y = 580;
    private static final int GRID_MARKET_HGAP = 20;
    private static final int GRID_MARKET_VGAP = 20;
    private static final int HBOX_SPACING = 110;
    private static final int VBOX_SPACING = 10;
    private static final int CIRCLE_RADIUS = 10;
    private static final int FAMILY_RADIUS = 20;
    private static final int IMAGE_HEIGHT = 70;
    private static final int IMAGE_WIDTH = 50;
    private static final int INSETS = 20;


    /**
     * Main gui MainBoardStage objects
     */
    private BackgroundImage background;
    private Button personalBoardButton;
    private Button personalTileButton;
    private Button leaderCardsButton;
    private AnchorPane leftPane;
    private GridPane gridTower;
    private GridPane gridAction;
    private GridPane gridMarket;
    private Scene scene;
    private BorderPane rightPane;

    private final Lock lock = new ReentrantLock();


    /**
     * Constructor for Main Board class
     */
    MainBoardStage(CallbackInterface callback, Player player, Game game, InformationCallback informationCallback) {
        this.game = game;
        this.player = player;
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

        gridTower = new GridPane();
        gridAction = new GridPane();
        gridMarket = new GridPane();

        showExtraHarvest();
        showExtraProduction();
        showExtraMarket();

        SplitPane splitPane = new SplitPane();
        leftPane = createLeftPane();
        rightPane = createRightPane();
        Group group = new Group();
        group.getChildren().add(leftPane);
        splitPane.getItems().addAll(group, rightPane);

        scene = new Scene(splitPane);
        this.setScene(scene);
    }

    /**
     * Create the left pane of the split pane
     * @return the left pane
     */
    public AnchorPane createLeftPane() {
        leftPane = new AnchorPane();

        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_HEIGHT, false, false, true, true);
        Image image2 = new Image("/images/MainBoardStageCover.jpg");
        background = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
        leftPane.setBackground(new Background(background));

        gridTower.relocate(GRID_TOWER_X, GRID_TOWER_Y);
        gridTower.setVgap(GRID_TOWER_VGAP);
        gridTower.setHgap(GRID_TOWER_HGAP);
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                if (i % 2 == 0) {
                    setDevelopmentCardInTowerCell(i / 2, j);
                } else {
                    if (game.getMainBoard().getTower(i / 2).getTowerCell(j).getPlayerNicknameInTheCell() == null) {
                        ImageView card = (ImageView) getNodeInGrid(i - 1, j);
                        Circle circle = new Circle(10, Color.AQUA);
                        gridTower.add(circle, i, j);
                        int column = i;
                        int row = j;
                        manageTargetEvent(circle);
                        circle.setOnDragDropped((DragEvent event) -> {
                            String[] name;
                            System.out.println("onDragDropped");
                            Dragboard db = event.getDragboard();
                            Node node = event.getPickResult().getIntersectedNode();
                            int x = GridPane.getColumnIndex(node);
                            if (db.hasString()) {
                                boolean success = false;
                                circle.setDisable(true);
                                name = db.getString().split("fill=0");
                                Color color = stringToColor(name[1]);
                                circle.setFill(color);
                                card.setVisible(false);
                                circle.setDisable(true);
                                try {
                                    System.out.println("my servants " + servants +"\n");
                                    System.out.println(getMemberColor(color).toString());
                                    this.game.pickupDevelopmentCardFromTower(player, getMemberColor(color), servants, column, row, informationCallback);
                                    System.out.println("CIAONE");
                                } catch (GameException e) {
                                    this.callback.showGameException();
                                }
                                this.callback.notifyEndTurnStage();
                                success = true;
                                event.setDropCompleted(success);
                                event.consume();
                            }
                        });
                    }
                }
            }
        }

        /*
        for (int i = 0; i < 3; i++) {
            setExcommunicationCard(i, 5);
        }
        */

        Circle council = new Circle(CIRCLE_RADIUS);
        manageTargetEvent(council);
        gridTower.add(council, 5, 4);
        council.setOnDragDropped(event -> {
            String[] name;
            System.out.println("onDragDropped");
            Dragboard db = event.getDragboard();
            Node node = event.getPickResult().getIntersectedNode();
            int x = GridPane.getColumnIndex(node);
            if (db.hasString()) {
                boolean success = false;
                council.setDisable(true);
                name = db.getString().split("fill=0");
                Color color = stringToColor(name[1]);
                council.setFill(color);
                try {
                    game.placeFamilyMemberInsideCouncilPalace(player, getMemberColor(color), servants , null);
                } catch (GameException e) {
                    callback.showGameException();
                }
                callback.notifyEndTurnStage();
                success = true;
                event.setDropCompleted(success);
                event.consume();
            }
        });

        gridAction.setHgap(GRID_TOWER_HGAP);
        gridAction.setVgap(GRID_TOWER_VGAP);

        if(game.getMainBoard().getProduction().isEmpty()) {
            Circle circleProduction = new Circle(CIRCLE_RADIUS);
            manageTargetEvent(circleProduction);
            gridAction.add(circleProduction, 0, 0);
            circleProduction.setOnDragDropped(event -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                int x = GridPane.getColumnIndex(node);
                if (db.hasString()) {
                    boolean success = false;
                    circleProduction.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleProduction.setFill(color);
                    try {
                        game.placeFamilyMemberInsideHarvestSimpleSpace(player, getMemberColor(color), servants, null);
                    } catch (GameException e) {
                        callback.showGameException();
                    }
                    circleProduction.setDisable(true);
                    callback.notifyEndTurnStage();
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }

        if(game.getMainBoard().getHarvest().isEmpty()) {
            Circle circleHarvest = new Circle(CIRCLE_RADIUS);
            manageTargetEvent(circleHarvest);
            gridAction.add(circleHarvest, 0, 1);
            circleHarvest.setOnDragDropped(event -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                int x = GridPane.getColumnIndex(node);
                if (db.hasString()) {
                    boolean success = false;
                    circleHarvest.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleHarvest.setFill(color);
                    try {
                        game.placeFamilyMemberInsideHarvestSimpleSpace(player, getMemberColor(color), servants, null);
                    } catch (GameException e) {
                        callback.showGameException();
                    }
                    circleHarvest.setDisable(true);
                    callback.notifyEndTurnStage();
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }

        gridMarket.setHgap(GRID_MARKET_HGAP);
        gridMarket.setVgap(GRID_MARKET_VGAP);

        for (int i = 0; i <2 ; i++) {
            int j = i;
            if(game.getMainBoard().getMarket().getMarketCell(i).isEmpty()) {
                Circle circleMarket = new Circle(CIRCLE_RADIUS);
                manageTargetEvent(circleMarket);
                gridMarket.add(circleMarket, 3 + i, 0);
                circleMarket.setOnDragDropped(event -> {
                    String[] name;
                    System.out.println("onDragDropped");
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    int x = GridPane.getColumnIndex(node);
                    if (db.hasString()) {
                        boolean success = false;
                        circleMarket.setDisable(true);
                        name = db.getString().split("fill=0");
                        Color color = stringToColor(name[1]);
                        circleMarket.setFill(color);
                        try {
                            game.placeFamilyMemberInsideMarket(player, getMemberColor(color), servants, j, null);
                        } catch (GameException e) {
                            callback.showGameException();
                        }
                        circleMarket.setDisable(true);
                        callback.notifyEndTurnStage();
                        success = true;
                        event.setDropCompleted(success);
                        event.consume();
                    }
                });
            }
        }

        HBox hBox = new HBox(HBOX_SPACING);
        hBox.getChildren().addAll(gridAction, gridMarket);
        hBox.relocate(GRID_ACTION_X, GRID_ACTION_Y);
        leftPane.setPrefSize(BACK_WIDTH, BACK_HEIGHT);
        leftPane.getChildren().addAll(gridTower, hBox);
        return leftPane;
    }

    /**
     * Create the right pane of the split pane
     * @return the right pane
     */
    public BorderPane createRightPane() {
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
        leaderCardsButton.setOnAction(event -> callback.showLeaderCards(player));

        Circle redMember = new Circle(FAMILY_RADIUS);
        redMember.setFill(Color.RED);
        Label redLabel = new Label();
        StackPane redPane = new StackPane();
        manageSourceEvent(redMember);
        redPane.getChildren().addAll(redMember, redLabel);

        Circle blackMember = new Circle(FAMILY_RADIUS);
        blackMember.setFill(Color.BLACK);
        Label blackLabel = new Label();
        StackPane blackPane = new StackPane();
        manageSourceEvent(blackMember);
        blackPane.getChildren().addAll(blackMember, blackLabel);

        Circle whiteMember = new Circle(FAMILY_RADIUS);
        whiteMember.setFill(Color.WHITE);
        Label whiteLabel = new Label();
        StackPane whitePane = new StackPane();
        manageSourceEvent(whiteMember);
        whitePane.getChildren().addAll(whiteMember, whiteLabel);

        Circle neutralMember = new Circle(FAMILY_RADIUS);
        neutralMember.setFill(Color.GREY);
        Label neutralLabel = new Label();
        StackPane neutralPane = new StackPane();
        manageSourceEvent(neutralMember);
        neutralPane.getChildren().addAll(neutralMember, neutralLabel);

        redLabel.textProperty().bind(new SimpleIntegerProperty(redValue).asString());
        blackLabel.textProperty().bind(new SimpleIntegerProperty(blackValue).asString());
        whiteLabel.textProperty().bind(new SimpleIntegerProperty(whiteValue).asString());
        neutralLabel.textProperty().bind(new SimpleIntegerProperty(neutralValue).asString());

        HBox turnBox = new HBox();
        Label turn = new Label("Turn = ");
        Label usernameLabel = new Label();
        usernameLabel.setAlignment(Pos.CENTER);
        usernameLabel.textProperty().bind(new SimpleStringProperty(username));
        turnBox.getChildren().addAll(turn, usernameLabel);

        GridPane pointsTable = new GridPane();
        Label militaryLabel = new Label("Military points: ");
        militaryLabel.setAlignment(Pos.CENTER);
        pointsTable.add(militaryLabel, 0, 0);
        pointsTable.add(new Label(Integer.toString(militaryPoints)), 1, 0);
        Label victoryLabel = new Label("Victory points: ");
        victoryLabel.setAlignment(Pos.CENTER);
        pointsTable.add(new Label(Integer.toString(victoryPoints)), 1, 1);
        pointsTable.add(victoryLabel, 0, 1);
        Label faithLabel = new Label("Faith points: ");
        faithLabel.setAlignment(Pos.CENTER);
        pointsTable.add(faithLabel, 0, 2);
        pointsTable.add(new Label(Integer.toString(faithPoints)), 1, 2);

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

        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                servants++;
                message.setVisible(false);
                number.setText(new Integer(servants).toString());
                minus.setVisible(true);
            }
        });

        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (servants > 0) {
                    servants--;
                    number.setText(new Integer(servants).toString());
                } else {
                    message.setVisible(true);
                    minus.setVisible(false);
                }
            }
        });

        VBox servantsBox = new VBox();
        servantsBox.setAlignment(Pos.CENTER);
        servantsBox.setSpacing(VBOX_SPACING);
        servantsBox.getChildren().addAll(plus, number, minus, message);

        vBox.getChildren().addAll(personalBoardButton, personalTileButton, leaderCardsButton, separator, redPane, blackPane, whitePane, neutralPane, separator1, turnBox, separator2, pointsTable, separator3, servantsBox);
        rightPane.setCenter(vBox);
        rightPane.setMargin(vBox, new Insets(INSETS));
        return rightPane;
    }

    /**
     * Convert an hexadecimal string into a Color
     * @param hexColor string
     * @return related color
     */
    private static Color stringToColor(String hexColor) {
        return Color.rgb(Integer.valueOf(hexColor.substring(1, 3), 16),
                Integer.valueOf(hexColor.substring(3, 5), 16),
                Integer.valueOf(hexColor.substring(5, 7), 16));
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

    /**
     * Method to manage the "Drag Over", "Drag Entered", "Drag Exited", "Drag Dropped" event
     * @param target node which is triggered by the event and does actions
     */
    private void manageTargetEvent(Circle target) {
        target.setOnDragOver(event -> {
            /* data is dragged over the target */
            System.out.println("onDragOver");
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != target &&
                    event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        target.setOnDragEntered(event -> {
            /* the drag-and-drop gesture entered the target */
            System.out.println("onDragEntered");
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                target.setScaleX(target.getScaleX() * 1.3);
                target.setScaleY(target.getScaleY() * 1.3);
            }
            event.consume();
        });
        target.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            target.setScaleX(target.getScaleX() / 1.3);
            target.setScaleY(target.getScaleY() / 1.3);
            event.consume();
        });
    }

    private FamilyMemberColor getMemberColor(Color color){
        if (Color.RED.equals(color))
            return FamilyMemberColor.ORANGE;
        else if (Color.BLACK.equals(color))
            return FamilyMemberColor.BLACK;
        else if (Color.GRAY.equals(color))
            return FamilyMemberColor.NEUTRAL;
        else if (Color.WHITE.equals(color))
            return FamilyMemberColor.WHITE;
        return null;
    }

    /**
     * Get a node in the gridTower object
     * @param row index
     * @param column index
     * @return Node
     */
    private Node getNodeInGrid(int column, int row) {
        Node result = null;
        ObservableList<Node> childrens = gridTower.getChildren();
        for (Node node : childrens) {
            if (gridTower.getRowIndex(node) == row && gridTower.getColumnIndex(node) == column) {
                result = node;
            }
        }
        return result;
    }

    @Override
    public void showExtraProduction() {
        System.out.println("setting production...");
        if (game.getPlayersMap().size() > 2) {
            Circle circleProductionExtended = new Circle(CIRCLE_RADIUS);
            manageTargetEvent(circleProductionExtended);
            gridAction.add(circleProductionExtended, 1, 0);
            circleProductionExtended.setOnDragDropped((DragEvent event) -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                int x = GridPane.getColumnIndex(node);
                if (db.hasString()) {
                    boolean success = false;
                    circleProductionExtended.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleProductionExtended.setFill(color);
                    try {
                        game.placeFamilyMemberInsideProductionExtendedSpace(player, getMemberColor(color), servants, null);
                    } catch (GameException e) {
                        callback.showGameException();
                    }
                    circleProductionExtended.setDisable(true);
                    callback.notifyEndTurnStage();
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }
    }

    @Override
    public void showExtraHarvest() {
        System.out.println("setting harvest...");
        if (game.getPlayersMap().size() > 2) {
            Circle circleHarvestExtended = new Circle(CIRCLE_RADIUS);
            manageTargetEvent(circleHarvestExtended);
            gridAction.add(circleHarvestExtended, 1, 1);
            circleHarvestExtended.setOnDragDropped((DragEvent event) -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                int x = GridPane.getColumnIndex(node);
                if (db.hasString()) {
                    boolean success = false;
                    circleHarvestExtended.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleHarvestExtended.setFill(color);
                    try {
                        game.placeFamilyMemberInsideHarvestExtendedSpace(player, getMemberColor(color), servants, null);
                    } catch (GameException e) {
                        callback.showGameException();
                    }
                    circleHarvestExtended.setDisable(true);
                    callback.notifyEndTurnStage();
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }
    }

    @Override
    public void showExtraMarket() {
        System.out.println("setting market...");
        if (game.getPlayersMap().size() > 3) {
            for(int i=0; i<2; i++) {
                int j = i+ 2;
                if(game.getMainBoard().getMarket().getMarketCell(j).isEmpty()) {
                    Circle circleMarketExtended = new Circle(CIRCLE_RADIUS);
                    manageTargetEvent(circleMarketExtended);
                    gridMarket.add(circleMarketExtended, 5 + i, 0);
                    circleMarketExtended.setOnDragDropped((DragEvent event) -> {
                        String[] name;
                        System.out.println("onDragDropped");
                        Dragboard db = event.getDragboard();
                        Node node = event.getPickResult().getIntersectedNode();
                        int x = GridPane.getColumnIndex(node);
                        if (db.hasString()) {
                            boolean success = false;
                            circleMarketExtended.setDisable(true);
                            name = db.getString().split("fill=0");
                            Color color = stringToColor(name[1]);
                            circleMarketExtended.setFill(color);
                            try {
                                game.placeFamilyMemberInsideMarket(player, getMemberColor(color), servants, j, null);
                            } catch (GameException e) {
                                callback.showGameException();
                            }
                            circleMarketExtended.setDisable(true);
                            callback.notifyEndTurnStage();
                            success = true;
                            event.setDropCompleted(success);
                            event.consume();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void setDevelopmentCardInTowerCell(int tower, int towerCell) {
        System.out.println("setting tower cell...");
        if(game.getMainBoard().getTower(tower).getTowerCell(towerCell).getPlayerNicknameInTheCell() == null) {
            DevelopmentCard devCard = game.getMainBoard().getTower(tower).getTowerCell(towerCell).getDevelopmentCard();
            StringBuilder path = new StringBuilder();
            path.append("images/developmentCard/devcards_f_en_c_");
            path.append(devCard.getId());
            path.append(".png");
            ImageView image = new ImageView(new Image(path.toString()));
            image.setFitHeight(IMAGE_HEIGHT);
            image.setFitWidth(IMAGE_WIDTH);
            image.autosize();
            gridTower.add(image, tower * 2, towerCell);
        }else{
            System.out.println("Errore");
        }
    }

    @Override
    public void setExcommunicationCard(int period, int row) {
        System.out.println("setting vatican");
        StringBuilder path = new StringBuilder();
        path.append("images/excommunicationCard/excomm_");
        path.append(period + 1);
        path.append("_");
        path.append(game.getMainBoard().getVatican().getExcommunicationCard(period).getCardID());
        path.append(".png");
        ImageView image = new ImageView((new Image(path.toString())));
        image.setFitHeight(IMAGE_HEIGHT);
        image.setFitWidth(IMAGE_WIDTH);
        image.autosize();
        gridTower.add(image, period+1, row);
    }

    interface CallbackInterface{

        void showPersonalBoardStage(Player player);

        void showPersonalTileBoardStage(Player player);

        void showLeaderCards(Player player);

        void showGameException();

        void showChooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege, CallbackInterface callback);

        void notifyEndTurnStage();

        void setCouncilPrivileges(String reason, ArrayList<Privilege> privileges);

        ArrayList<Privilege> getCouncilPrivileges();

        void activeLeaderCard(String leaderName);

        void discardLeader(String leaderName);
    }
}
