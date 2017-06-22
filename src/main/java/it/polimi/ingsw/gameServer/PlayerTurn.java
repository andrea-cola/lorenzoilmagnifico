package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.server.ServerPlayer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class PlayerTurn {

    private ServerPlayer currentPlayer;

    private Timer timer;

    private CountDownLatch countDownLatch;

    private static final Object object = new Object();

    PlayerTurn(ServerPlayer currentPlayer){
        this.currentPlayer = currentPlayer;
        timer = new Timer();
    }

    void startTimer(long moveWaitingTime){
        int time = (int)(moveWaitingTime/1000);
        countDownLatch = new CountDownLatch(time);
        timer.scheduleAtFixedRate(new TimeCountDown(), 1000, 1000);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    void stopTimer(){
        synchronized (object){
            resetTimer();
            while (countDownLatch.getCount() > 0)
                countDownLatch.countDown();
        }
    }

    ServerPlayer currentPlayer(){
        return this.currentPlayer;
    }

    private void resetTimer(){
        timer.cancel();
        timer.purge();
    }

    private class TimeCountDown extends TimerTask{

        /**
         * The action to be performed by this timer task.
         */
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
