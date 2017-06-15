package it.polimi.ingsw.socketCommunicationProtocol;

/**
 * This class is used to define all constants needed for communication protocol.
 */
/*package-local*/ class CommunicationProtocolConstants {

    /**
     * Requests from client to server.
     */
    /*package-local*/ static final String LOGIN_REQUEST = "LoginRequest";
    /*package-local*/ static final String SIGNIN_REQUEST = "SigninRequest";
    /*package-local*/ static final String JOIN_ROOM_REQUEST= "JoinRoomRequest";
    /*package-local*/ static final String CREATE_ROOM_REQUEST= "CreateRoomRequest";

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

}
