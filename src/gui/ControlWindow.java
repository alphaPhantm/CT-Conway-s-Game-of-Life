package gui;

import javax.swing.*;
import java.awt.*;

public class ControlWindow {

    private JFrame controlWindow;

    private final int width = 400, height = 800;

    public ControlWindow(GameWindow gameWindow, int offset){

        controlWindow = new JFrame();

        controlWindow.getContentPane().setPreferredSize(new Dimension(width, height));
        controlWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        controlWindow.setResizable(false);
        controlWindow.setLocation(gameWindow.getPos().x - (width + offset), gameWindow.getPos().y);
        controlWindow.setLayout(null);

        controlWindow.pack();
        controlWindow.setVisible(true);

    }

    public void setVisibility(boolean value){
        controlWindow.setVisible(value);
    }

}
