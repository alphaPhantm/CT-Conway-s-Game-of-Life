package game;

import gui.GUI;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Thread {

    private Control control;
    private GUI gui;
    private boolean running = true;
    private int velocity;
    private Timer t;
    private TimerTask tt;

    public Clock(Control control, GUI gui) {
        this.control = control;
        this.gui = gui;
        this.velocity = 500;
    }

    public void start() {

        if (t == null) {
            t = new Timer();
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

        System.out.println(this.velocity);
        t.scheduleAtFixedRate(tt, 0, this.velocity);
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

}

