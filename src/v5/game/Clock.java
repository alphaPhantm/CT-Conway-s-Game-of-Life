package v5.game;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Thread{

    public static boolean running = true;

    public void start(){

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (running){
                    Control.nextGen();
                }
            }
        };

        t.scheduleAtFixedRate(tt, 1, 1);

    }

}

