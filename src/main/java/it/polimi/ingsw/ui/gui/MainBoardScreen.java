package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This is the Graphic User Interface main board class
 */
public class MainBoardScreen extends Application{
    /**
     * Family member related values
     */
    private int redValue;
    private int whiteValue;
    private int blackValue;
    private final int neutralValue= 0;

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
    private static final int BACK_WIDTH = 150;
    private static final int BACK_HEIGHT = 200;
    private static final int GRID_TOWER_X = 60;
    private static final int GRID_TOWER_Y = 40;
    private static final int GRID_TOWER_HGAP = 20;
    private static final int GRID_TOWER_VGAP = 30;
    private static final int GRID_ACTION_X = 70;
    private static final int GRID_ACTION_Y = 650;
    private static final int GRID_MARKET_HGAP = 20;
    private static final int GRID_MARKET_VGAP = 30;
    private static final int HBOX_SPACING = 80;
    private static final int VBOX_SPACING = 10;
    private static final int CIRCLE_RADIUS = 10;
    private static final int FAMILY_RADIUS = 20;

    /**
     * Main gui MainBoardScreen objects
     */
    private VBox rightPane;
    private BackgroundImage background;
    private Button personalBoardButton;
    private Button personalTileButton;
    private AnchorPane leftPane;
    private GridPane gridTower;
    private Scene scene;

    /**
     * Constructor for Main Board class
     */
    public MainBoardScreen(){

    }

    /**
     * Setting parameters
     * @param username
     * @param militaryPoints
     * @param victoryPoints
     * @param faithPoints
     */
    public void setPlayerData(String username, int militaryPoints, int victoryPoints, int faithPoints){
        this.username = username;
        this.militaryPoints = militaryPoints;
        this.victoryPoints = victoryPoints;
        this.faithPoints = faithPoints;
    }


    /**
     * The main entry point for all JavaFX applications
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MainBoardScreen");

        SplitPane splitPane = new SplitPane();
        leftPane = createLeftPane();
        rightPane = createRightPane();
        Group group = new Group();
        group.getChildren().add(leftPane);
        splitPane.getItems().addAll(group, rightPane);

        scene = new Scene(splitPane);

        primaryStage.setScene(scene);
        primaryStage.setHeight(STAGE_HEIGHT);
        primaryStage.setWidth(STAGE_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Create the left pane of the split pane
     * @return the left pane
     */
    public AnchorPane createLeftPane(){
        leftPane = new AnchorPane();

        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_HEIGHT, false, false, true, false);
        Image image2 = new Image("/images/MainBoardCover.jpg");
        background = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        leftPane.setBackground(new Background(background));
        gridTower = new GridPane();
        gridTower.relocate(GRID_TOWER_X, GRID_TOWER_Y);
        gridTower.setVgap(GRID_TOWER_VGAP);
        gridTower.setHgap(GRID_TOWER_HGAP);
        for (int i = 0; i <= 7 ; i++) {
            for (int j = 0; j <= 3; j++) {
                if(i%2==0){
                    ImageView imageView = new ImageView();
                    gridTower.add(imageView, i, j);
                }else{
                    Circle circle = new Circle(10, Color.AQUA);
                    manageTargetEvent(circle);
                    gridTower.add(circle, i, j);
                }
            }
        }

        ImageView firsExcommunicationCard = new ImageView();
        ImageView secondExcommunicationCard = new ImageView();
        ImageView thirdExcommunicationCard = new ImageView();
        gridTower.add(firsExcommunicationCard, 1, 5);
        gridTower.add(secondExcommunicationCard, 2, 5);
        gridTower.add(thirdExcommunicationCard, 3, 5);

        Circle council = new Circle(CIRCLE_RADIUS);
        manageTargetEvent(council);
        gridTower.add(council, 5, 4);

        GridPane gridAction = new GridPane();
        gridAction.setHgap(GRID_TOWER_HGAP);
        gridAction.setVgap(GRID_TOWER_VGAP);
        Circle circleProduction = new Circle(CIRCLE_RADIUS);
        manageTargetEvent(circleProduction);
        gridAction.add(circleProduction, 0, 0);
        //if()
        //	Circle circleProductionExtended = new Circle(CIRCLE_RADIUS);
        //  manageTargetEvent(circleProductionExtended);
        //	gridAction.add(circleProductionExtended, 1, 0);
        //else
        Rectangle coverProduction = new Rectangle(30, 20);
        gridAction.add(coverProduction, 1, 0);

        Circle circleHarvest = new Circle(10);
        manageTargetEvent(circleHarvest);
        gridAction.add(circleHarvest, 0, 1);
        //if()
        //	Circle circleHarvestExtended = new Circle(CIRCLE_RADIUS);
        //  manageTargetEvent(circleHarvestExtended);
        //	gridAction.add(circleHarvestExtended, 1, 1);
        //else
        Rectangle coverHarvest = new Rectangle(30, 20);
        gridAction.add(coverHarvest, 1, 1);

        GridPane gridMarket = new GridPane();
        Circle circleMarket1 = new Circle(CIRCLE_RADIUS);
        manageTargetEvent(circleMarket1);
        Circle circleMarket2 = new Circle(CIRCLE_RADIUS);
        manageTargetEvent(circleMarket2);
        gridMarket.add(circleMarket1, 3, 0);
        gridMarket.add(circleMarket2, 4, 0);

        //if()
        //Circle circleMarketExtended1 = new Circle(CIRCLE_RADIUS);
        //Circle circleMarketExtended2 = new Circle(CIRCLE_RADIUS);
        //manageTargetEvent(circleMarketExtended1);
        //manageTargetEvent(circleMarketExtended2);
        //gridMarket.add(circleMarketExtended1, 5, 0);
        //gridMarket.add(circleMarketExtended2, 6, 1);
        //else
        Rectangle coverMarket1 = new Rectangle();
        Rectangle coverMarket2 = new Rectangle();
        gridMarket.add(coverMarket1, 5, 0);
        gridMarket.add(coverMarket2, 6, 1);

        gridMarket.setHgap(GRID_MARKET_HGAP);
        gridMarket.setVgap(GRID_MARKET_VGAP);


        HBox hBox = new HBox(HBOX_SPACING);
        hBox.getChildren().addAll(gridAction, gridMarket);
        leftPane.getChildren().addAll(gridTower, hBox);
        hBox.relocate(GRID_ACTION_X, GRID_ACTION_Y);
        return leftPane;
    }

    /**
     * Create the right pane of the split pane
     * @return the right pane
     */
    public VBox createRightPane(){
        rightPane= new VBox(VBOX_SPACING);
        personalBoardButton = new Button("View Personal Board");
        personalBoardButton.setAlignment(Pos.CENTER);
        personalBoardButton.setOnAction(event -> {
            Application personalBoardScreen = new PersonalBoardScreen(username);
            Thread thread = new Thread(()->Application.launch(personalBoardScreen.getClass()));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        personalTileButton = new Button("View Personal Tile");
        personalTileButton.setAlignment(Pos.CENTER);
        personalTileButton.setOnAction(event -> {
            Application personalBoardTileScreen = new PersonalTileBoardScreen(username);
            Thread thread = new Thread(()->Application.launch(personalBoardTileScreen.getClass()));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        Circle redMember= new Circle(FAMILY_RADIUS);
        redMember.setFill(Color.RED);
        Label redLabel = new Label();
        StackPane redPane = new StackPane();
        manageSourceEvent(redMember);
        redPane.getChildren().addAll(redMember, redLabel);

        Circle blackMember= new Circle(FAMILY_RADIUS);
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
        neutralPane.getChildren().addAll(neutralMember, neutralLabel);

        redLabel.textProperty().bind( new SimpleIntegerProperty(redValue).asString());
        blackLabel.textProperty().bind( new SimpleIntegerProperty(blackValue).asString());
        whiteLabel.textProperty().bind(new SimpleIntegerProperty(whiteValue).asString());
        neutralLabel.textProperty().bind(new SimpleIntegerProperty(neutralValue).asString());

        HBox turnBox = new HBox();
        Label turn = new Label("Turn = ");
        Label usernameLabel = new Label();
        usernameLabel.textProperty().bind(new SimpleStringProperty(username));
        turnBox.getChildren().addAll(turn, usernameLabel);

        GridPane pointsTable = new GridPane();
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

        rightPane.getChildren().addAll(personalBoardButton, personalTileButton, separator, redPane, blackPane, whitePane, neutralPane, separator1, turnBox, separator2, pointsTable);
        return rightPane;
    }

    /**
     * Convert an hexadecimal string into a Color
     * @param hexColor string
     * @return related color
     */
    private static Color stringToColor(String hexColor) {
        return Color.rgb(Integer.valueOf( hexColor.substring( 1, 3 ), 16 ),
                Integer.valueOf( hexColor.substring( 3, 5 ), 16 ),
                Integer.valueOf( hexColor.substring( 5, 7 ), 16 ));
    }

    /**
     * Method to manage the "Drag Detected" and "Drag Done" event
     * @param source node which triggers the action during the events
     */
    private void manageSourceEvent(Circle source){
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
    private void manageTargetEvent(Circle target){

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
            if (event.getGestureSource() != target && event.getDragboard().hasString()){
                target.setScaleX(target.getScaleX()*1.3);
                target.setScaleY(target.getScaleY()*1.3);

            }
            event.consume();
        });

        target.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            target.setScaleX(target.getScaleX()/1.3);
            target.setScaleY(target.getScaleY()/1.3);
            event.consume();
        });

        target.setOnDragDropped(event -> {
            String[] name;
            System.out.println("onDragDropped");
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasString()){
                target.setDisable(true);
                name = db.getString().split("fill=0");
                Color color = stringToColor(name[1]);
                target.setFill(color);

                int column = GridPane.getColumnIndex(target);
                int row = GridPane.getRowIndex(target);
                ImageView card = (ImageView) getNodeInGrid(row, column-1);
                card.setVisible(false);
                success = true;

            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    /**
     * Get a node in the gridTower object
     * @param row index
     * @param column index
     * @return Node
     */

    private Node getNodeInGrid(int row, int column){
        Node result = null;
        ObservableList<Node> childrens = gridTower.getChildren();
        for (Node node : childrens) {
            if(gridTower.getRowIndex(node) == row && gridTower.getColumnIndex(node) == column) {
                result= node;
            }
        }
        return result;
    }

}
