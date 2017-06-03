package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class TowerCell {

    private DevelopmentCard developmentCard;

    private Effect towerCellImmediateEffect;

    private Integer minFamilyMemberValue;

    private DevelopmentCardColor towerCellColor;

    private boolean empty = true;

    public TowerCell(DevelopmentCardColor color){
        this.towerCellColor = color;
    }

    public boolean isEmpty(){
        return true;
    }

    public DevelopmentCard getDevelopmentCard(){
        return this.developmentCard;
    }

    public void setDevelopmentCard(DevelopmentCard card){
        this.developmentCard = card;
    }

    public void setMinFamilyMemberValue(Integer value){
        this.minFamilyMemberValue = value;
    }

    public Integer getMinFamilyMemberValue(){
        return this.minFamilyMemberValue;
    }

    public DevelopmentCardColor getTowerCellColor(){
        return this.towerCellColor;
    }

}
