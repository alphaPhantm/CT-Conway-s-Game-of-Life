package game;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Thread{

    public static boolean running = true;

    public void start(Control control){

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (running){
                    control.nextGen();
                }
            }
        };

        t.scheduleAtFixedRate(tt, 1, 1000);

    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        Clock.running = running;
    }

}

