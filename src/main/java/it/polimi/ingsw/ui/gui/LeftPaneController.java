package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.UserInterface;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LeftPaneController implements LeftPaneSettigs  {


    private MainBoardStage.CallbackInterface callback;
    private InformationCallback informationCallback;
    private UserInterface client;
    private Integer servantValue;
    private boolean turn;
    private Game game;
    private Player player;
    private static final int CIRCLE_RADIUS = 10;


    @FXML
    private Circle circleTower10;
    @FXML
    private Circle circleTower30;
    @FXML
    private Circle circleTower50;
    @FXML
    private Circle circleTower70;
    @FXML
    private Circle circleTower11;
    @FXML
    private Circle circleTower31;
    @FXML
    private Circle circleTower51;
    @FXML
    private Circle circleTower71;
    @FXML
    private Circle circleTower12;
    @FXML
    private Circle circleTower32;
    @FXML
    private Circle circleTower52;
    @FXML
    private Circle circleTower72;
    @FXML
    private Circle circleTower13;
    @FXML
    private Circle circleTower33;
    @FXML
    private Circle circleTower53;
    @FXML
    private Circle circleTower73;
    @FXML
    private Circle circleCouncil54;
    @FXML
    private Circle circleHarvestSimple;
    @FXML
    private Circle circleHarvestExtended;
    @FXML
    private Circle circleProductionSimple;
    @FXML
    private Circle circleProductionExtended;

    @FXML
    private ImageView imageVatican1;
    @FXML
    private ImageView imageVatican2;
    @FXML
    private ImageView imageVatican3;

    @FXML
    private Circle circleMarket1;
    @FXML
    private Circle circleMarket2;
    @FXML
    private Circle circleMarket3;
    @FXML
    private Circle circleMarket4;


    @FXML
    private ImageView imageTower00;
    @FXML
    private ImageView imageTower20;
    @FXML
    private ImageView imageTower40;
    @FXML
    private ImageView imageTower60;
    @FXML
    private ImageView imageTower01;
    @FXML
    private ImageView imageTower21;
    @FXML
    private ImageView imageTower41;
    @FXML
    private ImageView imageTower61;
    @FXML
    private ImageView imageTower02;
    @FXML
    private ImageView imageTower22;
    @FXML
    private ImageView imageTower42;
    @FXML
    private ImageView imageTower62;
    @FXML
    private ImageView imageTower03;
    @FXML
    private ImageView imageTower23;
    @FXML
    private ImageView imageTower43;
    @FXML
    private ImageView imageTower63;

    @FXML
    private ImageView biggerCard;

    @FXML
    private GridPane gridTower;
    @FXML
    private GridPane gridAction;
    @FXML
    private GridPane gridMarket;
    @FXML
    private GridPane gridVatican;

    private MainBoardStage mainBoardStage;


    void initData(MainBoardStage mainBoardStage){

        this.mainBoardStage = mainBoardStage;

        this.game = mainBoardStage.getGame();
        this.callback = mainBoardStage.getCallback();
        this.informationCallback = mainBoardStage.getInformationCallback();
        this.turn = mainBoardStage.getTurn();
        this.client = mainBoardStage.getClient();
        this.player = client.getPlayer();
        this.servantValue = client.getPlayer().getPersonalBoard().getValuables().getPoints().get(ResourceType.SERVANT);

        setDevelopmentCardInTowerCell();
        setExcommunicationCardInVatican();
        setGridAction();
        setCouncil();
        setGridMarket();
    }

    @FXML
    private void initialize() {
        System.out.println("started!");
    }

    @Override
    public void setGridAction(){
        showExtraProduction();
        circleProductionSimple.setVisible(false);
        if(game.getMainBoard().getProduction().isEmpty() && turn) {
            circleProductionSimple.setVisible(true);
            manageTargetEvent(circleProductionSimple);
            circleProductionSimple.setOnDragDropped(event -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                if (db.hasString()) {
                    boolean success = false;
                    circleProductionSimple.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleProductionSimple.setFill(color);
                    try {
                        game.placeFamilyMemberInsideHarvestSimpleSpace(player, getMemberColor(color), servantsToSpend(color), informationCallback);
                        client.notifySetFamilyMemberInProductionSimple(getMemberColor(color), servantsToSpend(color));
                        this.callback.setUsedMember(true);
                        this.callback.updateMainBoard();
                    } catch (GameException e) {
                        callback.showGameException(e.getMessage());
                        circleProductionSimple.setDisable(false);
                        circleProductionSimple.setFill(Color.AQUA);
                    }
                    circleProductionSimple.setDisable(true);
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();

                }
            });
        }
        showExtraHarvest();
        circleHarvestSimple.setVisible(false);
        if(game.getMainBoard().getHarvest().isEmpty() && turn) {
            circleHarvestSimple.setVisible(true);
            manageTargetEvent(circleHarvestSimple);
            circleHarvestSimple.setOnDragDropped(event -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                if (db.hasString()) {
                    boolean success = false;
                    circleHarvestSimple.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleHarvestSimple.setFill(color);
                    try {
                        game.placeFamilyMemberInsideHarvestSimpleSpace(player, getMemberColor(color), servantsToSpend(color), informationCallback);
                        client.notifySetFamilyMemberInHarvestSimple(getMemberColor(color), servantsToSpend(color));
                        this.callback.setUsedMember(true);
                        this.callback.updateMainBoard();
                    } catch (GameException e) {
                        callback.showGameException(e.getMessage());
                        circleHarvestSimple.setDisable(false);
                        circleHarvestSimple.setFill(Color.AQUA);
                    }
                    circleHarvestSimple.setDisable(true);
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }
    }

    public void setGridMarket(){
        showExtraMarket();
        circleMarket1.setVisible(false);
        if(game.getMainBoard().getMarket().getMarketCell(0).isEmpty() && turn) {
            circleMarket1.setVisible(true);
            manageTargetEvent(circleMarket1);
            manageMarketDragDropped(circleMarket1, 0);
        }
        circleMarket2.setVisible(false);
        if(game.getMainBoard().getMarket().getMarketCell(1).isEmpty() && turn) {
            circleMarket2.setVisible(true);
            manageTargetEvent(circleMarket2);
            manageMarketDragDropped(circleMarket2, 1);
        }
    }

    public void setCouncil(){
        circleCouncil54.setVisible(false);
        if(turn) {
            circleCouncil54.setVisible(true);
            manageTargetEvent(circleCouncil54);
            circleCouncil54.setOnDragDropped(event -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                if (db.hasString()) {
                    boolean success = false;
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleCouncil54.setFill(color);
                    circleCouncil54.setDisable(true);
                    try {
                        game.placeFamilyMemberInsideCouncilPalace(player, getMemberColor(color), servantsToSpend(color), informationCallback);
                        client.notifySetFamilyMemberInCouncil(getMemberColor(color), servantsToSpend(color));
                        this.callback.setUsedMember(true);
                        this.callback.updateMainBoard();
                    } catch (GameException e) {
                        callback.showGameException(e.getMessage());
                        circleCouncil54.setDisable(false);
                        circleCouncil54.setFill(Color.AQUA);
                    }
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }
    }

    @Override
    public void setExcommunicationCardInVatican() {
        System.out.println("setting vatican...");

        StringBuilder path = new StringBuilder();
        path.append("images/excommunicationCard/excomm_1_");
        path.append(game.getMainBoard().getVatican().getExcommunicationCard(0).getCardID());
        path.append(".png");
        imageVatican1.setImage(new Image(path.toString()));

        StringBuilder path1 = new StringBuilder();
        path1.append("images/excommunicationCard/excomm_2_");
        path1.append(game.getMainBoard().getVatican().getExcommunicationCard(1).getCardID()-7);
        path1.append(".png");
        imageVatican2.setImage(new Image(path1.toString()));

        StringBuilder path2 = new StringBuilder();
        path2.append("images/excommunicationCard/excomm_3_");
        path2.append(game.getMainBoard().getVatican().getExcommunicationCard(2).getCardID()-14);
        path2.append(".png");
        imageVatican3.setImage(new Image(path2.toString()));
    }

    @Override
    public void showExtraProduction() {
        circleProductionExtended.setVisible(false);
        System.out.println("setting production...");
        if (game.getPlayersMap().size() > 2 && turn) {
            circleHarvestExtended.setVisible(true);
            manageTargetEvent(circleProductionExtended);
            circleProductionExtended.setOnDragDropped((DragEvent event) -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                if (db.hasString()) {
                    boolean success = false;
                    circleProductionExtended.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleProductionExtended.setFill(color);
                    try {
                        game.placeFamilyMemberInsideProductionExtendedSpace(player, getMemberColor(color), servantsToSpend(color), informationCallback);
                        client.notifySetFamilyMemberInProductionExtended(getMemberColor(color), servantsToSpend(color));
                        this.callback.setUsedMember(true);
                        this.callback.updateMainBoard();
                    } catch (GameException e) {
                        callback.showGameException(e.getMessage());
                        circleProductionExtended.setDisable(false);
                        circleProductionExtended.setFill(Color.AQUA);
                    }
                    circleProductionExtended.setDisable(true);
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
        circleHarvestExtended.setVisible(false);
        if (game.getPlayersMap().size() > 2 && turn) {
            circleHarvestExtended.setVisible(true);
            manageTargetEvent(circleHarvestExtended);
            circleHarvestExtended.setOnDragDropped((DragEvent event) -> {
                String[] name;
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                if (db.hasString()) {
                    boolean success = false;
                    circleHarvestExtended.setDisable(true);
                    name = db.getString().split("fill=0");
                    Color color = stringToColor(name[1]);
                    circleHarvestExtended.setFill(color);
                    try {
                        game.placeFamilyMemberInsideHarvestExtendedSpace(player, getMemberColor(color), servantsToSpend(color), informationCallback);
                        client.notifySetFamilyMemberInHarvestExtended(getMemberColor(color), servantsToSpend(color));
                        this.callback.setUsedMember(true);
                        this.callback.updateMainBoard();
                    } catch (GameException e) {
                        callback.showGameException(e.getMessage());
                        circleHarvestExtended.setDisable(false);
                        circleHarvestExtended.setFill(Color.AQUA);
                    }
                    circleHarvestExtended.setDisable(true);
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                }
            });
        }
    }

    @Override
    public void showExtraMarket() {
        circleMarket3.setVisible(false);
        circleMarket4.setVisible(false);
        System.out.println("setting market...");
        if (game.getPlayersMap().size() > 3 && turn) {
            if (game.getMainBoard().getMarket().getMarketCell(2).isEmpty()) {
                circleMarket3.setVisible(true);
                manageTargetEvent(circleMarket3);
                manageMarketDragDropped(circleMarket3, 2);
            }
            if (game.getMainBoard().getMarket().getMarketCell(3).isEmpty()) {
                circleMarket4.setVisible(true);
                manageTargetEvent(circleMarket4);
                manageMarketDragDropped(circleMarket4, 3);
            }
        }
    }

    @Override
    public void setDevelopmentCardInTowerCell () {
            System.out.println("setting tower cell...");
            StringBuilder path;
            circleTower10.setVisible(false);
            if (game.getMainBoard().getTower(0).getTowerCell(3).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(0).getTowerCell(3).getDevelopmentCard().getId());
                path.append(".png");
                imageTower00.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower00);
                if (turn) {
                    circleTower10.setVisible(true);
                    manageTargetEvent(circleTower10);
                    manageTowerDragDropped(circleTower10, 0, 3, imageTower00);
                }
            }
            circleTower11.setVisible(false);
            if (game.getMainBoard().getTower(0).getTowerCell(2).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(0).getTowerCell(2).getDevelopmentCard().getId());
                path.append(".png");
                imageTower01.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower01);
                if (turn) {
                    circleTower11.setVisible(true);
                    manageTargetEvent(circleTower11);
                    manageTowerDragDropped(circleTower11, 0, 2, imageTower01);
                }
            }
            circleTower12.setVisible(false);
            if (game.getMainBoard().getTower(0).getTowerCell(1).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(0).getTowerCell(1).getDevelopmentCard().getId());
                path.append(".png");
                imageTower02.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower02);
                if (turn) {
                    circleTower12.setVisible(true);
                    manageTargetEvent(circleTower12);
                    manageTowerDragDropped(circleTower12, 0, 1, imageTower02);
                }
            }
            circleTower13.setVisible(false);
            if (game.getMainBoard().getTower(0).getTowerCell(0).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(0).getTowerCell(0).getDevelopmentCard().getId());
                path.append(".png");
                imageTower03.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower03);
                if (turn) {
                    circleTower13.setVisible(true);
                    manageTargetEvent(circleTower13);
                    manageTowerDragDropped(circleTower13, 0, 0, imageTower03);
                }
            }
            circleTower30.setVisible(false);
            if (game.getMainBoard().getTower(1).getTowerCell(3).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(1).getTowerCell(3).getDevelopmentCard().getId());
                path.append(".png");
                imageTower20.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower20);
                if (turn) {
                    circleTower30.setVisible(true);
                    manageTargetEvent(circleTower30);
                    manageTowerDragDropped(circleTower30, 1, 3, imageTower20);
                }
            }
            circleTower31.setVisible(false);
            if (game.getMainBoard().getTower(1).getTowerCell(2).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(1).getTowerCell(2).getDevelopmentCard().getId());
                path.append(".png");
                imageTower21.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower21);
                if (turn) {
                    circleTower31.setVisible(true);
                    manageTargetEvent(circleTower31);
                    manageTowerDragDropped(circleTower31, 1, 2, imageTower21);
                }
            }
            circleTower32.setVisible(false);
            if (game.getMainBoard().getTower(1).getTowerCell(1).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard().getId());
                path.append(".png");
                imageTower22.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower22);
                if (turn) {
                    circleTower32.setVisible(true);
                    manageTargetEvent(circleTower32);
                    manageTowerDragDropped(circleTower32, 1, 1, imageTower22);
                }
            }
            circleTower33.setVisible(false);
            if (game.getMainBoard().getTower(1).getTowerCell(0).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(1).getTowerCell(0).getDevelopmentCard().getId());
                path.append(".png");
                imageTower23.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower23);
                if (turn) {
                    circleTower33.setVisible(true);
                    manageTargetEvent(circleTower33);
                    manageTowerDragDropped(circleTower33, 1, 0, imageTower23);
                }
            }
            circleTower50.setVisible(false);
            if (game.getMainBoard().getTower(2).getTowerCell(3).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(2).getTowerCell(3).getDevelopmentCard().getId());
                path.append(".png");
                imageTower40.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower40);
                if (turn) {
                    circleTower50.setVisible(true);
                    manageTargetEvent(circleTower50);
                    manageTowerDragDropped(circleTower50, 2, 3, imageTower40);
                }
            }
            circleTower51.setVisible(false);
            if (game.getMainBoard().getTower(2).getTowerCell(2).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(2).getTowerCell(2).getDevelopmentCard().getId());
                path.append(".png");
                imageTower41.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower41);
                if (turn) {
                    circleTower51.setVisible(true);
                    manageTargetEvent(circleTower51);
                    manageTowerDragDropped(circleTower51, 2, 2, imageTower41);
                }
            }
            circleTower52.setVisible(false);
            if (game.getMainBoard().getTower(2).getTowerCell(1).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(2).getTowerCell(1).getDevelopmentCard().getId());
                path.append(".png");
                imageTower42.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower42);
                if (turn) {
                    circleTower52.setVisible(true);
                    manageTargetEvent(circleTower52);
                    manageTowerDragDropped(circleTower52, 2, 1, imageTower42);
                }
            }
            circleTower53.setVisible(false);
            if (game.getMainBoard().getTower(2).getTowerCell(0).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(2).getTowerCell(0).getDevelopmentCard().getId());
                path.append(".png");
                imageTower43.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower43);
                if (turn) {
                    circleTower53.setVisible(true);
                    manageTargetEvent(circleTower53);
                    manageTowerDragDropped(circleTower53, 2, 0, imageTower43);
                }
            }
            circleTower70.setVisible(false);
            if (game.getMainBoard().getTower(3).getTowerCell(3).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(3).getTowerCell(3).getDevelopmentCard().getId());
                path.append(".png");
                imageTower60.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower60);
                if (turn) {
                    circleTower70.setVisible(true);
                    manageTargetEvent(circleTower70);
                    manageTowerDragDropped(circleTower70, 3, 3, imageTower60);
                }
            }
            circleTower71.setVisible(false);
            if (game.getMainBoard().getTower(3).getTowerCell(2).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(3).getTowerCell(2).getDevelopmentCard().getId());
                path.append(".png");
                imageTower61.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower61);
                if (turn) {
                    circleTower71.setVisible(true);
                    manageTargetEvent(circleTower71);
                    manageTowerDragDropped(circleTower71, 3, 2, imageTower61);
                }
            }
            circleTower72.setVisible(false);
            if (game.getMainBoard().getTower(3).getTowerCell(1).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(3).getTowerCell(1).getDevelopmentCard().getId());
                path.append(".png");
                imageTower62.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower62);
                if (turn) {
                    circleTower72.setVisible(true);
                    manageTargetEvent(circleTower72);
                    manageTowerDragDropped(circleTower72, 3, 1, imageTower62);
                }
            }
            circleTower73.setVisible(false);
            if (game.getMainBoard().getTower(3).getTowerCell(0).getPlayerNicknameInTheCell() == null) {
                path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(game.getMainBoard().getTower(3).getTowerCell(0).getDevelopmentCard().getId());
                path.append(".png");
                imageTower63.setImage(new Image(path.toString()));
                cardOnMousePressed(imageTower63);
                if (turn) {
                    circleTower73.setVisible(true);
                    manageTargetEvent(circleTower73);
                    manageTowerDragDropped(circleTower73, 3, 0, imageTower63);
                }
            }
    }

    private Integer servantsToSpend(Color color){
        return mainBoardStage.getServants();
    }

    private static FamilyMemberColor getMemberColor(Color color){
        if (Color.rgb(200, 110, 34).equals(color))
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

    private void cardOnMousePressed(ImageView card){
        card.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("pressed");
                biggerCard.setImage(card.getImage());
            }
        });
    }

    private void manageMarketDragDropped(Circle target, int index){
        target.setOnDragDropped((DragEvent event) -> {
            String[] name;
            System.out.println("onDragDropped");
            Dragboard db = event.getDragboard();
            Node node = event.getPickResult().getIntersectedNode();
            if (db.hasString()) {
                boolean success = false;
                target.setDisable(true);
                name = db.getString().split("fill=0");
                Color color = stringToColor(name[1]);
                target.setFill(color);
                try {
                    game.placeFamilyMemberInsideMarket(player, getMemberColor(color), servantsToSpend(color), index, informationCallback);
                    client.notifySetFamilyMemberInMarket(getMemberColor(color), servantsToSpend(color), index);
                    this.callback.setUsedMember(true);
                    this.callback.updateMainBoard();
                } catch (GameException e) {
                    callback.showGameException(e.getMessage());
                    target.setDisable(false);
                    target.setFill(Color.AQUA);
                }
                target.setDisable(true);
                success = true;
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    private void manageTowerDragDropped(Circle target, int tower, int towerCell, ImageView card){
        target.setOnDragDropped((DragEvent event) -> {
            String[] name;
            System.out.println("onDragDropped");
            Dragboard db = event.getDragboard();
            Node node = event.getPickResult().getIntersectedNode();
            if (db.hasString()) {
                boolean success = false;
                target.setDisable(true);
                name = db.getString().split("fill=0");
                Color color = stringToColor(name[1]);
                target.setFill(color);
                card.setVisible(false);
                target.setDisable(true);
                try {
                    this.game.pickupDevelopmentCardFromTower(player, getMemberColor(color), servantsToSpend(color), tower, towerCell, informationCallback);
                    this.client.notifySetFamilyMemberInTower(getMemberColor(color), servantsToSpend(color), tower, towerCell);
                    this.callback.setUsedMember(true);
                    this.callback.updateMainBoard();
                } catch (GameException e) {
                    this.callback.showGameException(e.getMessage());
                    card.setVisible(true);
                    target.setDisable(false);
                    target.setFill(Color.AQUA);
                }
                success = true;
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }
    /**
     * Method to manage the "Drag Over", "Drag Entered", "Drag Exited", "Drag Dropped" event
     * @param target node which is triggered by the event and does actions
     */
    private static void manageTargetEvent(Circle target) {
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

}