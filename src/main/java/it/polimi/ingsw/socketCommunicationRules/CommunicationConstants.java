package it.polimi.ingsw.socketCommunicationRules;

/*package-local*/ class CommunicationConstants {

    /**
     * Requests on server side.
     */
    /*package-local*/ static final String LOGIN_REQUEST = "loginRequest";
    /*package-local*/ static final String SIGNIN_REQUEST = "signinRequest";

    /**
     * Responses code, shared between clients and sockets.
     */
    /*package-local*/ static final int CODE_OK = 1;
    /*package-local*/ static final int CODE_ALREADY_EXISTS = 2;
    /*package-local*/ static final int CODE_LOGIN_FAILED = 3;

}
