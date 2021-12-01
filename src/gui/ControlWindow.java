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
    private JButton previousGenButton;
    private JButton lockMouse;
    private JButton clearButton;
    private JButton multipleGenSkipButton;
    private JButton jump2GenButton;
    private JTextField multipleGenSkipLable;
    private JTextField jump2GenLable;

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

        ImageIcon imageIcon = new ImageIcon("src/data/icons/Icon.png");
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
        initPreviousGenButton();
        initDrawLockButton();
        initClearButton();
        initMultibleGenSkip();
        initJump2Gen();
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
        velocitySlider.setValue(gui.getVelocity());
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

        velocitySliderLabel = new JTextField(String.valueOf(gui.getVelocity()));
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
                if (gui.getGeneration() == 0){
                    gui.saveFirstGrid();
                }
                if (!gui.isRunning()){
                    gui.setMouseLocked(true);
                    updateLockedMouse();
                }
                gui.setRunning(!gui.isRunning());
                updateToggleButton();
                gui.setVelocity(velocity);
            }
        });

        updateToggleButton();
    }

    private void initNextButton(){
        nextGenButton = new JButton();
        nextGenButton.setText("Next Gen.");
        nextGenButton.setFont(text);
        nextGenButton.setForeground(Color.decode("#121212"));
        nextGenButton.setBorderPainted(true);
        nextGenButton.setFocusPainted(false);
        nextGenButton.setContentAreaFilled(false);
        nextGenButton.setBounds(225, 675, 125, 50);
        nextGenButton.setVisible(true);
        nextGenButton.addMouseListener(hover);
        nextGenButton.setBorder(new RoundedBorder(25));
        nextGenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.isRunning()) {
                    if (gui.getGeneration() == 0) {
                        gui.saveFirstGrid();
                    }
                    gui.nextGen();
                    gui.showGrid(gui.getCells());
                }
            }
        });
    }

    private void initPreviousGenButton(){
        previousGenButton = new JButton();
        previousGenButton.setText("Prior Gen.");
        previousGenButton.setFont(text);
        previousGenButton.setForeground(Color.decode("#121212"));
        previousGenButton.setBorderPainted(true);
        previousGenButton.setFocusPainted(false);
        previousGenButton.setContentAreaFilled(false);
        previousGenButton.setBounds(50, 675, 125, 50);
        previousGenButton.setVisible(true);
        previousGenButton.addMouseListener(hover);
        previousGenButton.setBorder(new RoundedBorder(25));
        previousGenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.isRunning()) {
                    gui.previousGen();
                    gui.showGrid(gui.getCells());
                }
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
        lockMouse.setBounds(50, 450, 300, 50);
        lockMouse.setVisible(true);
        lockMouse.addMouseListener(hover);
        lockMouse.setBorder(new RoundedBorder(25));
        lockMouse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.isRunning()) {
                    gui.setMouseLocked(!gui.isMouseLocked());
                    updateLockedMouse();
                }
            }
        });
        updateLockedMouse();
    }

    private void initClearButton(){
        clearButton = new JButton();
        clearButton.setText("Clear Grid");
        clearButton.setFont(text);
        clearButton.setForeground(Color.decode("#121212"));
        clearButton.setBorderPainted(true);
        clearButton.setFocusPainted(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setBounds(50, 375, 300, 50);
        clearButton.setVisible(true);
        clearButton.addMouseListener(hover);
        clearButton.setBorder(new RoundedBorder(25));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.isRunning()) {
                    gui.clearGrid();
                    gui.showGrid(gui.getCells());
                }
            }
        });
    }

    private void initMultibleGenSkip(){

        multipleGenSkipButton = new JButton();
        multipleGenSkipButton.setText("Next           Generation");
        multipleGenSkipButton.setFont(text);
        multipleGenSkipButton.setForeground(Color.decode("#121212"));
        multipleGenSkipButton.setBorderPainted(true);
        multipleGenSkipButton.setFocusPainted(false);
        multipleGenSkipButton.setContentAreaFilled(false);
        multipleGenSkipButton.setBounds(50, 600, 300, 50);
        multipleGenSkipButton.setVisible(true);
        multipleGenSkipButton.addMouseListener(hover);
        multipleGenSkipButton.setBorder(new RoundedBorder(25));
        multipleGenSkipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.isRunning()) {
                    gui.multipleGenSkip(Integer.parseInt(multipleGenSkipLable.getText()));
                    gui.showGrid(gui.getCells());
                }
            }
        });

        multipleGenSkipLable = new JTextField("6");
        multipleGenSkipLable.setOpaque(false);
        multipleGenSkipLable.setBackground(null);
        multipleGenSkipLable.setBorder(null);
        multipleGenSkipLable.setBounds(160, 600, 50, 50);
        multipleGenSkipLable.setFont(text);
        multipleGenSkipLable.setForeground(Color.decode("#121212"));
        multipleGenSkipLable.setVisible(true);
        multipleGenSkipLable.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });
    }

    private void initJump2Gen(){

        jump2GenButton = new JButton();
        jump2GenButton.setText("Jump to generation");
        jump2GenButton.setFont(text);
        jump2GenButton.setForeground(Color.decode("#121212"));
        jump2GenButton.setBorderPainted(true);
        jump2GenButton.setFocusPainted(false);
        jump2GenButton.setContentAreaFilled(false);
        jump2GenButton.setBounds(50, 525, 300, 50);
        jump2GenButton.setVisible(true);
        jump2GenButton.addMouseListener(hover);
        jump2GenButton.setBorder(new RoundedBorder(25));
        jump2GenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.isRunning()) {
                    gui.jump2Gen(Integer.parseInt(jump2GenLable.getText()));
                    gui.showGrid(gui.getCells());
                }
            }
        });

        jump2GenLable = new JTextField("6");
        jump2GenLable.setOpaque(false);
        jump2GenLable.setBackground(null);
        jump2GenLable.setBorder(null);
        jump2GenLable.setBounds(275, 525, 50, 50);
        jump2GenLable.setFont(text);
        jump2GenLable.setForeground(Color.decode("#121212"));
        jump2GenLable.setVisible(true);
        jump2GenLable.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });
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
        controlWindow.add(previousGenButton);
        controlWindow.add(lockMouse);
        controlWindow.add(clearButton);
        controlWindow.add(multipleGenSkipLable);
        controlWindow.add(jump2GenLable);
        controlWindow.add(multipleGenSkipButton);
        controlWindow.add(jump2GenButton);
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
