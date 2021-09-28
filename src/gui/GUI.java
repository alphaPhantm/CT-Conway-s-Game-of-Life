package gui;

import game.Control;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;


public class GUI {

    private final Control control;

    private JFrame jf;

    private JLabel heading;
    private int xAxisSize = 6;
    private JSlider xAxis;
    private JLabel xAxisName;
    private JLabel xAxisLabel;
    private int yAxisSize = 6;
    private JSlider yAxis;
    private JLabel yAxisName;
    private JLabel yAxisLabel;
    private int cellCount = 6;
    private JSlider startCells;
    private JLabel startCellsName;
    private JLabel startCellsLabel;

    private MouseListener hover;

    private JButton randomStart;
    private JButton manuelStart;

    private ImageIcon preview;
    private JLabel previewLabel;


    public GUI(Control control) {

        this.control = control;

    }

    public void buildControlWindow() {
        jf = new JFrame();

        jf.setTitle("Conway's Game of Life");

        ImageIcon imageIcon = new ImageIcon("src/data/PNG/Icon.png");
        jf.setIconImage(imageIcon.getImage());

        //getContentPane().setBackground(Color.decode("#121212"));

        jf.setUndecorated(false);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setAutoRequestFocus(false);
        jf.setLayout(null);
        jf.setSize(752, 424);

        jf.setResizable(false);


        initControlComponents();
        addControlComponents();


        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    private void initControlComponents() {

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
                xAxisSize = xAxis.getValue();
                updateSliderLables(0, xAxisSize);
                updateStartCells();
            }
        });
        xAxis.setVisible(true);

        xAxisName = new JLabel("Breite in Zellen:");
        xAxisName.setBounds(50, 100, 200, 30);
        xAxisName.setFont(text);
        xAxisName.setForeground(Color.decode("#121212"));
        xAxisName.setVisible(true);

        xAxisLabel = new JLabel("6");
        xAxisLabel.setBounds(180, 80, 200, 30);
        xAxisLabel.setFont(text);
        xAxisLabel.setForeground(Color.decode("#121212"));
        xAxisLabel.setVisible(true);


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
                updateSliderLables(1, yAxisSize);
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


        startCells = new JSlider(6, 6);
        startCells.setBounds(180, 210, 200, 20);
        startCells.setFont(text);
        startCells.setForeground(Color.decode("#121212"));
        startCells.setValue(6);
        startCells.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cellCount = startCells.getValue();
                updateSliderLables(2, cellCount);
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

        randomStart = new JButton();
        randomStart.setText("Random Start");
        randomStart.setFont(text);
        randomStart.setForeground(Color.decode("#121212"));
        randomStart.setBorderPainted(true);
        randomStart.setFocusPainted(false);
        randomStart.setContentAreaFilled(false);
        randomStart.setBounds(50, 280, 150, 50);
        randomStart.setVisible(true);
        randomStart.addMouseListener(hover);
        randomStart.setBorder(new RoundedBorder(25));
        randomStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.buildGameWindow(xAxisSize, yAxisSize, cellCount);
            }
        });

        manuelStart = new JButton();
        manuelStart.setText("Manuel Start");
        manuelStart.setFont(text);
        manuelStart.setForeground(Color.decode("#121212"));
        manuelStart.setBorderPainted(true);
        manuelStart.setFocusPainted(false);
        manuelStart.setContentAreaFilled(false);
        manuelStart.setBounds(225, 280, 150, 50);
        manuelStart.setVisible(true);
        manuelStart.addMouseListener(hover);
        manuelStart.setBorder(new RoundedBorder(25));
        manuelStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        preview = new ImageIcon("src/data/PNG/Preview.png");
        previewLabel = new JLabel(preview);
        previewLabel.setBounds(450, 45, 203, 302);

    }

    private void updateStartCells() {
        startCells.setMaximum(xAxisSize * yAxisSize);
    }

    private void updateSliderLables(int slider, int val) {
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

    private void addControlComponents() {
        jf.add(heading);
        jf.add(xAxis);
        jf.add(xAxisName);
        jf.add(xAxisLabel);
        jf.add(yAxis);
        jf.add(yAxisName);
        jf.add(yAxisLabel);
        jf.add(startCells);
        jf.add(startCellsName);
        jf.add(startCellsLabel);
        jf.add(randomStart);
        jf.add(manuelStart);
        jf.add(previewLabel);

    }

    public void buildGameWindow() {


    }

    private void initGameComponents() {
    }

    private void addGameComponents() {
    }


}
