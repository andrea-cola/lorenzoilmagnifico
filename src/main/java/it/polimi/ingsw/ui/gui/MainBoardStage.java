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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/**
 * This is the Graphic User Interface main board class
 */
public class MainBoardStage extends JFXPanel implements MainBoardSettings{
    /**
     * Family member related values
     */
    private int redValue;
    private int whiteValue;
    private int blackValue;
    private int neutralValue;

    private CallbackInterface callback;
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
    private static final int BACK_WIDTH = 150;
    private static final int BACK_HEIGHT = 200;
    private static final int GRID_TOWER_X = 20;
    private static final int GRID_TOWER_Y = 20;
    private static final int GRID_TOWER_HGAP = 20;
    private static final int GRID_TOWER_VGAP = 30;
    private static final int GRID_ACTION_X = 20;
    private static final int GRID_ACTION_Y = 500;
    private static final int GRID_MARKET_HGAP = 20;
    private static final int GRID_MARKET_VGAP = 30;
    private static final int HBOX_SPACING = 80;
    private static final int VBOX_SPACING = 10;
    private static final int CIRCLE_RADIUS = 10;
    private static final int FAMILY_RADIUS = 20;
    private static final int IMAGE_HEIGHT = 70;
    private static final int IMAGE_WIDTH = 50;


    /**
     * Main gui MainBoardStage objects
     */
    private VBox rightPane;
    private BackgroundImage background;
    private Button personalBoardButton;
    private Button personalTileButton;
    private Button leaderCardsButton;
    private AnchorPane leftPane;
    private GridPane gridTower;
    private GridPane gridAction;
    private GridPane gridMarket;
    private Scene scene;


    /**
     * Constructor for Main Board class
     */
    MainBoardStage(CallbackInterface callback, Player player, Game game){
        this.game = game;
        this.player = player;
        this.callback = callback;

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
    public AnchorPane createLeftPane(){
        leftPane = new AnchorPane();

        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_HEIGHT, false, false, true, false);
        Image image2 = new Image("/images/MainBoardStageCover.jpg");
        background = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        leftPane.setBackground(new Background(background));

        gridTower.relocate(GRID_TOWER_X, GRID_TOWER_Y);
        gridTower.setVgap(GRID_TOWER_VGAP);
        gridTower.setHgap(GRID_TOWER_HGAP);
        for (int i = 0; i <= 7 ; i++) {
            for (int j = 0; j < 4; j++) {
                if(i%2==0){
                    setDevelopmentCardInTowerCell(i/2, j);
                }else{
                    ImageView card = (ImageView) getNodeInGrid(i-1, j);
                    Circle circle = new Circle(10, Color.AQUA);
                    gridTower.add(circle, i, j);
                    int column = i;
                    int row = j;
                    FamilyMemberColor memberColor = manageTargetEvent(circle);
                    System.out.println(memberColor);
                    circle.setOnMouseDragReleased(event -> {
                        card.setVisible(false);
                        try {
                            game.pickupDevelopmentCardFromTower(player, manageTargetEvent(circle), column, row, null);
                            circle.setVisible(false);
                            circle.setDisable(true);
                        } catch (GameException e) {
                            callback.showGameException();
                        }
                        event.consume();
                    });

                }
            }
        }

        /**
        ImageView firsExcommunicationCard = new ImageView();
        ImageView secondExcommunicationCard = new ImageView();
        ImageView thirdExcommunicationCard = new ImageView();
        gridTower.add(firsExcommunicationCard, 1, 5);
        gridTower.add(secondExcommunicationCard, 2, 5);
        gridTower.add(thirdExcommunicationCard, 3, 5);
        */

        Circle council = new Circle(CIRCLE_RADIUS);
        gridTower.add(council, 5, 4);
        FamilyMemberColor councilColor = manageTargetEvent(council);
        council.setOnDragDropped(event -> {
            try {
                game.placeFamilyMemberInsideCouncilPalace(player, councilColor , null);
            } catch (GameException e) {
                callback.showGameException();
            }
        });

        gridAction.setHgap(GRID_TOWER_HGAP);
        gridAction.setVgap(GRID_TOWER_VGAP);

        Circle circleProduction = new Circle(CIRCLE_RADIUS);
        FamilyMemberColor productionColor = manageTargetEvent(circleProduction);
        gridAction.add(circleProduction, 0, 0);
        circleProduction.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                game.placeFamilyMemberInsideHarvestSimpleSpace(player, productionColor, null);
            }
        });

        Circle circleHarvest = new Circle(10);
        FamilyMemberColor harvestColor = manageTargetEvent(circleHarvest);
        gridAction.add(circleHarvest, 0, 1);
        circleHarvest.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                game.placeFamilyMemberInsideHarvestSimpleSpace(player, harvestColor,null);
            }
        });

        gridMarket.setHgap(GRID_MARKET_HGAP);
        gridMarket.setVgap(GRID_MARKET_VGAP);

        Circle circleMarket1 = new Circle(CIRCLE_RADIUS);
        FamilyMemberColor marketColor1 = manageTargetEvent(circleMarket1);
        gridMarket.add(circleMarket1, 3, 0);
        circleMarket1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                try {
                    game.placeFamilyMemberInsideMarket(player, marketColor1, 1, null);
                } catch (GameException e) {
                    callback.showGameException();
                }
            }
        });

        Circle circleMarket2 = new Circle(CIRCLE_RADIUS);
        FamilyMemberColor marketColor2 = manageTargetEvent(circleMarket2);
        gridMarket.add(circleMarket2, 4, 0);
        circleMarket2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                try {
                    game.placeFamilyMemberInsideMarket(player, marketColor2, 2, null);
                } catch (GameException e) {
                    callback.showGameException();
                }
            }
        });

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
        rightPane.setAlignment(Pos.CENTER);

        personalBoardButton = new Button("PERSONAL BOARD");
        personalBoardButton.setAlignment(Pos.CENTER);
        personalBoardButton.setOnAction(event -> this.callback.showPersonalBoardStage(player));

        personalTileButton = new Button("PERSONAL TILE");
        personalTileButton.setAlignment(Pos.CENTER);
        personalTileButton.setOnAction(event -> this.callback.showPersonalTileBoardStage(player));

        leaderCardsButton = new Button("LEADER CARDS");
        leaderCardsButton.setAlignment(Pos.CENTER);
        leaderCardsButton.setOnAction(event -> callback.showLeaderCards(player));

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

        rightPane.getChildren().addAll(personalBoardButton, personalTileButton, leaderCardsButton, separator, redPane, blackPane, whitePane, neutralPane, separator1, turnBox, separator2, pointsTable);
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
    private FamilyMemberColor manageTargetEvent(Circle target){
        final Color[] color = new Color[1];
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
            Node node = event.getPickResult().getIntersectedNode();
            int x = GridPane.getColumnIndex(node);
            if(db.hasString()){
                boolean success = false;
                target.setDisable(true);
                name = db.getString().split("fill=0");
                color[0] = stringToColor(name[1]);
                target.setFill(color[0]);
                success = true;
                event.setDropCompleted(success);
                event.consume();
            }
        });

        if(Color.RED.equals(color))
            return FamilyMemberColor.ORANGE;
        else if(Color.BLACK.equals(color))
            return FamilyMemberColor.BLACK;
        else if(Color.GRAY.equals(color))
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
    private Node getNodeInGrid(int column, int row){
        Node result = null;
        ObservableList<Node> childrens = gridTower.getChildren();
        for (Node node : childrens) {
            if(gridTower.getRowIndex(node) == row && gridTower.getColumnIndex(node) == column) {
                result= node;
            }
        }
        return result;
    }

    @Override
    public void showExtraProduction() {
        System.out.println("setting production...");
        if(game.getPlayersMap().size()>2) {
            Circle circleProductionExtended = new Circle(CIRCLE_RADIUS);
            manageTargetEvent(circleProductionExtended);
            gridAction.add(circleProductionExtended, 1, 0);
        }else {
            Rectangle coverProduction = new Rectangle(30, 20);
            coverProduction.setFill(new ImagePattern(new Image("images/actionCover/productionCover.png")));
            gridAction.add(coverProduction, 1, 0);
        }
    }

    @Override
    public void showExtraHarvest() {
        System.out.println("setting harvest...");
        if(game.getPlayersMap().size()>2) {
            Circle circleHarvestExtended = new Circle(CIRCLE_RADIUS);
            manageTargetEvent(circleHarvestExtended);
            gridAction.add(circleHarvestExtended, 1, 1);
        }else {
            Rectangle coverHarvest = new Rectangle(30, 20);
            coverHarvest.setFill(new ImagePattern(new Image("images/actionCover/productionCover.png")));
            gridAction.add(coverHarvest, 1, 1);
        }
    }

    @Override
    public void showExtraMarket() {
        System.out.println("setting market...");
        if(game.getPlayersMap().size()>3) {
            Circle circleMarketExtended1 = new Circle(CIRCLE_RADIUS);
            Circle circleMarketExtended2 = new Circle(CIRCLE_RADIUS);
            manageTargetEvent(circleMarketExtended1);
            manageTargetEvent(circleMarketExtended2);
            gridMarket.add(circleMarketExtended1, 5, 0);
            gridMarket.add(circleMarketExtended2, 6, 1);
        }else {
            Rectangle coverMarket1 = new Rectangle(30, 20);
            Rectangle coverMarket2 = new Rectangle(30, 20);
            coverMarket1.setFill(new ImagePattern(new Image("images/actionCover/marketCover.png")));
            coverMarket1.setFill(new ImagePattern(new Image("images/actionCover/marketCover.png")));
            gridMarket.add(coverMarket1, 5, 0);
            gridMarket.add(coverMarket2, 6, 1);
        }
    }

    @Override
    public void setDevelopmentCardInTowerCell(int tower, int towerCell) {
        System.out.println("setting tower cell...");
        DevelopmentCard devCard = game.getMainBoard().getTower(tower).getTowerCell(towerCell).getDevelopmentCard();
        StringBuilder path = new StringBuilder();
        path.append("images/developmentCard/devcards_f_en_c_");
        path.append(devCard.getId());
        path.append(".png");
        ImageView image = new ImageView(new Image(path.toString()));
        image.setFitHeight(IMAGE_HEIGHT);
        image.setFitWidth(IMAGE_WIDTH);
        image.autosize();
        gridTower.add(image, tower*2, towerCell);
    }

    @Override
    public void setExcommunicationCard() {
        ExcommunicationCard[] exCard = game.getMainBoard().getVatican().getExcommunicationCards();
        StringBuilder path = new StringBuilder();
    }



    interface CallbackInterface{

        void showPersonalBoardStage(Player player);

        void showPersonalTileBoardStage(Player player);

        void showLeaderCards(Player player);

        void showGameException();

        void notifyEndTurnStage();
    }
}
