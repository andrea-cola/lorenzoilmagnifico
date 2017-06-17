package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the personal board abstraction.
 */
public class PersonalBoard implements Serializable{

    private static final int MAX_NUMER_OF_CARD_PER_TYPE = 6;

    /**
     * Contains all values of points and resources.
     */
    private PointsAndResources valuables;

    /**
     * bonus
     */
    private Map<ActionType, Integer> harvestProductionDiceValueBonus = new HashMap<>();
    private Map<DevelopmentCardColor, Integer> developmentCardColorDiceValueBonus = new HashMap<>();

    /**
     * Array of available familyMembers;
     */
    private FamilyMember familyMember;

    /**
     * Military points required to pick up a green card and place it in a specific position of the territory card array.
     */
    private static int[] greenCardsMilitaryPointsRequirements = new int[MAX_NUMER_OF_CARD_PER_TYPE];

    /**
     * Array of cards, divided per types.
     */
    private ArrayList<DevelopmentCard> territoryCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> buildingCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> characterCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> ventureCards = new ArrayList<>();

    /**
     * Personal board tile choosen by the player.
     */
    private PersonalBoardTile personalBoardTile;

    /**
     * Set family mamber
     * @param member
     */
    public void setFamilyMembers(FamilyMember member){
        this.familyMember = member;
    }

    /**
     * Get family member
     * @return
     */
    public FamilyMember getFamilyMember(){
        return this.familyMember;
    }


    /**
     * Set military points needed to place a card in a specific position.
     * @param array
     */
    public void setGreenCardsMilitaryPointsRequirements(int[] array){
        greenCardsMilitaryPointsRequirements = array;
    }

    /**
     * Get military points needed to place a card in a specific position.
     * @param index of position.
     * @return military points needed.
     */
    public int getGreenCardsMilitaryPointsRequirements(int index){
        return greenCardsMilitaryPointsRequirements[index];
    }

    /**
     * Set points and resources
     * @param pointsAndResources
     */
    public void setValuables(PointsAndResources pointsAndResources){
        this.valuables = pointsAndResources;
    }

    /**
     * Get points and resources.
     * @return points and resources.
     */
    public PointsAndResources getValuables(){
        return this.valuables;
    }

    /**
     * Set territory card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setTerritoryCards(int atIndex, DevelopmentCard withCard){
        this.territoryCards.set(atIndex, withCard);
    }

    /**
     * Get a specific territory card from the array.
     * @return a territory card.
     */
    public ArrayList<DevelopmentCard> getTerritoryCards(){
        return this.territoryCards;
    }

    /**
     * Set a building card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setBuildingCards(int atIndex, DevelopmentCard withCard){
        this.buildingCards.set(atIndex, withCard);
    }

    /**
     * Get a specific building card from the array.
     * @return a building card.
     */
    public ArrayList<DevelopmentCard> getBuildingCards(){
        return this.buildingCards;
    }

    /**
     * Set a character card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setCharacterCards(int atIndex, DevelopmentCard withCard){
        this.characterCards.set(atIndex, withCard);
    }

    /**
     * Get a specific character card from the array.
     * @return a character card.
     */
    public ArrayList<DevelopmentCard> getCharacterCards(){
        return this.characterCards;
    }

    /**
     * Set a venture card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setVentureCards(int atIndex, DevelopmentCard withCard){
        this.ventureCards.set(atIndex, withCard);
    }

    /**
     * Get a specific venture card from the array.
     * @return a venture card.
     */
    public ArrayList<DevelopmentCard> getVentureCards(){
        return this.ventureCards;
    }


    /**
     *
     */
    public void setHarvestProductionDiceValueBonus(ActionType type, Integer value){
        this.harvestProductionDiceValueBonus.put(type, this.harvestProductionDiceValueBonus.get(type) + value);
    }

    public Map<ActionType, Integer> getHarvestProductionDiceValueBonus(){
        return this.harvestProductionDiceValueBonus;
    }

    public void setDevelopmentCardColorDiceValueBonus(DevelopmentCardColor cardColor, Integer value){
        this.developmentCardColorDiceValueBonus.put(cardColor, value);
    }

    public Map<DevelopmentCardColor, Integer> getDevelopmentCardColorDiceValueBonus(){
        return this.developmentCardColorDiceValueBonus;
    }

}
