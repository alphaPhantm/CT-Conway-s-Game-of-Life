package gui;


import game.StartMode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class MenuWindow {

    private GUI gui;

    private final JFrame menuWindow;

    private Font head;
    private Font text;

    private JLabel heading;

    private final int xAxisMin = 6, xAxisMax = 1024;
    private final int yAxisMin = 6, yAxisMax = 1024;

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
    private JButton manuelStart;

    private ImageIcon preview;
    private JLabel previewLabel;

    public MenuWindow(String title, GUI gui){

        this.gui = gui;

        menuWindow = new JFrame();

        menuWindow.setTitle(title);

        ImageIcon imageIcon = new ImageIcon("src/data/icons/Icon.png");
        menuWindow.setIconImage(imageIcon.getImage());


        menuWindow.setUndecorated(false);

        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWindow.setAutoRequestFocus(false);
        menuWindow.setLayout(null);
        menuWindow.setSize(752, 424);

        menuWindow.setResizable(false);


        initComponents();
        addComponents();


        menuWindow.setLocationRelativeTo(null);
        menuWindow.setVisible(true);
    }

    private void initComponents() {

        this.head = gui.getHead();
        this.text = gui.getText();
        this.hover = gui.getHover();



        initHeading();
        initXAxis();
        initYAxis();
        initStartCells();
        initModeBox();
        initStartButton();
        initManuelStart();
        initIMG();

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
                if(!gui.isSliderLocked()) {
                    xAxisSize = xAxis.getValue();
                    updateSliderLabels(0, xAxisSize);
                    updateStartCells();
                }
            }
        });
        xAxis.setVisible(true);


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

                gui.setSliderLocked(true);
                String s = xAxisLabel.getText();
                xAxis.setValue(Integer.parseInt(s));
                xAxisSize = xAxis.getValue();
                updateStartCells();
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

    private void initYAxis(){
        yAxis = new JSlider(yAxisMin, yAxisMax);
        yAxis.setBounds(180, 160, 200, 20);
        yAxis.setFont(text);
        yAxis.setForeground(Color.decode("#121212"));
        yAxis.setValue(6);
        yAxis.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!gui.isSliderLocked()) {
                    yAxisSize = yAxis.getValue();
                    updateSliderLabels(1, yAxisSize);
                    updateStartCells();
                }
            }
        });
        yAxis.setVisible(true);

        yAxisName = new JLabel("Höhe in Zellen:");
        yAxisName.setBounds(50, 150, 200, 30);
        yAxisName.setFont(text);
        yAxisName.setForeground(Color.decode("#121212"));
        yAxisName.setVisible(true);

        yAxisLabel = new JTextField("6");
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

                gui.setSliderLocked(true);
                String s = yAxisLabel.getText();
                yAxis.setValue(Integer.parseInt(s));
                yAxisSize = yAxis.getValue();
                updateStartCells();
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

    private void initStartCells(){
        startCells = new JSlider(6, 6);
        startCells.setBounds(180, 210, 200, 20);
        startCells.setFont(text);
        startCells.setForeground(Color.decode("#121212"));
        startCells.setValue(6);
        startCells.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!gui.isSliderLocked()) {
                    cellCount = startCells.getValue();
                    updateSliderLabels(2, cellCount);
                    updateStartCells();
                }
            }
        });
        startCells.setVisible(true);

        startCellsName = new JLabel("Start Zellen:");
        startCellsName.setBounds(50, 200, 200, 30);
        startCellsName.setFont(text);
        startCellsName.setForeground(Color.decode("#121212"));
        startCellsName.setVisible(true);

        startCellsLabel = new JTextField("6");
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

                gui.setSliderLocked(true);
                String s = startCellsLabel.getText();
                startCells.setValue(Integer.parseInt(s));
                cellCount = startCells.getValue();
                gui.setSliderLocked(false);
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
        modeBox.setBounds(180, 257, 100, 20);
        addComboBoxEntry(modeBox, "Randomized");
        addComboBoxEntry(modeBox, "Task1");
        addComboBoxEntry(modeBox, "Blinker");
        addComboBoxEntry(modeBox, "Toad");

        for (int i = 0; i < gui.getAllGrids().size(); i++){
            addComboBoxEntry(modeBox, gui.getAllGrids().get(i));
        }

        modeName = new JLabel("Select Figure: ");
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
        startButton.setBounds(50, 310, 150, 50);
        startButton.setVisible(true);
        startButton.addMouseListener(hover);
        startButton.setBorder(new RoundedBorder(25));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = modeBox.getSelectedIndex();

                StartMode startMode = StartMode.values()[index + 1]; // + 1 BECAUSE MANUEL HAS THE FIRST INDEX IN ENUM

                gui.buildGameWindow(xAxisSize, yAxisSize, cellCount, startMode);

                menuWindow.setVisible(false);
            }
        });
    }

    private void initManuelStart(){
        manuelStart = new JButton();
        manuelStart.setText("Manuel Start");
        manuelStart.setFont(text);
        manuelStart.setForeground(Color.decode("#121212"));
        manuelStart.setBorderPainted(true);
        manuelStart.setFocusPainted(false);
        manuelStart.setContentAreaFilled(false);
        manuelStart.setBounds(225, 310, 150, 50);
        manuelStart.setVisible(true);
        manuelStart.addMouseListener(hover);
        manuelStart.setBorder(new RoundedBorder(25));
        manuelStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.buildGameWindow(xAxisSize, yAxisSize, cellCount, StartMode.Manuel);
                menuWindow.setVisible(false);
            }
        });
    }

    private void initIMG(){
        preview = new ImageIcon("src/data/pictures/Preview.png");
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
        menuWindow.add(manuelStart);
        menuWindow.add(previewLabel);
        menuWindow.add(modeName);
        menuWindow.add(modeBox);
    }

    private void addComboBoxEntry(JComboBox<String> comboBox, String entry) {
        comboBox.addItem(entry);
    }

    public void setVisibility(boolean visibility){
        menuWindow.setVisible(visibility);
    }

    public void setFocus(){
        menuWindow.requestFocus();
    }

}
