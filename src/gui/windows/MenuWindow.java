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

public class MenuWindow extends GUI {


    private final JFrame menuWindow;

    private JLabel heading;

    private final int xAxisMin = 6, xAxisMax = 2048;
    private final int yAxisMin = 6, yAxisMax = 2048;

    private int xAxisSize = 6;
    private JSlider xAxis;
    private JLabel xAxisName;
    private JTextField xAxisLabel;
    private int yAxisSize = 6;
    private JSlider yAxis;
    private JLabel yAxisName;
    private JTextField yAxisLabel;
    private int cellCount = 6;
    private JSlider startCells;
    private JLabel startCellsName;
    private JTextField startCellsLabel;

    private JLabel modeName;
    private JComboBox<String> modeBox;

    private MouseListener hover;

    private JButton startButton;
    private JButton helpButton;


    private ImageIcon preview;
    private JLabel previewLabel;

    public MenuWindow(Control control){
        super(control);
        initPublicComponents();

        menuWindow = new JFrame();

        menuWindow.setTitle(this.title);

        ImageIcon imageIcon = new ImageIcon("src/assets/icons/Icon.png");
        menuWindow.setIconImage(imageIcon.getImage());


        menuWindow.setUndecorated(false);

        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWindow.setAutoRequestFocus(false);
        menuWindow.setLayout(null);
        menuWindow.setSize(752, 424);

        menuWindow.setResizable(true);


        menuWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.isAltDown() && e.getKeyCode() == KeyEvent.VK_D) {
                    menuWindow.setVisible(false);
                    if (control.getDatabaseWindow() == null){
                        control.buildDatabaseWindow();
                    } else {
                        control.setDatabaseVisibility(true);
                    }


                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        menuWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuWindow.requestFocus();
            }
        });


        initComponents();
        addComponents();


        menuWindow.setLocationRelativeTo(null);
        menuWindow.setVisible(true);

    }

    private void initComponents() {

        initHeading();
        initXAxis();
        initYAxis();
        initStartCells();
        initModeBox();
        initStartButton();
        initIMG();
        initHelp();

    }


    private void initHelp(){
        helpButton = new JButton();
        helpButton.setText("?");
        helpButton.setFont(small);
        helpButton.setForeground(Color.decode("#121212"));
        helpButton.setBorderPainted(true);
        helpButton.setFocusPainted(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorder(null);
        helpButton.setBounds(692, 344, 40, 40);
        helpButton.setVisible(true);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuWindow, "Press CTRL + AlT + D for Database Window or CTRL + ALT + C in Game Window to show Control Panel.");
            }
        });
    }

    private void initHeading(){
        heading = new JLabel("Menu Panel");
        heading.setFont(head);
        heading.setForeground(Color.decode("#121212"));
        heading.setBounds(50, 25, 400, 50);
        heading.setVisible(true);
    }

    private void initXAxis(){
        xAxis = new JSlider(xAxisMin, xAxisMax);
        xAxis.setBounds(180, 110, 200, 20);
        xAxis.setFont(text);
        xAxis.setForeground(Color.decode("#121212"));
        xAxis.setValue(6);
        xAxis.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!isSliderLocked()) {
                    xAxisSize = xAxis.getValue();
                    updateSliderLabels(0, xAxisSize);
                    updateStartCells();
                }
            }
        });
        xAxis.setVisible(true);


        xAxisName = new JLabel("Width in Cells:");
        xAxisName.setBounds(50, 100, 200, 30);
        xAxisName.setFont(text);
        xAxisName.setForeground(Color.decode("#121212"));
        xAxisName.setVisible(true);

        xAxisLabel = new JTextField();
        xAxisLabel.setDocument(new JTextFieldLimit(4));
        xAxisLabel.setText("6");
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

                setSliderLocked(true);
                String s = xAxisLabel.getText();
                xAxis.setValue(Integer.parseInt(s));
                xAxisSize = xAxis.getValue();
                updateStartCells();
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

    private void initYAxis(){
        yAxis = new JSlider(yAxisMin, yAxisMax);
        yAxis.setBounds(180, 160, 200, 20);
        yAxis.setFont(text);
        yAxis.setForeground(Color.decode("#121212"));
        yAxis.setValue(6);
        yAxis.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!isSliderLocked()) {
                    yAxisSize = yAxis.getValue();
                    updateSliderLabels(1, yAxisSize);
                    updateStartCells();
                }
            }
        });
        yAxis.setVisible(true);

        yAxisName = new JLabel("Height in Cells:");
        yAxisName.setBounds(50, 150, 200, 30);
        yAxisName.setFont(text);
        yAxisName.setForeground(Color.decode("#121212"));
        yAxisName.setVisible(true);

        yAxisLabel = new JTextField();
        yAxisLabel.setDocument(new JTextFieldLimit(4));
        yAxisLabel.setText("6");
        yAxisLabel.setBackground(null);
        yAxisLabel.setBorder(null);
        yAxisLabel.setBounds(180, 130, 200, 30);
        yAxisLabel.setFont(text);
        yAxisLabel.setForeground(Color.decode("#121212"));
        yAxisLabel.setVisible(true);
        yAxisLabel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        yAxisLabel.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                setSliderLocked(true);
                String s = yAxisLabel.getText();
                yAxis.setValue(Integer.parseInt(s));
                yAxisSize = yAxis.getValue();
                updateStartCells();
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

    private void initStartCells(){
        startCells = new JSlider(6, 6);
        startCells.setBounds(180, 210, 200, 20);
        startCells.setFont(text);
        startCells.setForeground(Color.decode("#121212"));
        startCells.setValue(6);
        startCells.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!isSliderLocked()) {
                    cellCount = startCells.getValue();
                    updateSliderLabels(2, cellCount);
                    updateStartCells();
                }
            }
        });
        startCells.setVisible(true);

        startCellsName = new JLabel("Start/Max Cells:");
        startCellsName.setBounds(50, 200, 200, 30);
        startCellsName.setFont(text);
        startCellsName.setForeground(Color.decode("#121212"));
        startCellsName.setVisible(true);

        startCellsLabel = new JTextField();
        startCellsLabel.setDocument(new JTextFieldLimit(7));
        startCellsLabel.setText("6");
        startCellsLabel.setBackground(null);
        startCellsLabel.setBorder(null);
        startCellsLabel.setBounds(180, 180, 200, 30);
        startCellsLabel.setFont(text);
        startCellsLabel.setForeground(Color.decode("#121212"));
        startCellsLabel.setVisible(true);
        startCellsLabel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        startCellsLabel.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                setSliderLocked(true);
                String s = startCellsLabel.getText();
                startCells.setValue(Integer.parseInt(s));
                cellCount = startCells.getValue();
                setSliderLocked(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        updateStartCells();
    }

    private void initModeBox(){
        modeBox = new JComboBox<String>();
        modeBox.setBounds(180, 257, 200, 20);
        addComboBoxEntry(modeBox, "Randomized");
        addComboBoxEntry(modeBox, "Manuel");

        for (int i = 0; i < getAllGrids().size(); i++){
            addComboBoxEntry(modeBox, getAllGrids().get(i));
        }

        modeName = new JLabel("Select Grid: ");
        modeName.setBounds(50, 250, 200, 30);
        modeName.setFont(text);
        modeName.setForeground(Color.decode("#121212"));
        modeName.setVisible(true);
    }

    private void initStartButton(){
        startButton = new JButton();
        startButton.setText("Start Game");
        startButton.setFont(text);
        startButton.setForeground(Color.decode("#121212"));
        startButton.setBorderPainted(true);
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setBounds(50, 310, 330, 50);
        startButton.setVisible(true);
        startButton.addMouseListener(new Hover());
        startButton.setBorder(new RoundedBorder(25));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buildGameWindow(xAxisSize, yAxisSize, cellCount, modeBox.getSelectedItem().toString());

                menuWindow.setVisible(false);
            }
        });
    }


    private void initIMG(){
        preview = new ImageIcon("src/assets/pictures/Preview.png");
        previewLabel = new JLabel(preview);
        previewLabel.setBounds(450, 45, 203, 302);
    }

    private void updateStartCells() {
        startCells.setMaximum(xAxisSize * yAxisSize);
    }

    private void updateSliderLabels(int slider, int val) {
        switch (slider) {
            case 0 -> xAxisLabel.setText(String.valueOf(val));
            case 1 -> yAxisLabel.setText(String.valueOf(val));
            case 2 -> startCellsLabel.setText(String.valueOf(val));
        }
    }

    private void addComponents(){
        menuWindow.add(heading);
        menuWindow.add(xAxis);
        menuWindow.add(xAxisName);
        menuWindow.add(xAxisLabel);
        menuWindow.add(yAxis);
        menuWindow.add(yAxisName);
        menuWindow.add(yAxisLabel);
        menuWindow.add(startCells);
        menuWindow.add(startCellsName);
        menuWindow.add(startCellsLabel);
        menuWindow.add(startButton);
        menuWindow.add(previewLabel);
        menuWindow.add(modeName);
        menuWindow.add(modeBox);
        menuWindow.add(helpButton);
    }

    private void addComboBoxEntry(JComboBox<String> comboBox, String entry) {
        comboBox.addItem(entry);
    }

    public void setVisibility(boolean visibility){
        menuWindow.getContentPane().removeAll();
        menuWindow.getContentPane().repaint();
        initComponents();
        addComponents();
        menuWindow.setVisible(visibility);
    }


    public void setFocus(){
        menuWindow.requestFocus();
    }

}
