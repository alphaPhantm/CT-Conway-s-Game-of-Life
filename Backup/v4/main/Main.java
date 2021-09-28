package v4.main;

import v4.game.Clock;
import v4.game.Control;
import v4.gui.GUI;

public class Main {

    public static void main(String[] args) {

        GUI g = new GUI();
        Control c = new Control();
        Clock clk = new Clock();

        c.create();
        g.create();
        clk.start();
    }

}
