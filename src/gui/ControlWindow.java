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
    private MouseAdapter hover;

    private JFrame controlWindow;
    private JLabel heading;
    private JButton toggleGame;
    private JButton nextGenButton;
    private JButton lockMouse;
    private JButton clearButton;
    private JButton multipleGenSkipButton;
    private JTextField multipleGenSkipLable;

    private int velocity, minVelocity = 1 , maxVelocity = 5000;


    private JSlider velocitySlider;
    private JLabel velocitySliderName;
    private JTextField velocitySliderLabel;

    private GUI gui;

    public ControlWindow(GameWindow gameWindow, GUI gui, int offset) {

        this.gui = gui;

        this.head = gui.getHead();
        this.text = gui.getText();
        this.hover = gui.getHover();

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



        initComponents();
        addComponents();


    }

    private void initComponents(){
        initHeading();
        initVelocity();
        initToggleButton();
        initNextButton();
        initDrawLockButton();
    }

    private void initHeading(){
        heading = new JLabel("Control Panel");
        heading.setFont(head);
        heading.setForeground(Color.decode("#121212"));
        heading.setBounds(50, 25, 400, 50);
        heading.setVisible(true);
    }

    private void initVelocity(){
        velocitySlider = new JSlider(minVelocity, maxVelocity);
        velocitySlider.setBounds(120, 110, 230, 20);
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


        velocitySliderName = new JLabel("Velocity:");
        velocitySliderName.setBounds(50, 100, 200, 30);
        velocitySliderName.setFont(text);
        velocitySliderName.setForeground(Color.decode("#121212"));
        velocitySliderName.setVisible(true);

        velocitySliderLabel = new JTextField("6");
        velocitySliderLabel.setBackground(null);
        velocitySliderLabel.setBorder(null);
        velocitySliderLabel.setBounds(120, 80, 200, 30);
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

    private void initToggleButton(){

        toggleGame = new JButton();
        toggleGame.setText("Start Game");
        toggleGame.setFont(text);
        toggleGame.setForeground(Color.decode("#121212"));
        toggleGame.setBorderPainted(true);
        toggleGame.setFocusPainted(false);
        toggleGame.setContentAreaFilled(false);
        toggleGame.setBounds(50, 750, 300, 50);
        toggleGame.setVisible(true);
        toggleGame.addMouseListener(hover);
        toggleGame.setBorder(new RoundedBorder(25));


        toggleGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setRunning(!gui.isRunning());
                updateToggleButton();
                gui.setVelocity(velocity);
            }
        });

        updateToggleButton();
    }

    private void initNextButton(){
        nextGenButton = new JButton();
        nextGenButton.setText("Next Generation");
        nextGenButton.setFont(text);
        nextGenButton.setForeground(Color.decode("#121212"));
        nextGenButton.setBorderPainted(true);
        nextGenButton.setFocusPainted(false);
        nextGenButton.setContentAreaFilled(false);
        nextGenButton.setBounds(50, 675, 300, 50);
        nextGenButton.setVisible(true);
        nextGenButton.addMouseListener(hover);
        nextGenButton.setBorder(new RoundedBorder(25));
        nextGenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.nextGen();
                gui.showGrid(gui.getCells());
            }
        });
    }

    private void initDrawLockButton(){
        lockMouse = new JButton();
        lockMouse.setFont(text);
        lockMouse.setForeground(Color.decode("#121212"));
        lockMouse.setBorderPainted(true);
        lockMouse.setFocusPainted(false);
        lockMouse.setContentAreaFilled(false);
        lockMouse.setBounds(50, 600, 300, 50);
        lockMouse.setVisible(true);
        lockMouse.addMouseListener(hover);
        lockMouse.setBorder(new RoundedBorder(25));
        lockMouse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setMouseLocked(!gui.isMouseLocked());
                updateLockedMouse();
            }
        });
        updateLockedMouse();
    }

    private void initSaveButton(){}

    private void initSaveNameField(){}

    private void addComponents(){
        controlWindow.add(heading);
        controlWindow.add(velocitySlider);
        controlWindow.add(velocitySliderName);
        controlWindow.add(velocitySliderLabel);
        controlWindow.add(toggleGame);
        controlWindow.add(nextGenButton);
        controlWindow.add(lockMouse);
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


    private void updateLockedMouse() {
        if (!gui.isMouseLocked()) {
            lockMouse.setText("Lock Mouse");
        } else {
            lockMouse.setText("Unlock Mouse");
        }
    }

    public void dispose(){
        controlWindow.dispose();
    }


    public void setVelocity(int velocity){
        gui.setVelocity(velocity);
    }
}
