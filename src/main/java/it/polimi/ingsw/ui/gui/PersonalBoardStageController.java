package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class PersonalBoardStageController {
    private Player player;

    @FXML
    private ImageView purple0;
    @FXML
    private ImageView purple1;
    @FXML
    private ImageView purple2;
    @FXML
    private ImageView purple3;
    @FXML
    private ImageView purple4;
    @FXML
    private ImageView purple5;
    @FXML
    private ImageView blue0;
    @FXML
    private ImageView blue1;
    @FXML
    private ImageView blue2;
    @FXML
    private ImageView blue3;
    @FXML
    private ImageView blue4;
    @FXML
    private ImageView blue5;
    @FXML
    private ImageView yellow0;
    @FXML
    private ImageView yellow1;
    @FXML
    private ImageView yellow2;
    @FXML
    private ImageView yellow3;
    @FXML
    private ImageView yellow4;
    @FXML
    private ImageView yellow5;
    @FXML
    private ImageView green0;
    @FXML
    private ImageView green1;
    @FXML
    private ImageView green2;
    @FXML
    private ImageView green3;
    @FXML
    private ImageView green4;
    @FXML
    private ImageView green5;

    @FXML
    private ImageView biggerImage;

    void initData(Player player){
        this.player = player;
        setDevelopmentCard();
    }

    private void cardOnMousePressed(ImageView card){
        card.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                biggerImage.setImage(card.getImage());
            }
        });
    }

    void setDevelopmentCard() {
        if(!player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).isEmpty()){
            int size = player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).size();
            if(size > 0 && player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(0)!= null) {
                StringBuilder path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(0).getId());
                path.append(".png");
                purple0.setImage(new Image(path.toString()));
                cardOnMousePressed(purple0);
            }
            if(size > 1 && player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(1) != null) {
                StringBuilder path1 = new StringBuilder();
                path1.append("images/developmentCard/devcards_f_en_c_");
                path1.append(player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(1).getId());
                path1.append(".png");
                purple1.setImage(new Image(path1.toString()));
                cardOnMousePressed(purple1);
            }
            if(size > 2 && player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(2) != null) {
                StringBuilder path2 = new StringBuilder();
                path2.append("images/developmentCard/devcards_f_en_c_");
                path2.append(player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(2).getId());
                path2.append(".png");
                purple2.setImage(new Image(path2.toString()));
                cardOnMousePressed(purple2);
            }
            if(size > 3 && player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(3) != null ) {
                StringBuilder path3 = new StringBuilder();
                path3.append("images/developmentCard/devcards_f_en_c_");
                path3.append(player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(3).getId());
                path3.append(".png");
                purple3.setImage(new Image(path3.toString()));
                cardOnMousePressed(purple3);
            }
            if(size >4 && player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(4) != null) {
                StringBuilder path4 = new StringBuilder();
                path4.append("images/developmentCard/devcards_f_en_c_");
                path4.append(player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(4).getId());
                path4.append(".png");
                purple4.setImage(new Image(path4.toString()));
                cardOnMousePressed(purple4);
            }
            if(size > 5 && player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(5) != null) {
                StringBuilder path5 = new StringBuilder();
                path5.append("images/developmentCard/devcards_f_en_c_");
                path5.append(player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(5).getId());
                path5.append(".png");
                purple5.setImage(new Image(path5.toString()));
                cardOnMousePressed(purple5);
            }
        }
        if(!player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).isEmpty()){
            int size = player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).size();
            if(size > 0 &&  player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(0)!= null) {
                StringBuilder path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(0).getId());
                path.append(".png");
                blue0.setImage(new Image(path.toString()));
                cardOnMousePressed(blue0);
            }
            if(size > 1 && player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(1) != null) {
                StringBuilder path1 = new StringBuilder();
                path1.append("images/developmentCard/devcards_f_en_c_");
                path1.append(player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(1).getId());
                path1.append(".png");
                blue1.setImage(new Image(path1.toString()));
                cardOnMousePressed(blue1);
            }
            if(size > 2 && player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(2) != null) {
                StringBuilder path2 = new StringBuilder();
                path2.append("images/developmentCard/devcards_f_en_c_");
                path2.append(player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(2).getId());
                path2.append(".png");
                blue2.setImage(new Image(path2.toString()));
                cardOnMousePressed(blue2);
            }
            if(size > 3 && player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(3) != null) {
                StringBuilder path3 = new StringBuilder();
                path3.append("images/developmentCard/devcards_f_en_c_");
                path3.append(player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(3).getId());
                path3.append(".png");
                blue3.setImage(new Image(path3.toString()));
                cardOnMousePressed(blue3);
            }
            if(size > 4 && player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(4) != null) {
                StringBuilder path4 = new StringBuilder();
                path4.append("images/developmentCard/devcards_f_en_c_");
                path4.append(player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(4).getId());
                path4.append(".png");
                blue4.setImage(new Image(path4.toString()));
                cardOnMousePressed(blue4);
            }
            if(size > 5 && player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(5) != null) {
                StringBuilder path5 = new StringBuilder();
                path5.append("images/developmentCard/devcards_f_en_c_");
                path5.append(player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(5).getId());
                path5.append(".png");
                blue5.setImage(new Image(path5.toString()));
                cardOnMousePressed(blue5);
            }
        }
        if(!player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).isEmpty()){
            int size = player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).size();
            if(size > 0 && player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(0) != null) {
                StringBuilder path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(0).getId());
                path.append(".png");
                yellow0.setImage(new Image(path.toString()));
                cardOnMousePressed(yellow0);
            }
            if(size > 1 && player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(1) != null) {
                StringBuilder path1 = new StringBuilder();
                path1.append("images/developmentCard/devcards_f_en_c_");
                path1.append(player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(1).getId());
                path1.append(".png");
                yellow1.setImage(new Image(path1.toString()));
                cardOnMousePressed(yellow1);
            }
            if(size > 2 && player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(2) != null) {
                StringBuilder path2 = new StringBuilder();
                path2.append("images/developmentCard/devcards_f_en_c_");
                path2.append(player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(2).getId());
                path2.append(".png");
                yellow2.setImage(new Image(path2.toString()));
                cardOnMousePressed(yellow2);
            }
            if(size > 3 && player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(3) != null) {
                StringBuilder path3 = new StringBuilder();
                path3.append("images/developmentCard/devcards_f_en_c_");
                path3.append(player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(3).getId());
                path3.append(".png");
                yellow3.setImage(new Image(path3.toString()));
                cardOnMousePressed(yellow3);
            }
            if(size > 4  && player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(4) != null) {
                StringBuilder path4 = new StringBuilder();
                path4.append("images/developmentCard/devcards_f_en_c_");
                path4.append(player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(4).getId());
                path4.append(".png");
                yellow4.setImage(new Image(path4.toString()));
                cardOnMousePressed(yellow4);
            }
            if(size > 5 && player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(5) != null) {
                StringBuilder path5 = new StringBuilder();
                path5.append("images/developmentCard/devcards_f_en_c_");
                path5.append(player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(5).getId());
                path5.append(".png");
                yellow5.setImage(new Image(path5.toString()));
                cardOnMousePressed(yellow5);
            }
        }
        if(!player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).isEmpty()){
            int size = player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).size();
            if(size > 0  && player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(0) != null) {
                StringBuilder path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                path.append(player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(0).getId());
                path.append(".png");
                green0.setImage(new Image(path.toString()));
                cardOnMousePressed(green0);
            }
            if(size > 1 && player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(1) != null) {
                StringBuilder path1 = new StringBuilder();
                path1.append("images/developmentCard/devcards_f_en_c_");
                path1.append(player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(1).getId());
                path1.append(".png");
                green1.setImage(new Image(path1.toString()));
                cardOnMousePressed(green1);
            }
            if(size > 2 && player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(2) != null) {
                StringBuilder path2 = new StringBuilder();
                path2.append("images/developmentCard/devcards_f_en_c_");
                path2.append(player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(2).getId());
                path2.append(".png");
                green2.setImage(new Image(path2.toString()));
                cardOnMousePressed(green2);
            }
            if(size > 3 && player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(3) != null) {
                StringBuilder path3 = new StringBuilder();
                path3.append("images/developmentCard/devcards_f_en_c_");
                path3.append(player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(3).getId());
                path3.append(".png");
                green3.setImage(new Image(path3.toString()));
                cardOnMousePressed(green3);
            }
            if(size > 4 && player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(4) != null) {
                StringBuilder path4 = new StringBuilder();
                path4.append("images/developmentCard/devcards_f_en_c_");
                path4.append(player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(4).getId());
                path4.append(".png");
                green4.setImage(new Image(path4.toString()));
                cardOnMousePressed(green4);
            }
            if(size > 5 && player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(5) != null) {
                StringBuilder path5 = new StringBuilder();
                path5.append("images/developmentCard/devcards_f_en_c_");
                path5.append(player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(5).getId());
                path5.append(".png");
                green5.setImage(new Image(path5.toString()));
                cardOnMousePressed(green5);
            }
        }
    }
}
