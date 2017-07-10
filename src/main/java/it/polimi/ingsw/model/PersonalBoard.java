package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.LELorenzoDeMedici;
import it.polimi.ingsw.utility.Configuration;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents the personal board abstraction.
 */
public class PersonalBoard implements Serializable {

    private static final int MAX_NUMBER_OF_CARD_PER_TYPE = 6;

    /**
     * Contains all values of points and resources.
     */
    private PointsAndResources valuables;

    /**
     * bonus: save the bonus dice values for harvest/production
     */
    private Map<ActionType, Integer> harvestProductionDiceValueBonus = new EnumMap<>(ActionType.class);

    /**
     * bonus: save the bonus dice values for cards
     */
    private Map<DevelopmentCardColor, Integer> developmentCardColorDiceValueBonus = new EnumMap<>(DevelopmentCardColor.class);

    /**
     * discounts: save the discount cost for development cards
     */
    private Map<DevelopmentCardColor, List<PointsAndResources>> costDiscountForDevelopmentCard = new EnumMap<>(DevelopmentCardColor.class);

    /**
     * Family members;
     */
    private FamilyMember familyMember;

    /**
     * Array of family members already used by the player
     */
    private ArrayList<FamilyMemberColor> familyMembersUsed;

    /**
     * Military points required to pick up a green card and place it in a specific position of the territory card array.
     */
    private int[] greenCardsMilitaryPointsRequirements;

    /**
     * Flag to check if the player can always place family members even in occupied spaces
     */
    private boolean alwaysPlaceFamilyMemberInsideActionSpace = false;

    /**
     * Array of cards, divided per types.
     */
    private ArrayList<DevelopmentCard> territoryCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> buildingCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> characterCards = new ArrayList<>();
    private ArrayList<DevelopmentCard> ventureCards = new ArrayList<>();
    private ArrayList<LeaderCard> leaderCards = new ArrayList<>();
    private ArrayList<ExcommunicationCard> excommunicationCards = new ArrayList<>();

    /**
     * keeps track of all the excommunication malus the player has
     */
    private ExcommunicationValues excommunicationValues;

    /**
     * Personal board tile choosen by the player.
     */
    private PersonalBoardTile personalBoardTile;

    public PersonalBoard() {
        this.familyMember = new FamilyMember();
        this.valuables = new PointsAndResources();
        for (ActionType type : ActionType.values())
            this.harvestProductionDiceValueBonus.put(type, 0);

        for (DevelopmentCardColor color : DevelopmentCardColor.values()) {
            List<PointsAndResources> discounts = new ArrayList<>();
            discounts.add(new PointsAndResources());
            this.costDiscountForDevelopmentCard.put(color, discounts);
            this.developmentCardColorDiceValueBonus.put(color, 0);
        }

        this.familyMembersUsed = new ArrayList<>();
        this.leaderCards = new ArrayList<>();
        this.excommunicationCards = new ArrayList<>();
        this.excommunicationValues = new ExcommunicationValues();
    }

    /**
     * Get the max number of cards per type you can add to the player board
     *
     * @return
     */
    public int getMaxNumberOfCardPerType() {
        return MAX_NUMBER_OF_CARD_PER_TYPE;
    }

    /**
     * Set personal board tile.
     *
     * @param personalBoardTile to set.
     */
    public void setPersonalBoardTile(PersonalBoardTile personalBoardTile) {
        this.personalBoardTile = personalBoardTile;
    }

    /**
     * Get personal board tile.
     *
     * @return personal board tile.
     */
    public PersonalBoardTile getPersonalBoardTile() {
        return this.personalBoardTile;
    }

    /**
     * Set family member
     *
     * @param member to be set.
     */
    public void setFamilyMember(FamilyMember member) {
        this.familyMember = member;
    }

    /**
     * Get family member
     *
     * @return
     */
    public FamilyMember getFamilyMember() {
        return this.familyMember;
    }

    /**
     * Set the array of family members already used by the player
     *
     * @param familyMember
     */
    public void setFamilyMembersUsed(FamilyMemberColor familyMember) {
        this.familyMembersUsed.add(familyMember);
    }

    /**
     * Get the array of family members already used by the player
     *
     * @return
     */
    public List<FamilyMemberColor> getFamilyMembersUsed() {
        return this.familyMembersUsed;
    }

    /**
     * Return true if a family member is already used.
     *
     * @param familyMemberColor to check.
     * @return boolean.
     */
    public boolean familyMemberIsUsed(FamilyMemberColor familyMemberColor) {
        for (FamilyMemberColor color : familyMembersUsed)
            if (familyMemberColor.equals(color))
                return true;
        return false;
    }

    /**
     * Set military points needed to place a card in a specific position.
     *
     * @param array
     */
    public void setGreenCardsMilitaryPointsRequirements(int[] array) {
        this.greenCardsMilitaryPointsRequirements = new int[array.length];
        for(int i = 0; i < greenCardsMilitaryPointsRequirements.length; i++)
            this.greenCardsMilitaryPointsRequirements[i] = array[i];
    }

    /**
     * Get military points needed to place a card in a specific position.
     *
     * @param index of position.
     * @return military points needed.
     */
    public int getGreenCardsMilitaryPointsRequirements(int index) {
        return this.greenCardsMilitaryPointsRequirements[index];
    }

    public int[] getGreenCardsMilitaryPointsRequirements() {
        return this.greenCardsMilitaryPointsRequirements;
    }

    /**
     * Add a new card to the player's personal board
     *
     * @param card
     */
    public void addCard(DevelopmentCard card) {
        switch (card.getColor()) {
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
     *
     * @param developmentCardColor
     * @return
     */
    public List<DevelopmentCard> getCards(DevelopmentCardColor developmentCardColor) {
        switch (developmentCardColor) {
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
        return Collections.emptyList();
    }


    /**
     * Set points and resources
     *
     * @param pointsAndResources
     */
    public void setValuables(PointsAndResources pointsAndResources) {
        this.valuables = pointsAndResources;
    }


    /**
     * Get points and resources
     */
    public PointsAndResources getValuables() {
        return this.valuables;
    }


    /**
     * Set the dice bonus value for harvest and production zones
     */
    public void setHarvestProductionDiceValueBonus(ActionType type, Integer value) {
        this.harvestProductionDiceValueBonus.put(type, this.harvestProductionDiceValueBonus.get(type) + value);
    }

    /**
     * Get the dice bonus value for harvest and production zones
     * @return
     */
    public Map<ActionType, Integer> getHarvestProductionDiceValueBonus() {
        return this.harvestProductionDiceValueBonus;
    }

    /**
     * Set the dice development card bonus value based on card's color
     *
     * @param cardColor
     * @param value
     */
    public void setDevelopmentCardColorDiceValueBonus(DevelopmentCardColor cardColor, Integer value) {
        this.developmentCardColorDiceValueBonus.put(cardColor, this.developmentCardColorDiceValueBonus.get(cardColor) + value);
    }

    /**
     * Get the dice development card bonus value based on card's color
     *
     * @return
     */
    public Map<DevelopmentCardColor, Integer> getDevelopmentCardColorDiceValueBonus() {
        return this.developmentCardColorDiceValueBonus;
    }

    /**
     * Set the cost discount value for a particular type of development cards
     *
     * @param cardColor
     * @param valuables
     */
    public void setCostDiscountForDevelopmentCard(DevelopmentCardColor cardColor, List<PointsAndResources> valuables) {
        List<PointsAndResources> discounts = this.costDiscountForDevelopmentCard.get(cardColor);
        List<PointsAndResources> newDiscounts = new ArrayList<>();
        for (PointsAndResources oldDiscounts : discounts) {
            for (PointsAndResources discountToAdd : valuables) {
                PointsAndResources newDiscount = oldDiscounts;
                for (ResourceType resourceType : ResourceType.values())
                    newDiscount.increase(resourceType, discountToAdd.getResources().get(resourceType));
                for (PointType pointType : PointType.values())
                    newDiscount.increase(pointType, discountToAdd.getPoints().get(pointType));
                newDiscounts.add(newDiscount);
            }
        }
        this.costDiscountForDevelopmentCard.put(cardColor, newDiscounts);
    }

    /**
     * Get the cost discount value for a particular type of development cards
     *
     * @return
     */
    public List<PointsAndResources> getCostDiscountForDevelopmentCard(DevelopmentCardColor color) {
        return this.costDiscountForDevelopmentCard.get(color);
    }

    /**
     * This method returns the array of leader cards
     *
     * @return
     */
    public List<LeaderCard> getLeaderCards() {
        return this.leaderCards;
    }

    /**
     * This method checks if the player has a particular leader card per name and returns it
     *
     * @param name
     * @return
     */
    public LeaderCard getLeaderCardWithName(String name) {
        for (LeaderCard card : this.leaderCards) {
            if (card.getLeaderCardName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    /**
     * This method sets a particular leadercard
     *
     * @param leaderCard
     */
    public void setLeaderCard(LeaderCard leaderCard) {
        this.leaderCards.add(leaderCard);
    }


    /**
     * This method add a new excommunication to the player
     */
    public void addExcommunicationCard(ExcommunicationCard card) {
        this.excommunicationCards.add(card);
    }

    /**
     * This method returns the excommunication cards of the player
     */
    public List<ExcommunicationCard> getExcommunicationCards() {
        return this.excommunicationCards;
    }


    /**
     * Get excommunication values
     */
    public ExcommunicationValues getExcommunicationValues() {
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

    public void turnReset(Configuration configuration) {
        this.familyMembersUsed = new ArrayList<>();
        this.familyMember = new FamilyMember();
        for(LeaderCard leaderCard : leaderCards) {
            if (leaderCard.getLeaderCardName().equalsIgnoreCase("lorenzo il magnifico")
                    && leaderCard.getLeaderEffectActive() && leaderCard.getEffect() instanceof LELorenzoDeMedici) {
                LELorenzoDeMedici effect = (LELorenzoDeMedici)leaderCard.getEffect();
                LeaderCard leaderCardToDelete = effect.getLeaderCard();
                for(int j = 0; j < leaderCards.size(); j++)
                    if(leaderCards.get(j).getLeaderCardName().equals(leaderCardToDelete.getLeaderCardName()))
                        leaderCards.remove(j);
            }
            leaderCard.setLeaderEffectActive(false);
        }
        this.getFamilyMember().setFamilyMemberValue(FamilyMemberColor.NEUTRAL, 0);
        this.setGreenCardsMilitaryPointsRequirements(configuration.getPersonalBoard().getGreenCardsMilitaryPointsRequirements());
        this.alwaysPlaceFamilyMemberInsideActionSpace = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n-> FAMILY MEMBERS AVAILABLE\n");
        for(Map.Entry pair : familyMember.getMembers().entrySet())
            if (!familyMemberIsUsed((FamilyMemberColor) pair.getKey()))
                stringBuilder.append(pair.getKey().toString().toLowerCase() + "=" + pair.getValue() + " ");

        stringBuilder.append("\n\n-> RESOURCES AVAILABLE\n" + valuables + "\n");

        stringBuilder.append("\n-> BONUS AVAILABLE\n");
        for(Map.Entry pair : harvestProductionDiceValueBonus.entrySet())
            if((int) pair.getValue() != 0)
                stringBuilder.append(pair.getKey().toString() + "=" + pair.getValue() + "\n");
        for(Map.Entry pair : developmentCardColorDiceValueBonus.entrySet())
            if((int) pair.getValue() != 0)
                stringBuilder.append(pair.getKey().toString() + "=" + pair.getValue() + "\n");
        for(Map.Entry pair : costDiscountForDevelopmentCard.entrySet())
            if (!((ArrayList) pair.getValue()).isEmpty())
                for(PointsAndResources valuable : (ArrayList<PointsAndResources>)pair.getValue())
                    if(!valuable.toString().equals(""))
                        stringBuilder.append(valuable.toString() + "\n");

        stringBuilder.append("\n-> TERRITORY CARDS\n");
        for (DevelopmentCard card : territoryCards)
            stringBuilder.append(card.toString());
        stringBuilder.append("\n-> BUILDING CARDS\n");
        for (DevelopmentCard card : buildingCards)
            stringBuilder.append(card.toString());
        stringBuilder.append("\n-> CHARACTERS CARDS\n");
        for (DevelopmentCard card : characterCards)
            stringBuilder.append(card.toString());
        stringBuilder.append("\n-> VENTURE CARDS\n");
        for (DevelopmentCard card : ventureCards)
            stringBuilder.append(card.toString());

        stringBuilder.append("\n-> EXCOMMUNICATIONS (active)\n");
        if (!excommunicationCards.isEmpty())
            for (ExcommunicationCard card : excommunicationCards)
                stringBuilder.append(card.toString());

        stringBuilder.append("\n-> LEADER CARDS\n");
        if (!leaderCards.isEmpty())
            for (LeaderCard card : leaderCards)
                stringBuilder.append(card.toString() + "\n");
        return stringBuilder.toString();
    }

    public String toStringSmall(){
        StringBuilder stringBuilder = new StringBuilder("\n-> RESOURCES AVAILABLE\n" + valuables + "\n");

        stringBuilder.append("\n-> TERRITORY CARDS: ");
        for (DevelopmentCard card : territoryCards)
            stringBuilder.append(card.getName() + ", ");
        stringBuilder.append("\n-> BUILDING CARDS: ");
        for (DevelopmentCard card : buildingCards)
            stringBuilder.append(card.getName() + ", ");
        stringBuilder.append("\n-> CHARACTERS CARDS: ");
        for (DevelopmentCard card : characterCards)
            stringBuilder.append(card.getName() + ", ");
        stringBuilder.append("\n-> VENTURE CARDS: ");
        for (DevelopmentCard card : ventureCards)
            stringBuilder.append(card.getName() + ", ");

        stringBuilder.append("\n-> EXCOMMUNICATIONS (active)\n");
        if (!excommunicationCards.isEmpty())
            for (ExcommunicationCard card : excommunicationCards)
                stringBuilder.append(card.toString());

        stringBuilder.append("\n-> LEADER CARDS (active): ");
        if (!leaderCards.isEmpty())
            for (LeaderCard leaderCard : leaderCards)
                if (leaderCard.getLeaderEffectActive()) stringBuilder.append(leaderCard.getLeaderCardName() + ", ");
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}
