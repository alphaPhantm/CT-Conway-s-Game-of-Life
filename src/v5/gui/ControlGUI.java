package v5.gui;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlGUI {

    public static final int WIDTH = 512, HEIGHT = 512, XOFF = 10, YOFF = 10;
    private JFrame jf;

    private JSlider gridSize;
    private JLabel gridSizeLabel;
    private JSlider startCells;

    private JButton start;

    public void create() {
        jf = new JFrame("Game of Live");
        jf.setSize(305, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);

        gridSize = new JSlider(6, 512);
        gridSize.setBounds(50, 50, 150, 30);
        gridSize.setValue(6);
        gridSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = gridSize.getValue();
                update(value);
            }
        });

        gridSizeLabel = new JLabel("6");
        gridSizeLabel.setBounds(50, 30, 30, 30);

        startCells = new JSlider();
        startCells.setBounds(50, 100, 150, 30);

        start = new JButton("start");
        start.setBounds(0, 0, 50, 10);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jf.add(start);
        jf.add(gridSize);
        jf.add(startCells);
        jf.add(gridSizeLabel);
        jf.setVisible(true);
    }


    private void update(int val){
        gridSizeLabel.setText(String.valueOf(val));
    }

}
