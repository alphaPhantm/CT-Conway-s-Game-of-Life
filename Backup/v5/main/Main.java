package v5.main;

import v5.game.Clock;
import v5.game.Control;
import v5.gui.ControlGUI;
import v5.gui.GUI;
import v5.draw.Draw;

public class Main {

    public static void main(String[] args) {

//        ControlGUI controlGUI = new ControlGUI();
//        controlGUI.create();

        Control control = new Control();
        GUI gui = new GUI();
        Clock clock = new Clock();

        control.create();
        gui.create();
        clock.start();

    }

}
