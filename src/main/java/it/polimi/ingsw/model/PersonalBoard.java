package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lorenzo on 23/05/17.
 */
public class PersonalBoard {

    private PointsAndResources valuables;

    private ArrayList<DevelopmentCard> territoryCards;

    private ArrayList<DevelopmentCard> buildingCards;

    private ArrayList<DevelopmentCard> characterCards;

    private ArrayList<DevelopmentCard> ventureCards;

    public PersonalBoard(){
        this.valuables = new PointsAndResources();
    }

    public boolean isFull(){
        return true;
    }


    public PointsAndResources getValuables(){
        return this.valuables;
    }

    public void setTerritoryCards(int atIndex, DevelopmentCard withCard){
        this.territoryCards.set(atIndex, withCard);
    }

    public DevelopmentCard getTerritoryCard(int atIndex){
        return this.territoryCards.get(atIndex);
    }

    public void setBuildingCards(int atIndex, DevelopmentCard withCard){
        this.buildingCards.set(atIndex, withCard);
    }



    public DevelopmentCard getBuildingCard(int atIndex){
        return this.buildingCards.get(atIndex);
    }

    public void setCharacterCards(int atIndex, DevelopmentCard withCard){
        this.characterCards.set(atIndex, withCard);
    }

    public DevelopmentCard getCharacterCard(int atIndex){
        return  this.characterCards.get(atIndex);
    }

    public void setVentureCards(int atIndex, DevelopmentCard withCard){
        this.ventureCards.set(atIndex, withCard);
    }

    public DevelopmentCard getVentureCard(int atIndex){
        return this.ventureCards.get(atIndex);
    }
}
