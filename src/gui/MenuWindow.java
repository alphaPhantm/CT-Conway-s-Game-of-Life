package gui;

import game.Control;
import game.StartMode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class MenuWindow {

    private final JFrame menuWindow;

    private JLabel heading;
    private int xAxisSize = 6;
    private JSlider xAxis;
    private JLabel xAxisName;
    private JTextField xAxisLabel;
    private int yAxisSize = 6;
    private JSlider yAxis;
    private JLabel yAxisName;
    private JLabel yAxisLabel;
    private int cellCount = 6;
    private JSlider startCells;
    private JLabel startCellsName;
    private JLabel startCellsLabel;

    private JLabel modeName;
    private JComboBox<String> modeBox;

    private MouseListener hover;

    private JButton startButton;
    private JButton manuelStart;

    private ImageIcon preview;
    private JLabel previewLabel;

    private boolean sliderLocked = false;

    public MenuWindow(String title, Control control){
        menuWindow = new JFrame();

        menuWindow.setTitle(title);

        ImageIcon imageIcon = new ImageIcon("src/data/PNG/Icon.png");
        menuWindow.setIconImage(imageIcon.getImage());


        menuWindow.setUndecorated(false);

        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWindow.setAutoRequestFocus(false);
        menuWindow.setLayout(null);
        menuWindow.setSize(752, 424);

        menuWindow.setResizable(false);


        initComponents(control);
        addComponents();


        menuWindow.setLocationRelativeTo(null);
        menuWindow.setVisible(true);
    }

    private void initComponents(Control control) {
        Font head = new Font("Segoe UI Light", Font.PLAIN, 24);
        Font text = new Font("Segoe UI Light", Font.PLAIN, 16);

        heading = new JLabel("Control Panel");
        heading.setFont(head);
        heading.setForeground(Color.decode("#121212"));
        heading.setBounds(50, 25, 400, 50);
        heading.setVisible(true);

        int xAxisMin = 6;
        int xAxisMax = 1024;
        xAxis = new JSlider(xAxisMin, xAxisMax);
        xAxis.setBounds(180, 110, 200, 20);
        xAxis.setFont(text);
        xAxis.setForeground(Color.decode("#121212"));
        xAxis.setValue(6);
        xAxis.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!sliderLocked) {
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

                sliderLocked = true;
                String s = xAxisLabel.getText();
                xAxis.setValue(Integer.parseInt(s));
                sliderLocked = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        int yAxisMin = 6;
        int yAxisMax = 1024;
        yAxis = new JSlider(yAxisMin, yAxisMax);
        yAxis.setBounds(180, 160, 200, 20);
        yAxis.setFont(text);
        yAxis.setForeground(Color.decode("#121212"));
        yAxis.setValue(6);
        yAxis.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                yAxisSize = yAxis.getValue();
                updateSliderLabels(1, yAxisSize);
                updateStartCells();
            }
        });
        yAxis.setVisible(true);

        yAxisName = new JLabel("Höhe in Zellen:");
        yAxisName.setBounds(50, 150, 200, 30);
        yAxisName.setFont(text);
        yAxisName.setForeground(Color.decode("#121212"));
        yAxisName.setVisible(true);

        yAxisLabel = new JLabel("6");
        yAxisLabel.setBounds(180, 130, 200, 30);
        yAxisLabel.setFont(text);
        yAxisLabel.setForeground(Color.decode("#121212"));
        yAxisLabel.setVisible(true);
        //TODO: Inputfield witch Changelistner to cange Slider

        startCells = new JSlider(6, 6);
        startCells.setBounds(180, 210, 200, 20);
        startCells.setFont(text);
        startCells.setForeground(Color.decode("#121212"));
        startCells.setValue(6);
        startCells.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cellCount = startCells.getValue();
                updateSliderLabels(2, cellCount);
            }
        });
        startCells.setVisible(true);

        startCellsName = new JLabel("Start Zellen:");
        startCellsName.setBounds(50, 200, 200, 30);
        startCellsName.setFont(text);
        startCellsName.setForeground(Color.decode("#121212"));
        startCellsName.setVisible(true);

        startCellsLabel = new JLabel("6");
        startCellsLabel.setBounds(180, 180, 200, 30);
        startCellsLabel.setFont(text);
        startCellsLabel.setForeground(Color.decode("#121212"));
        startCellsLabel.setVisible(true);

        updateStartCells();

        hover = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                JButton b = (JButton) e.getSource();
                b.setForeground(Color.decode("#C40233"));
                b.setBounds(b.getX() - 10, b.getY() - 10, b.getWidth() + 20, b.getHeight() + 20);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                JButton b = (JButton) e.getSource();
                b.setForeground(Color.decode("#121212"));
                b.setBounds(b.getX() + 10, b.getY() + 10, b.getWidth() - 20, b.getHeight() - 20);

            }
        };

        modeName = new JLabel("Select Figure: ");
        modeName.setBounds(50, 250, 200, 30);
        modeName.setFont(text);
        modeName.setForeground(Color.decode("#121212"));
        modeName.setVisible(true);


        modeBox = new JComboBox<String>();
        modeBox.setBounds(180, 257, 100, 20);
        addComboBoxEntry(modeBox, "Randomized");
        addComboBoxEntry(modeBox, "Task1");
        addComboBoxEntry(modeBox, "Blinker");
        addComboBoxEntry(modeBox, "Toad");


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

                StartMode startMethod = StartMode.Task1;

                int index = modeBox.getSelectedIndex();

                startMethod = StartMode.values()[index + 1]; // + 1 BECAUSE MANUEL HAS THE FIRST INDEX IN ENUM

                control.buildGameWindow(xAxisSize, yAxisSize, cellCount, startMethod);
            }
        });

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
                control.buildGameWindow(xAxisSize, yAxisSize, cellCount, StartMode.Manuel);
            }
        });


        preview = new ImageIcon("src/data/PNG/Preview.png");
        previewLabel = new JLabel(preview);
        previewLabel.setBounds(450, 45, 203, 302);

    }

    private void updateStartCells() {
        startCells.setMaximum(xAxisSize * yAxisSize);
    }

    private void updateSliderLabels(int slider, int val) {
        switch (slider) {
            case 0:
                xAxisLabel.setText(String.valueOf(val));
                break;
            case 1:
                yAxisLabel.setText(String.valueOf(val));
                break;
            case 2:
                startCellsLabel.setText(String.valueOf(val));
                break;
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

}