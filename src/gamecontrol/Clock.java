package gamecontrol;

import gui.control.GUI;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Thread {

    private Control control;
    private boolean running = true;
    private int velocity, delay;
    private boolean isFirstGen;
    private Timer t;
    private TimerTask tt;

    public Clock(Control control) {
        this.control = control;
        this.velocity = 500;
        this.delay = 0;

        this.isFirstGen = true;
    }

    public void start() {

        this.delay = this.velocity;

        if (isFirstGen) {
            t = new Timer();
            this.delay = 0;
            this.isFirstGen = false;
        }

       if (tt != null){
           tt.cancel();
       }

        tt = new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    control.showGrid(control.getCells());
                    control.nextGen();
                }
            }
        };


        t.scheduleAtFixedRate(tt, delay, this.velocity);
    }


    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
        start();
    }

    public boolean isFirstGen() {
        return isFirstGen;
    }

    public void setFirstGen(boolean firstGen) {
        isFirstGen = firstGen;
    }
}

