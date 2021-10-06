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

    private Font head;
    private Font text;

    private JFrame controlWindow;
    private JButton toggleGame;

    private int velocity, minVelocity = 1 , maxVelocity = 10000;


    private JSlider velocitySlider;
    private JLabel velocitySliderName;
    private JTextField velocitySliderLabel;

    private GUI gui;

    public ControlWindow(GameWindow gameWindow, GUI gui, int offset) {

        this.gui = gui;

        this.head = gui.getHead();
        this.text = gui.getText();

        velocity = 500;

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
                gui.setVelocity(velocity);
            }
        });

        initVelocity();

        controlWindow.add(velocitySlider);
        controlWindow.add(velocitySliderName);
        controlWindow.add(velocitySliderLabel);
        controlWindow.add(toggleGame);

    }

    /* Contol Windwo Update. Velocity Change implemented.
    FONT and some other Var and method needs to be implemented in the GUI class not in Menu Window */

    private void initVelocity(){
        velocitySlider = new JSlider(minVelocity, maxVelocity);
        velocitySlider.setBounds(180, 110, 200, 20);
        velocitySlider.setFont(text);
        velocitySlider.setForeground(Color.decode("#121212"));
        velocitySlider.setValue(6);
        velocitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!gui.isSliderLocked()) {
                    velocity = velocitySlider.getValue();
                    velocitySliderLabel.setText(String.valueOf(velocity));
                    gui.setVelocity(velocity);
                }
            }
        });
        velocitySlider.setVisible(true);


        velocitySliderName = new JLabel("Breite in Zellen:");
        velocitySliderName.setBounds(50, 100, 200, 30);
        velocitySliderName.setFont(text);
        velocitySliderName.setForeground(Color.decode("#121212"));
        velocitySliderName.setVisible(true);

        velocitySliderLabel = new JTextField("6");
        velocitySliderLabel.setBackground(null);
        velocitySliderLabel.setBorder(null);
        velocitySliderLabel.setBounds(180, 80, 200, 30);
        velocitySliderLabel.setFont(text);
        velocitySliderLabel.setForeground(Color.decode("#121212"));
        velocitySliderLabel.setVisible(true);
        velocitySliderLabel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        velocitySliderLabel.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                gui.setSliderLocked(true);
                String s = velocitySliderLabel.getText();
                velocitySlider.setValue(Integer.parseInt(s));
                velocity = velocitySlider.getValue();
                gui.setVelocity(velocity);
                gui.setSliderLocked(false);
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
