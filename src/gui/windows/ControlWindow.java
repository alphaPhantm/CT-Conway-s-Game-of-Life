package gui.windows;

import gamecontrol.Control;
import gui.fundamental.Hover;
import gui.fundamental.JTextFieldLimit;
import gui.fundamental.RoundedBorder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;


/**
 * In this class the Control Window is implemented. In the Control Window you are able to change some game variables and enable the drawing mode.
 * @author Noah Kessinger
 */
public class ControlWindow extends GUI {

    private final int width = 400, height = 800;

    private final JFrame controlWindow;
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
    private JTextField saveGrid;
    private JLabel saveGridPlaceholder;

    private int velocity;
    private final int minVelocity = 1;
    private final int maxVelocity = 5000;


    private JSlider velocitySlider;
    private JLabel velocitySliderName;
    private JTextField velocitySliderLabel;

    public ControlWindow(Control control) {
        super(control);

        velocity = 500;

        initPublicComponents();

        controlWindow = new JFrame(title + " Control");

        controlWindow.getContentPane().setPreferredSize(new Dimension(width, height + (2 * offset)));
        controlWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        controlWindow.setResizable(false);
        controlWindow.setLocation(control.getGameWindowPos().x - (width + offset), control.getGameWindowPos().y);
        controlWindow.setLayout(null);

        ImageIcon imageIcon = new ImageIcon(iconPath);
        controlWindow.setIconImage(imageIcon.getImage());

        controlWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                setVisibility(false);
            }
        });

        controlWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveGrid.setFocusable(false);
                saveGrid.setFocusable(true);
            }
        });

        controlWindow.pack();
        controlWindow.setVisible(true);


        initComponents();
        addComponents();


    }

    private void initComponents() {
        initHeading();
        initVelocity();
        initToggleButton();
        initNextButton();
        initPreviousGenButton();
        initDrawLockButton();
        initClearButton();
        initMultibleGenSkip();
        initJump2Gen();
        initSaveGrid();
    }

    private void initHeading() {
        heading = new JLabel("Control Panel");
        heading.setFont(head);
        heading.setForeground(Color.decode("#121212"));
        heading.setBounds(50, 25, 400, 50);
        heading.setVisible(true);
    }

    private void initVelocity() {
        velocitySlider = new JSlider(minVelocity, maxVelocity);
        velocitySlider.setBounds(120, 110, 230, 20);
        velocitySlider.setFont(text);
        velocitySlider.setForeground(Color.decode("#121212"));
        velocitySlider.setValue(getVelocity());
        velocitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!isSliderLocked()) {
                    velocity = velocitySlider.getValue();
                    velocitySliderLabel.setText(String.valueOf(velocity));
                    setVelocity(velocity);
                }
            }
        });
        velocitySlider.setVisible(true);


        velocitySliderName = new JLabel("Velocity:");
        velocitySliderName.setBounds(50, 100, 200, 30);
        velocitySliderName.setFont(text);
        velocitySliderName.setForeground(Color.decode("#121212"));
        velocitySliderName.setVisible(true);

        velocitySliderLabel = new JTextField(String.valueOf(getVelocity()));
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

                setSliderLocked(true);
                String s = velocitySliderLabel.getText();
                velocitySlider.setValue(Integer.parseInt(s));
                velocity = velocitySlider.getValue();
                setVelocity(velocity);
                setSliderLocked(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


    }

    private void initToggleButton() {

        toggleGame = new JButton();
        toggleGame.setText("Start Game");
        toggleGame.setFont(text);
        toggleGame.setForeground(Color.decode("#121212"));
        toggleGame.setBorderPainted(true);
        toggleGame.setFocusPainted(false);
        toggleGame.setContentAreaFilled(false);
        toggleGame.setBounds(50, 750, 300, 50);
        toggleGame.setVisible(true);
        toggleGame.addMouseListener(new Hover());
        toggleGame.setBorder(new RoundedBorder(25));


        toggleGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getGeneration() == 0) {
                    saveFirstGrid();
                }
                if (!isRunning()) {
                    setMouseLocked(true);
                    saveGrid.setFocusable(false);
                    updateLockedMouse();
                } else {
                    saveGrid.setFocusable(true);
                }
                setRunning(!isRunning());
                updateToggleButton();
                setVelocity(velocity);
            }
        });

        updateToggleButton();
    }

    private void initNextButton() {
        nextGenButton = new JButton();
        nextGenButton.setText("Next Gen.");
        nextGenButton.setFont(text);
        nextGenButton.setForeground(Color.decode("#121212"));
        nextGenButton.setBorderPainted(true);
        nextGenButton.setFocusPainted(false);
        nextGenButton.setContentAreaFilled(false);
        nextGenButton.setBounds(225, 675, 125, 50);
        nextGenButton.setVisible(true);
        nextGenButton.addMouseListener(new Hover());
        nextGenButton.setBorder(new RoundedBorder(25));
        nextGenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    if (getGeneration() == 0) {
                        saveFirstGrid();
                    }
                    nextGen();
                    control.showGrid(getCells());
                }
            }
        });
    }

    private void initPreviousGenButton() {
        previousGenButton = new JButton();
        previousGenButton.setText("Prior Gen.");
        previousGenButton.setFont(text);
        previousGenButton.setForeground(Color.decode("#121212"));
        previousGenButton.setBorderPainted(true);
        previousGenButton.setFocusPainted(false);
        previousGenButton.setContentAreaFilled(false);
        previousGenButton.setBounds(50, 675, 125, 50);
        previousGenButton.setVisible(true);
        previousGenButton.addMouseListener(new Hover());
        previousGenButton.setBorder(new RoundedBorder(25));
        previousGenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    previousGen();
                    control.showGrid(getCells());
                }
            }
        });
    }

    private void initDrawLockButton() {
        lockMouse = new JButton();
        lockMouse.setFont(text);
        lockMouse.setForeground(Color.decode("#121212"));
        lockMouse.setBorderPainted(true);
        lockMouse.setFocusPainted(false);
        lockMouse.setContentAreaFilled(false);
        lockMouse.setBounds(50, 450, 300, 50);
        lockMouse.setVisible(true);
        lockMouse.addMouseListener(new Hover());
        lockMouse.setBorder(new RoundedBorder(25));
        lockMouse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    setMouseLocked(!isMouseLocked());
                    updateLockedMouse();
                }
            }
        });
        updateLockedMouse();
    }

    private void initClearButton() {
        clearButton = new JButton();
        clearButton.setText("Clear Grid");
        clearButton.setFont(text);
        clearButton.setForeground(Color.decode("#121212"));
        clearButton.setBorderPainted(true);
        clearButton.setFocusPainted(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setBounds(50, 375, 300, 50);
        clearButton.setVisible(true);
        clearButton.addMouseListener(new Hover());
        clearButton.setBorder(new RoundedBorder(25));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    clearGrid();
                    control.showGrid(getCells());
                }
            }
        });
    }

    private void initMultibleGenSkip() {

        multipleGenSkipButton = new JButton();
        multipleGenSkipButton.setText("Next           Generation");
        multipleGenSkipButton.setFont(text);
        multipleGenSkipButton.setForeground(Color.decode("#121212"));
        multipleGenSkipButton.setBorderPainted(true);
        multipleGenSkipButton.setFocusPainted(false);
        multipleGenSkipButton.setContentAreaFilled(false);
        multipleGenSkipButton.setBounds(50, 600, 300, 50);
        multipleGenSkipButton.setVisible(true);
        multipleGenSkipButton.addMouseListener(new Hover());
        multipleGenSkipButton.setBorder(new RoundedBorder(25));
        multipleGenSkipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    multipleGenSkip(Integer.parseInt(multipleGenSkipLable.getText()));
                    control.showGrid(getCells());
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

    private void initJump2Gen() {

        jump2GenButton = new JButton();
        jump2GenButton.setText("Jump to generation");
        jump2GenButton.setFont(text);
        jump2GenButton.setForeground(Color.decode("#121212"));
        jump2GenButton.setBorderPainted(true);
        jump2GenButton.setFocusPainted(false);
        jump2GenButton.setContentAreaFilled(false);
        jump2GenButton.setBounds(50, 525, 300, 50);
        jump2GenButton.setVisible(true);
        jump2GenButton.addMouseListener(new Hover());
        jump2GenButton.setBorder(new RoundedBorder(25));
        jump2GenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    jump2Gen(Integer.parseInt(jump2GenLable.getText()));
                    control.showGrid(getCells());
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

    private void initSaveGrid() {

        saveGridPlaceholder = new JLabel("Save Grid...");
        saveGridPlaceholder.setBounds(50, 300, 300, 50);
        saveGridPlaceholder.setFont(text);
        saveGridPlaceholder.setForeground(Color.decode("#121212"));
        saveGridPlaceholder.setVisible(true);

        saveGrid = new JTextField();
        saveGrid.setDocument(new JTextFieldLimit(15));
        saveGrid.setFocusable(false);
        saveGrid.setOpaque(false);
        saveGrid.setBackground(null);
        saveGrid.setBorder(null);
        saveGrid.setBounds(50, 300, 300, 50);
        saveGrid.setFont(text);
        saveGrid.setForeground(Color.decode("#121212"));
        saveGrid.setVisible(true);
        saveGrid.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!(Character.isDigit(evt.getKeyChar()) || Character.isLetter(evt.getKeyChar()))) {
                    evt.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (checkName(saveGrid.getText() ) && getAllGrids().size() < 10) {
                        saveGridInDB(saveGrid.getText());
                    }

                    saveGrid.setText("");
                    saveGridPlaceholder.setVisible(true);
                    controlWindow.requestFocus();

                }
            }
        });
        saveGrid.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                saveGridPlaceholder.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (saveGrid.getText().equals("")) {
                    saveGridPlaceholder.setVisible(true);
                }

            }
        });

    }

    private void addComponents() {
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
        controlWindow.add(saveGridPlaceholder);
        controlWindow.add(saveGrid);
    }

    public void setVisibility(boolean value) {
        controlWindow.setVisible(value);
    }

    private void updateToggleButton() {
        if (!isRunning()) {
            toggleGame.setText("Resume Game");
        } else {
            toggleGame.setText("Pause Game");
        }
    }


    private void updateLockedMouse() {
        if (!isMouseLocked()) {
            lockMouse.setText("Lock Mouse");
        } else {
            lockMouse.setText("Unlock Mouse");
        }
    }

    public void dispose() {
        controlWindow.dispose();
    }
}
