package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class StartGUI{

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
    private JSlider startCells;
    private JLabel startCellsName;
    private JLabel startCellsLabel;

    private MouseListener hover;

    private JButton randomStart;
    private JButton manuelStart;


    public StartGUI(){

        jf = new JFrame();

        jf.setTitle("CT-Conway's Game of Life");

        ImageIcon imageIcon = new ImageIcon("src/data/icon.png");
        jf.setIconImage(imageIcon.getImage());

        //getContentPane().setBackground(Color.decode("#121212"));

        jf.setUndecorated(false);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setAutoRequestFocus(false);
        jf.setLayout(null);
        jf.setSize(752, 424);
        //jf.getContentPane().setPreferredSize(new Dimension(752, 423));

        jf.setResizable(false);


        initComponents();
        addComponents();


        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

    public void initComponents(){

        Font head = new Font("Segoe UI Light", Font.PLAIN, 24);
        Font text = new Font("Segoe UI Light", Font.PLAIN, 16);

        heading = new JLabel("Conway's Game of Life Control");
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
                int value = startCells.getValue();
                updateSliderLables(2, value);
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
                b.setContentAreaFilled(true);
                b.setBackground(Color.decode("#121212"));
                b.setForeground(Color.decode("#F8F8FF"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                JButton b = (JButton) e.getSource();
                b.setForeground(Color.decode("#121212"));
                b.setContentAreaFilled(false);

            }
        };

        randomStart = new JButton();
        randomStart.setText("Random Start");
        randomStart.setFont(text);
        randomStart.setForeground(Color.decode("#121212"));
        randomStart.setBorderPainted(false);
        randomStart.setFocusPainted(false);
        randomStart.setContentAreaFilled(false);
        randomStart.setBounds(50, 280, 150, 50);
        randomStart.setVisible(true);
        randomStart.addMouseListener(hover);

        manuelStart = new JButton();
        manuelStart.setText("Manuel Start");
        manuelStart.setFont(text);
        manuelStart.setForeground(Color.decode("#121212"));
        manuelStart.setBorderPainted(false);
        manuelStart.setFocusPainted(false);
        manuelStart.setContentAreaFilled(false);
        manuelStart.setBounds(225, 280, 150, 50);
        manuelStart.setVisible(true);
        manuelStart.addMouseListener(hover);


        manuelStart.setBounds(225, 280, 150, 50);

        Icon gif = new ImageIcon("src/data/control.gif");
        JLabel gifLabel = new JLabel(gif);
        gifLabel.setBounds(391, 25, 336, 336);
        jf.getContentPane().add(gifLabel);

    }

    private void updateStartCells() {

        startCells.setMaximum(xAxisSize * yAxisSize);

    }

    private void updateSliderLables(int slider, int val){
        switch (slider){
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


    public void addComponents(){
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

    }



}
