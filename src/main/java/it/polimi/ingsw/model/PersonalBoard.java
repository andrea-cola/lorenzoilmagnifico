package it.polimi.ingsw.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polimi.ingsw.exceptions.GameErrorType;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.Effect;

import java.io.Serializable;
import java.util.*;

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
    private Map<DevelopmentCardColor, List<PointsAndResources>> costDiscountForDevelopmentCard = new HashMap<>();

    /**
     * Family members;
     */
    private FamilyMember familyMember;

    /**
     * Array of family members already used by the player
     */
    private ArrayList<FamilyMemberColor> familyMembersUsed = new ArrayList<>();

    /**
     * Military points required to pick up a green card and place it in a specific position of the territory card array.
     */
    private int[] greenCardsMilitaryPointsRequirements;

    /**
     * Flag to check if the player can always place family members even in occupied spaces
     */
    private Boolean alwaysPlaceFamilyMemberInsideActionSpace = false;

    /**
     * Array of cards, divided per types.
     */
    private ArrayList<DevelopmentCard> territoryCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> buildingCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> characterCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> ventureCards = new ArrayList<>();
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<ExcommunicationCard> excommunicationCards;

    /**
     * keeps track of all the excommunication malus the player has
     */
    private ExcommunicationValues excommunicationValues = new ExcommunicationValues();

    /**
     * Personal board tile choosen by the player.
     */
    private PersonalBoardTile personalBoardTile;

    public PersonalBoard(){
        this.valuables = new PointsAndResources();
        for(ActionType type : ActionType.values())
            this.harvestProductionDiceValueBonus.put(type, 0);

        for(DevelopmentCardColor color : DevelopmentCardColor.values()) {
            List<PointsAndResources> discounts = new ArrayList<>();
            discounts.add(new PointsAndResources());
            this.costDiscountForDevelopmentCard.put(color, discounts);
            this.developmentCardColorDiceValueBonus.put(color, 0);
        }
        this.familyMembersUsed = new ArrayList<>();
        this.leaderCards = new ArrayList<>();
        this.excommunicationCards = new ArrayList<>();
    }

    /**
     * Get the max number of cards per type you can add to the player board
     * @return
     */
    public int getMaxNumberOfCardPerType(){
        return MAX_NUMBER_OF_CARD_PER_TYPE;
    }

    /**
     * Set personal board tile.
     * @param personalBoardTile to set.
     */
    public void setPersonalBoardTile(PersonalBoardTile personalBoardTile){
        this.personalBoardTile = personalBoardTile;
    }

    /**
     * Get personal board tile.
     * @return personal board tile.
     */
    public PersonalBoardTile getPersonalBoardTile() {
        return this.personalBoardTile;
    }

    /**
     * Set family member
     * @param member to be set.
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
     * Return true if a family member is already used.
     * @param familyMemberColor to check.
     * @return boolean.
     */
    public boolean familyMemberIsUsed(FamilyMemberColor familyMemberColor){
        for(FamilyMemberColor color : familyMembersUsed)
            if(familyMemberColor.equals(color))
                return true;
        return false;
    }

    /**
     * Set military points needed to place a card in a specific position.
     * @param array
     */
    public void setGreenCardsMilitaryPointsRequirements(int[] array){
        this.greenCardsMilitaryPointsRequirements = array;
    }

    /**
     * Get military points needed to place a card in a specific position.
     * @param index of position.
     * @return military points needed.
     */
    public int getGreenCardsMilitaryPointsRequirements(int index){
        return this.greenCardsMilitaryPointsRequirements[index];
    }

    public int[] getGreenCardsMilitaryPointsRequirements(){
        return this.greenCardsMilitaryPointsRequirements;
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

    /**
     * Returns the cards owned by the player by color
     * @param developmentCardColor
     * @return
     */
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
     * Get points and resources
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
    public void setCostDiscountForDevelopmentCard(DevelopmentCardColor cardColor, List<PointsAndResources> valuables){
        List<PointsAndResources> discounts = this.costDiscountForDevelopmentCard.get(cardColor);
        List<PointsAndResources> newDiscounts = new ArrayList<>();
        for(PointsAndResources oldDiscounts : discounts){
            for(PointsAndResources discountToAdd: valuables){
                PointsAndResources newDiscount = oldDiscounts;
                for(ResourceType resourceType : ResourceType.values())
                    newDiscount.increase(resourceType, discountToAdd.getResources().get(resourceType));
                for(PointType pointType : PointType.values())
                    newDiscount.increase(pointType, discountToAdd.getPoints().get(pointType));
                newDiscounts.add(newDiscount);
            }
        }
        this.costDiscountForDevelopmentCard.put(cardColor, newDiscounts);
    }

    /**
     * Get the cost discount value for a particular type of development cards
     * @return
     */
    public List<PointsAndResources> getCostDiscountForDevelopmentCard(DevelopmentCardColor color){
        return this.costDiscountForDevelopmentCard.get(color);
    }

    /**
     * This method returns the array of leader cards
     * @return
     */
    public List<LeaderCard> getLeaderCards(){
        return this.leaderCards;
    }

    /**
     * This method checks if the player has a particular leader card per name and returns it
     * @param name
     * @return
     */
    public LeaderCard getLeaderCardWithName(String name){
        for (LeaderCard card : this.leaderCards){
            if (card.getLeaderCardName().equals(name)){
                return card;
            }
        }
        return null;
    }

    /**
     * This method sets a particular leadercard
     * @param leaderCard
     */
    public void setLeaderCard(LeaderCard leaderCard){
        this.leaderCards.add(leaderCard);
    }


    /**
     * This method add a new excommunication to the player
     */
    public void addExcommunicationCard(ExcommunicationCard card){
        this.excommunicationCards.add(card);
    }

    /**
     * This method returns the excommunication cards of the player
     */
    public ArrayList<ExcommunicationCard> getExcommunivationCards(){
        return this.excommunicationCards;
    }


    /**
     * Get excommunication values
     */
    public ExcommunicationValues getExcommunicationValues(){
        return this.excommunicationValues;
    }


    /**
     * Set if the user can always place a family member inside action spaces
     */
    public void setAlwaysPlaceFamilyMemberInsideActionSpace(Boolean updatedValue){
        this.alwaysPlaceFamilyMemberInsideActionSpace = updatedValue;
    }

    /**
     * Get if the user can always place a family member inside action spaces
     */
    public Boolean getAlwaysPlaceFamilyMemberInsideActionSpace(){
        return this.alwaysPlaceFamilyMemberInsideActionSpace;
    }

    /**
     * Reset dice values for family members.
     * Reset list of family members used.
     */
    public void turnReset(){
        this.familyMembersUsed = new ArrayList<>();
        this.familyMember = new FamilyMember();
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RESOURCES\n");
        stringBuilder.append(valuables.toString());

        stringBuilder.append("FAMILY MEMBERS AVAILABLE\n");
        Iterator it = familyMember.getMembers().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(!familyMemberIsUsed((FamilyMemberColor)pair.getKey()))
                stringBuilder.append(pair.getKey().toString() + " = " + pair.getValue() + "\n");
        }

        stringBuilder.append("<TERRITORY CARDS>\n");
        for(DevelopmentCard card : territoryCards)
            stringBuilder.append(card.toString());
        stringBuilder.append("<BUILDING CARDS>\n");
        for(DevelopmentCard card : buildingCards)
            stringBuilder.append(card.toString());
        stringBuilder.append("<CHARACTERS CARDS>\n");
        for(DevelopmentCard card : characterCards)
            stringBuilder.append(card.toString());
        stringBuilder.append("<VENTURE CARDS>\n");
        for(DevelopmentCard card : ventureCards)
            stringBuilder.append(card.toString());

        return stringBuilder.toString();
    }


}
