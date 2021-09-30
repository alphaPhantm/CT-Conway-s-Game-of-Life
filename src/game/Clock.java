package game;

import gui.GUI;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Thread{

    private boolean running = true;

    public void start(Control control, GUI gui){



        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                if (running){
                    gui.showGrid(control.getCells());
                    control.nextGen();


                }
            }
        };

        t.scheduleAtFixedRate(tt, 1000, 3000);

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}

