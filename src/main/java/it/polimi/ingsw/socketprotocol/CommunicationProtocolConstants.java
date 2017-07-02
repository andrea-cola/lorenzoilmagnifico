package it.polimi.ingsw.socketprotocol;

/**
 * This class is used to define all constants needed for communication protocol.
 */
/*package-local*/ class CommunicationProtocolConstants {

    /**
     * Requests from client to server.
     */
    /*package-local*/ static final String LOGIN_REQUEST = "LoginRequest";
    /*package-local*/ static final String SIGNIN_REQUEST = "SigninRequest";
    /*package-local*/ static final String JOIN_ROOM_REQUEST = "JoinRoomRequest";
    /*package-local*/ static final String CREATE_ROOM_REQUEST = "CreateRoomRequest";
    /*package-local*/ static final String FAMILIAR_IN_TOWER = "FamilyMemberInTower";
    /*package-local*/ static final String FAMILIAR_IN_COUNCIL = "FamilyMemberInCouncil";
    /*package-local*/ static final String FAMILIAR_IN_MARKET = "FamilyMemberInMarket";
    /*package-local*/ static final String FAMILIAR_IN_PRODUCTION_SIMPLE = "FamilyMemberInPS";
    /*package-local*/ static final String FAMILIAR_IN_PRODUCTION_EXTENDED = "FamilyMemberInPE";
    /*package-local*/ static final String FAMILIAR_IN_HARVEST_SIMPLE = "FamilyMemberInHS";
    /*package-local*/ static final String FAMILIAR_IN_HARVEST_EXTENDED = "FamilyMemberInHE";
    /*package-local*/ static final String ACTIVATE_LEADER_CARD = "activateLeaderCard";
    /*package-local*/ static final String DISCARD_LEADER_CARD = "discardLeaderCard";
    /*package-local*/ static final String SUPPORT_FOR_THE_CHURCH_CHOICE = "supportForTheChurchChoice";
    /*package-local*/ static final String END_TURN = "endTurn";

    /**
     * Responses from server to client.
     */
    /*package-local*/ static final int USER_LOGIN_SIGNIN_OK = 1;
    /*package-local*/ static final int USER_LOGIN_WRONG_PASSWORD = 2;
    /*package-local*/ static final int USER_ALREADY_LOGGEDIN = 3;
    /*package-local*/ static final int USER_NOT_EXISTS = 4;
    /*package-local*/ static final int USER_ALREADY_EXISTS = 5;
    /*package-local*/ static final int USER_FAIL_GENERIC = 6;
    /*package-local*/ static final int ROOM_JOINED = 7;
    /*package-local*/ static final int NO_ROOM_AVAILABLE = 8;


    /**
     * Requests from server to client.
     */
    /*package-local*/ static final String GAME_MODEL = "gameModel";
    /*package-local*/ static final String PERSONAL_TILES = "personalTiles";
    /*package-local*/ static final String LEADER_CARDS = "leaderCards";
    /*package-local*/ static final String TURN_STARTED = "turnStarted";
    /*package-local*/ static final String MODEL_UPDATE = "modelUpdate";
    /*package-local*/ static final String SUPPORT_FOR_THE_CHURCH = "supportForTheChurch";

}