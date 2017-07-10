package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;

/**
 * Excommunication logic screen.
 */
/*package-local*/ class ExcommunicationScreen extends BasicGameScreen{

    /**
     * Callback interface.
     */
    private ExcommunicationCallback callback;

    /**
     * Class constructor.
     * @param callback interface.
     */
    /*package-local*/ ExcommunicationScreen(ExcommunicationCallback callback) {
        this.callback = callback;
        printScreenTitle("DO YOU WANT TO BE EXCOMMUNICATED?");
        addOption("ex", "'ex [yes/no]' to get the excommunication.", this::getExcommunicationChoice);
    }

    /**
     * Get choice.
     * @param parameters passed.
     * @throws WrongCommandException if something is wrong.
     */
    private void getExcommunicationChoice(String[] parameters) throws WrongCommandException{
        if(parameters.length == 1){
            if(parameters[0].equals("yes")) {
                this.callback.notifyExcommunicationChoice(true);
                return;
            }
            if(parameters[0].equals("no")) {
                this.callback.notifyExcommunicationChoice(false);
                return;
            }
        }
        throw new WrongCommandException();
    }

    /**
     * Callback interface.
     */
    @FunctionalInterface
    public interface ExcommunicationCallback{

        void notifyExcommunicationChoice(boolean choice);

    }

}
