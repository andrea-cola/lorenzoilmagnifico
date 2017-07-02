package it.polimi.ingsw.exceptions;

/**
 * List of all game errors.
 */
public enum GameErrorType {

    FAMILY_MEMBER_ALREADY_PLACED("The player has already placed a family member in this area"),
    FAMILY_MEMBER_DICE_VALUE("Family member value is not enough"),
    FAMILY_MEMBER_ALREADY_USED("This family member has been already used"),
    MILITARY_POINTS_REQUIRED("The player has not military points enough to get this card"),
    PERSONAL_BOARD_MAX_CARD_LIMIT_REACHED("The player has reached the maximum amount for this type of development card"),
    PLAYER_POINTS_ERROR("Player points are not enough"),
    PLAYER_RESOURCES_ERROR("Player resources are not enough"),
    PLAYER_CARDS_ERROR("Player cards are not enough"),
    LEADER_CARD_ALREADY_USED("This card has been already used"),
    GENERIC_ERROR("Generic error"),
    NOT_ENOUGH_RESOURCES("You don't have enough amount of resources to do this move. Please retry."),
    NOT_ENOUGH_POINTS("You don't have enough amount of points to do this move. Please retry."),
    EXCOMMUNICATION_EFFECT_MARKET("You can't place a family member inside the market because of the excommunication effect");

    /**
     * Enumeration message.
     */
    private final String error;

    /**
     * Enumeration constructor.
     * @param error error message.
     */
    GameErrorType(String error) {
        this.error = error;
    }

    /**
     * Give back the error message.
     * @return error message.
     */
    @Override
    public String toString(){
        return this.error;
    }


}
