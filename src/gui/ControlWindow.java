package gui;

import javax.swing.*;
import java.awt.*;

public class ControlWindow {

    private JFrame controlWindow;

    public ControlWindow(){

        controlWindow = new JFrame();

        controlWindow.getContentPane().setPreferredSize(new Dimension(400, 800));
        controlWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        controlWindow.setResizable(false);
//        controlWindow.set
        controlWindow.setLayout(null);

        controlWindow.pack();
        controlWindow.setVisible(true);

    }

}
