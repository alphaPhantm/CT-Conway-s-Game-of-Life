package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ControlWindow {

    private final int width = 400, height = 800;
    private JFrame controlWindow;
    private JButton toggleGame;

    private GUI gui;

    public ControlWindow(GameWindow gameWindow, GUI gui, int offset) {

        this.gui = gui;

        controlWindow = new JFrame();

        controlWindow.getContentPane().setPreferredSize(new Dimension(width, height + (2 * offset)));
        controlWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        controlWindow.setResizable(false);
        controlWindow.setLocation(gameWindow.getPos().x - (width + offset), gameWindow.getPos().y);
        controlWindow.setLayout(null);

        ImageIcon imageIcon = new ImageIcon("src/files/PNG/Icon.png");
        controlWindow.setIconImage(imageIcon.getImage());

        controlWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                setVisibility(false);
            }
        });

        controlWindow.pack();
        controlWindow.setVisible(true);

        toggleGame = new JButton();
        updateToggleButton();

        toggleGame.setBounds(50, 50, 100, 50);

        toggleGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setRunning(!gui.isRunning());
                updateToggleButton();
            }
        });


        controlWindow.add(toggleGame);

    }

    public void setVisibility(boolean value) {
        controlWindow.setVisible(value);
    }

    private void updateToggleButton() {
        if (!gui.isRunning()) {
            toggleGame.setText("Resume Game");
        } else {
            toggleGame.setText("Pause Game");
        }
    }

    public void dispose(){
        controlWindow.dispose();
    }

}
