package v4.game;

public class Clock extends Thread {

    public static boolean running = true;

    public void run(){

        while (running){
            try {
                sleep(50);
                v4.game.Control.nextGen();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
