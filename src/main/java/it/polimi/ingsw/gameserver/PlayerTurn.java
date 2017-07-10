package it.polimi.ingsw.gameserver;

import it.polimi.ingsw.server.ServerPlayer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * This class manages the player's turn
 */
/*package-local*/ class PlayerTurn {

    /**
     * The current player that is playing his turn
     */
    private ServerPlayer currentPlayer;

    /**
     * Timer for the player's move
     */
    private Timer timer;


    private CountDownLatch countDownLatch;

    private static final Object object = new Object();

    /**
     * Class constructor
     * @param currentPlayer the current player
     */
    /*package-local*/ PlayerTurn(ServerPlayer currentPlayer){
        this.currentPlayer = currentPlayer;
        timer = new Timer();
    }

    /**
     * This method starts the timer
     * @param moveWaitingTime
     */
    /*package-local*/ void startTimer(long moveWaitingTime){
        int time = (int)(moveWaitingTime/1000);
        countDownLatch = new CountDownLatch(time);
        timer.scheduleAtFixedRate(new TimeCountDown(), 1000, 1000);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method stops the timer
     */
    /*package-local*/ void stopTimer(){
        synchronized (object){
            resetTimer();
            while (countDownLatch.getCount() > 0)
                countDownLatch.countDown();
        }
    }

    /**
     * This method returns the current player that is playing his turn
     * @return
     */
    /*package-local*/ ServerPlayer currentPlayer(){
        return this.currentPlayer;
    }

    /**
     * This method resets the timer
     */
    /*package-local*/ private void resetTimer(){
        timer.cancel();
        timer.purge();
    }


    private class TimeCountDown extends TimerTask{
        @Override
        public void run() {
            synchronized (object){
                if(countDownLatch.getCount() > 0){
                    if(countDownLatch.getCount() == 1)
                        resetTimer();
                    countDownLatch.countDown();
                }
            }
        }
    }
}
