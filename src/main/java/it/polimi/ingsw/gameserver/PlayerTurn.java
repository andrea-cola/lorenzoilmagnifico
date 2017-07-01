package it.polimi.ingsw.gameserver;

import it.polimi.ingsw.server.ServerPlayer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/*package-local*/ class PlayerTurn {

    private ServerPlayer currentPlayer;

    private Timer timer;

    private CountDownLatch countDownLatch;

    private static final Object object = new Object();

    /*package-local*/ PlayerTurn(ServerPlayer currentPlayer){
        this.currentPlayer = currentPlayer;
        timer = new Timer();
    }

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

    /*package-local*/ void stopTimer(){
        synchronized (object){
            resetTimer();
            while (countDownLatch.getCount() > 0)
                countDownLatch.countDown();
        }
    }

    /*package-local*/ ServerPlayer currentPlayer(){
        return this.currentPlayer;
    }

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
