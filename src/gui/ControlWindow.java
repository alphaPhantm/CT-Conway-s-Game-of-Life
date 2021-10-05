package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class ControlWindow {

    private final int width = 400, height = 800;
    private JFrame controlWindow;
    private JButton toggleGame;

    private int maxVelocity = 1 , minVelocity = 10000;

    private JSlider velocitySlider;
    private JLabel velocitySliderName;
    private JTextField velocitySliderLabel;

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
                setVelocity(100);
            }
        });


        controlWindow.add(toggleGame);

    }

    private void initVelocity(Font text){
        velocitySlider = new JSlider(minVelocity, maxVelocity);
        velocitySlider.setBounds(180, 110, 200, 20);
        velocitySlider.setFont(text);
        velocitySlider.setForeground(Color.decode("#121212"));
        velocitySlider.setValue(6);
        velocitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!sliderLocked) {
                    xAxisSize = velocitySlider.getValue();
                    updateSliderLabels(0, xAxisSize);
                    updateStartCells();
                }
            }
        });
        velocitySlider.setVisible(true);


        xAxisName = new JLabel("Breite in Zellen:");
        xAxisName.setBounds(50, 100, 200, 30);
        xAxisName.setFont(text);
        xAxisName.setForeground(Color.decode("#121212"));
        xAxisName.setVisible(true);

        xAxisLabel = new JTextField("6");
        xAxisLabel.setBackground(null);
        xAxisLabel.setBorder(null);
        xAxisLabel.setBounds(180, 80, 200, 30);
        xAxisLabel.setFont(text);
        xAxisLabel.setForeground(Color.decode("#121212"));
        xAxisLabel.setVisible(true);
        xAxisLabel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        xAxisLabel.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                sliderLocked = true;
                String s = xAxisLabel.getText();
                velocitySlider.setValue(Integer.parseInt(s));
                xAxisSize = velocitySlider.getValue();
                updateStartCells();
                sliderLocked = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
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


    public void setVelocity(int velocity){
        gui.setVelocity(velocity);
    }
}
