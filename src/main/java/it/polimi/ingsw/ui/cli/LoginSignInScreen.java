package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;

public class LoginSignInScreen extends BasicScreen {

    private final ICallback callback;

    LoginSignInScreen(ScreenInterface screenInterface, ICallback callback){
        super(screenInterface);

        System.out.println("\n\n[LOGIN & SIGN IN]");
        this.callback = callback;
        addPrintCommand("login", arguments->login(arguments));
        addPrintCommand("signin", arguments->signin(arguments));
        printHelps();
        readCommand();
    }

    private void printHelps(){
        System.out.println("Helps: login [username] [password]");
        System.out.println("Helps: signin [username] [password]");
    }

    private void login(String[] arguments) throws WrongCommandException{
        if(arguments.length == 2)
            this.callback.loginPlayer(arguments[0], arguments[1], false);
        else
            throw new WrongCommandException();
    }

    private void signin(String[] arguments) throws WrongCommandException {
        if(arguments.length == 2)
            this.callback.loginPlayer(arguments[0], arguments[1], true);
        else
            throw new WrongCommandException();
    }

    @FunctionalInterface
    interface ICallback {
        void loginPlayer(String username, String password, boolean flag);
    }
}