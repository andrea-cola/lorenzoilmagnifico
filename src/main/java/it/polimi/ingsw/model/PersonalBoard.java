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
     * bonus: save the bonus dice values for harvest, production, cards
     */
    private Map<ActionType, Integer> harvestProductionDiceValueBonus = new HashMap<>();
    private Map<DevelopmentCardColor, Integer> developmentCardColorDiceValueBonus = new HashMap<>();

    /**
     * discounts: save the discount cost for development cards
     */
    private Map<DevelopmentCardColor, PointsAndResources> costDiscountForDevelopmentCard = new HashMap<>();

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

    public void setPersonalBoardTile(PersonalBoardTile personalBoardTile){
        this.personalBoardTile = personalBoardTile;
    }

    public PersonalBoardTile getPersonalBoardTile(){
        return this.personalBoardTile;
    }

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


    public void addCard(DevelopmentCard card){
        switch (card.getColor()){
            case GREEN:
                this.territoryCards.add(card);
                break;
            case YELLOW:
                this.buildingCards.add(card);
                break;
            case BLUE:
                this.characterCards.add(card);
                break;
            case PURPLE:
                this.ventureCards.add(card);
                break;
        }
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
     * Add territory card
     * @param card
     */
    public void addTerritoryCard(DevelopmentCard card){
        this.territoryCards.add(card);
    }

    /**
     * Get a specific territory card from the array.
     * @return a territory card.
     */
    public ArrayList<DevelopmentCard> getTerritoryCards(){
        return this.territoryCards;
    }

    /**
     * Add building card
     * @param card
     */
    public void addBuildingCard(DevelopmentCard card){
        this.buildingCards.add(card);
    }

    /**
     * Get a specific building card from the array.
     * @return a building card.
     */
    public ArrayList<DevelopmentCard> getBuildingCards(){
        return this.buildingCards;
    }

    /**
     * Add character card
     * @param card
     */
    public void addCharacterCard(DevelopmentCard card){
        this.characterCards.add(card);
    }

    /**
     * Get a specific character card from the array.
     * @return a character card.
     */
    public ArrayList<DevelopmentCard> getCharacterCards(){
        return this.characterCards;
    }

    /**
     * Add venture card
     * @param card
     */
    public void addVentureCard(DevelopmentCard card){
        this.ventureCards.add(card);
    }

    /**
     * Get a specific venture card from the array.
     * @return a venture card.
     */
    public ArrayList<DevelopmentCard> getVentureCards(){
        return this.ventureCards;
    }


    /**
     * Set the dice bonus value for harvest and production zones
     */
    public void setHarvestProductionDiceValueBonus(ActionType type, Integer value){
        this.harvestProductionDiceValueBonus.put(type, this.harvestProductionDiceValueBonus.get(type) + value);
    }

    /**
     * Get the dice bonus value for harvest and production zones
     * @return
     */
    public Map<ActionType, Integer> getHarvestProductionDiceValueBonus(){
        return this.harvestProductionDiceValueBonus;
    }

    /**
     * Set the dice development card bonus value based on card's color
     * @param cardColor
     * @param value
     */
    public void setDevelopmentCardColorDiceValueBonus(DevelopmentCardColor cardColor, Integer value){
        this.developmentCardColorDiceValueBonus.put(cardColor, this.developmentCardColorDiceValueBonus.get(cardColor) + value);
    }

    /**
     * Get the dice development card bonus value based on card's color
     * @return
     */
    public Map<DevelopmentCardColor, Integer> getDevelopmentCardColorDiceValueBonus(){
        return this.developmentCardColorDiceValueBonus;
    }

    /**
     * Set the cost discount value for a particular type of development cards
     * @param cardColor
     * @param valuables
     */
    public void setCostDiscountForDevelopmentCard(DevelopmentCardColor cardColor, PointsAndResources valuables){
        this.costDiscountForDevelopmentCard.put(cardColor, valuables);
    }

    /**
     * Get the cost discount value for a particular type of development cards
     * @return
     */
    public Map<DevelopmentCardColor, PointsAndResources> getCostDiscountForDevelopmentCard(){
        return this.costDiscountForDevelopmentCard;
    }
}
