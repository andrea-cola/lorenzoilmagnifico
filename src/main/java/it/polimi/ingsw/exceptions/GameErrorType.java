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
    PLAYER_RESOURCES_ERROR("Player resources are not enough");

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
    public String toString(){
        return this.error;
    }


}
