package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the personal board abstraction.
 */
public class PersonalBoard implements Serializable{

    private static final int MAX_NUMBER_OF_CARD_PER_TYPE = 6;

    /**
     * Contains all values of points and resources.
     */
    private PointsAndResources valuables;

    /**
     * Military points required to pick up a green card and place it in a specific position of the territory card array.
     */
    private static int[] greenCardsMilitaryPointsRequirements = new int[MAX_NUMBER_OF_CARD_PER_TYPE];

    /**
     * bonus: save the bonus dice values for harvest/production
     */
    private Map<ActionType, Integer> harvestProductionDiceValueBonus = new HashMap<>();

    /**
     * bonus: save the bonus dice values for cards
     */
    private Map<DevelopmentCardColor, Integer> developmentCardColorDiceValueBonus = new HashMap<>();

    /**
     * discounts: save the discount cost for development cards
     */
    private Map<DevelopmentCardColor, PointsAndResources> costDiscountForDevelopmentCard = new HashMap<>();

    /**
     * FamilyMembers;
     */
    private FamilyMember familyMember;

    /**
     * Array of family members already used by the player
     */
    private ArrayList<FamilyMemberColor> familyMembersUsed;

    /**
     * Array of cards, divided per types.
     */
    private ArrayList<DevelopmentCard> territoryCards;
    private ArrayList<DevelopmentCard> buildingCards;
    private ArrayList<DevelopmentCard> characterCards;
    private ArrayList<DevelopmentCard> ventureCards;

    /**
     * Personal board tile choosen by the player.
     */
    private PersonalBoardTile personalBoardTile;

    /**
     * Array of leader cards
     */
    private ArrayList<LeaderCard> leaderCards;

    public void setPersonalBoardTile(PersonalBoardTile personalBoardTile){
        this.personalBoardTile = personalBoardTile;
    }

    public PersonalBoardTile getPersonalBoardTile() {
        return this.personalBoardTile;
    }

    public PersonalBoard(){

        this.valuables = new PointsAndResources();

        for(ActionType type : ActionType.values())
            this.harvestProductionDiceValueBonus.put(type, 0);

        for(DevelopmentCardColor color : DevelopmentCardColor.values()) {
            this.costDiscountForDevelopmentCard.put(color, new PointsAndResources());
            this.developmentCardColorDiceValueBonus.put(color, 0);
        }

        this.territoryCards = new ArrayList<>();
        this.buildingCards = new ArrayList<>();
        this.characterCards = new ArrayList<>();
        this.ventureCards = new ArrayList<>();

        this.personalBoardTile = new PersonalBoardTile();
        this.familyMember = new FamilyMember();
        this.familyMembersUsed = new ArrayList<>();
        this.leaderCards = new ArrayList<>();

        //configure green cards military points requirements
        for (int i = 0; i < MAX_NUMBER_OF_CARD_PER_TYPE; i++){
            switch (i){
                case 0:
                case 1:
                    greenCardsMilitaryPointsRequirements[i] = 0;
                    break;
                case 2:
                    greenCardsMilitaryPointsRequirements[i] = 3;
                    break;
                case 3:
                    greenCardsMilitaryPointsRequirements[i] = 7;
                    break;
                case 4:
                    greenCardsMilitaryPointsRequirements[i] = 12;
                    break;
                case 5:
                    greenCardsMilitaryPointsRequirements[i] = 18;
                    break;
            }
        }
    }

    /**
     * Set family member
     * @param member
     */
    public void setFamilyMember(FamilyMember member){
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
     * Set the array of family members already used by the player
     * @param familyMember
     */
    public void setFamilyMembersUsed(FamilyMemberColor familyMember){
        this.familyMembersUsed.add(familyMember);
    }

    /**
     * Get the array of family members already used by the player
     * @return
     */
    public ArrayList<FamilyMemberColor> getFamilyMembersUsed(){
        return this.familyMembersUsed;
    }

    /**
     * Set military points needed to place a card in a specific position.
     */
    public void setGreenCardsMilitaryPointsRequirements(int atIndex, int withValue){
        greenCardsMilitaryPointsRequirements[atIndex] = withValue;
    }

    /**
     * Get military points needed to place a card in a specific position.
     * @param atIndex of position.
     * @return military points needed.
     */
    public int getGreenCardsMilitaryPointsRequirements(int atIndex){
        return greenCardsMilitaryPointsRequirements[atIndex];
    }

    /**
     * Get the max number of cards per type you can add to the player board
     * @return
     */
    public int getMaxNumberOfCardPerType(){
        return MAX_NUMBER_OF_CARD_PER_TYPE;
    }

    /**
     * Add a new card to the player's personal board
     * @param card
     */
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

    public ArrayList<DevelopmentCard> getCards(DevelopmentCardColor developmentCardColor){
        switch (developmentCardColor){
            case GREEN:
                return this.territoryCards;
            case YELLOW:
                return this.buildingCards;
            case PURPLE:
                return this.ventureCards;
            case BLUE:
                return this.characterCards;
            default:
                break;
        }
        return null;
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
