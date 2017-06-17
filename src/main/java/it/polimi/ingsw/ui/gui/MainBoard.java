package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Insets;
import javafx.event.EventHandler;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This the main board where
 */
public class MainBoard extends Application {

    private int redValue;
    private int whiteValue;
    private int blackValue;
    private final Integer neutralValue= 0;

    private int militaryPoints;
    private int victoryPoints;
    private int faithPoints;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;
    private static final int BACK_WIDTH = 200;
    private static final int BACK_HEIGHT = 250;
    private static final int GRID_X = 120;
    private static final int GRID_Y = 100;
    private static final int GRID_H_GAP = 80;
    private static final int GRID_V_GAP = 80;
    private static final int GRID_ACTION_X = 70;
    private static final int GRID_ACTION_Y = 650;
    private static final int GRID_MARKET_H_GAP = 20;
    private static final int GRID_MARKET_V_GAP = 30;
    private static final int HBOX_SPACING = 80;

    private VBox rightPane;
    private BackgroundImage background;
    private Button myButton;
    private AnchorPane leftPane;


    private String username;

    /**
     * The main entry point for all JavaFX applications
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MainBoard");


        SplitPane splitPane = new SplitPane();
        leftPane = createLeftPane();
        rightPane= createRightPane();
        splitPane.getItems().addAll(leftPane, rightPane);

        Scene scene = new Scene(splitPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setWidth(WIDTH);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public AnchorPane createLeftPane(){
        AnchorPane leftPane = new AnchorPane();
        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_HEIGHT, false,false, true, false);
        Image image1 = new Image("gameboard.jpeg");
        Image image2 = new Image("gameboard1.jpg");
        background = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        leftPane.setBackground(new Background(background));
        GridPane gridTower = new GridPane();
        gridTower.relocate(GRID_X, GRID_Y);
        gridTower.setVgap(GRID_V_GAP);
        gridTower.setHgap(GRID_H_GAP);
        for (int i = 0; i <= 7 ; i++) {
            for (int j = 0; j <= 3; j++) {
                if(i%2==0){
                    Rectangle rectangle = new Rectangle(50, 70, Color.AZURE);
                    gridTower.add(rectangle, i, j);
                }else{
                    Circle circle = new Circle(10, Color.AQUA);
                    gridTower.add(circle, i, j);
                }
            }
        }

        Circle council = new Circle(10);
        council.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");
                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != council &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        council.setOnDragEntered(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != council &&
                        event.getDragboard().hasString()) {
                    council.setScaleX(council.getScaleX()*1.3);
                    council.setScaleY(council.getScaleY()*1.3);
                }
                event.consume();
            }
        });

        council.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                council.setScaleX(council.getScaleX()/1.3);
                council.setScaleY(council.getScaleY()/1.3);
                event.consume();
            }
        });

        council.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                String name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if(db.hasString()){
                    council.setDisable(true);
                    name= db.getString();
                    System.out.println(name);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }

        });
        gridTower.add(council, 5, 4);

        GridPane gridAction = new GridPane();
        gridAction.setHgap(GRID_H_GAP);
        gridAction.setVgap(GRID_V_GAP);
        CheckBox checkProduction = new CheckBox();
        gridAction.add(checkProduction, 0, 0);
        CheckBox checkProductionExtended = new CheckBox();
        gridAction.add(checkProductionExtended, 1, 0);
        CheckBox checkHarvest = new CheckBox();
        gridAction.add(checkHarvest, 0, 1);
        CheckBox checkHarvestExtended = new CheckBox();
        gridAction.add(checkHarvestExtended, 1, 1);


        CheckBox checkMarket1 = new CheckBox();
        CheckBox checkMarket2 = new CheckBox();
        CheckBox checkMarketExtended1 = new CheckBox();
        CheckBox checkMarketExtended2 = new CheckBox();
        GridPane gridMarket = new GridPane();
        gridMarket.add(checkMarket1, 3, 0);
        gridMarket.add(checkMarket2, 4, 0);
        gridMarket.add(checkMarketExtended1, 5, 0);
        gridMarket.add(checkMarketExtended2, 6, 1);
        gridMarket.setHgap(GRID_MARKET_H_GAP);
        gridMarket.setVgap(GRID_MARKET_V_GAP);

        HBox hBox = new HBox(HBOX_SPACING);
        hBox.getChildren().addAll(gridAction, gridMarket);
        leftPane.getChildren().addAll(gridTower, hBox);
        hBox.relocate(GRID_ACTION_X, GRID_ACTION_Y);
        return leftPane;
    }


    public VBox createRightPane(){
        rightPane= new VBox(10);
        myButton = new Button("View My Board");
        myButton.setAlignment(Pos.CENTER);

        //myButton.addEventHandler();

        Circle redMember= new Circle(20);
        redMember.setFill(Color.RED);
        Label redLabel = new Label();
        StackPane redPane = new StackPane();

        redMember.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = redMember.startDragAndDrop(TransferMode.ANY);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(redMember.getId());
                db.setContent(clipboardContent);
                event.consume();
                System.out.println("onDragDetected");
            }
        });

        redMember.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("onDragDone");
                if (event.getTransferMode() == TransferMode.MOVE) {
                    redMember.setDisable(true);
                }
                event.consume();
            }
        });

        redPane.getChildren().addAll(redMember, redLabel);


        Circle blackMember= new Circle(20);
        blackMember.setFill(Color.BLACK);
        Label blackLabel = new Label();
        StackPane blackPane = new StackPane();
        blackPane.getChildren().addAll(blackMember, blackLabel);

        Circle whiteMember = new Circle(20);
        whiteMember.setFill(Color.WHITE);
        Label whiteLabel = new Label();
        StackPane whitePane = new StackPane();
        whitePane.getChildren().addAll(whiteMember, whiteLabel);

        Circle neutralMember = new Circle(20);
        neutralMember.setFill(Color.GREY);
        Label neutralLabel = new Label();
        StackPane neutralPane = new StackPane();
        neutralPane.getChildren().addAll(neutralMember, neutralLabel);

        redLabel.textProperty().bind( new SimpleIntegerProperty(redValue).asString());
        blackLabel.textProperty().bind( new SimpleIntegerProperty(blackValue).asString());
        whiteLabel.textProperty().bind(new SimpleIntegerProperty(whiteValue).asString());
        neutralLabel.textProperty().bind(new SimpleIntegerProperty(neutralValue).asString());


        HBox turnBox = new HBox();
        turnBox.setAlignment(Pos.CENTER);
        Label turn = new Label("Turn = ");
        Label usernameLabel = new Label();
        usernameLabel.textProperty().bind(new SimpleStringProperty(username));
        turnBox.getChildren().addAll(turn, usernameLabel);

        GridPane pointsTable = new GridPane();
        pointsTable.setAlignment(Pos.CENTER);
        Label militaryLabel = new Label("Military points: ");
        pointsTable.add(militaryLabel, 0, 0);
        pointsTable.add(new Label(Integer.toString(militaryPoints)), 1, 0);
        Label victoryLabel = new Label("Victory points: ");
        pointsTable.add(new Label(Integer.toString(victoryPoints)),1,1);
        pointsTable.add(victoryLabel, 0, 1);
        Label faithLabel= new Label("Faith points: ");
        pointsTable.add(faithLabel, 0, 2);
        pointsTable.add(new Label(Integer.toString(faithPoints)), 1, 2);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);

        rightPane.getChildren().addAll(myButton, separator, redPane, blackPane, whitePane, neutralPane, separator1, turnBox, separator2, pointsTable);
        return rightPane;
    }
}
