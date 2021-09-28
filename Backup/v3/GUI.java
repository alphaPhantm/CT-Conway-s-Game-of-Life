package v3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import v3.Control;

public class GUI extends JFrame {

    private JButton[][] buttons = new JButton[6][6];
    private JButton nextGen = new JButton();
    public Control c;

    public GUI(Control c){

        this.c = c;

        setTitle("Test");
        setUndecorated(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setLayout(null);
        setSize(500, 500);
        setResizable(true);


        initComponents();
        addComponents();


        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void initComponents() {

        nextGen.setBounds(0, 0, 100, 50);
        nextGen.setText("Next");
        nextGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.update();
            }
        });

        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 6; y++) {
                buttons[i][y] = new JButton();
                buttons[i][y].setBounds(i * 50 + 50, y * 50 + 50, 50, 50);
                int finalI = i;
                int finalY = y;
                buttons[i][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Objects.equals(buttons[finalI][finalY].getText(), "0")){
                            buttons[finalI][finalY].setText("1");
                            buttons[finalI][finalY].setBackground(Color.green);
                        } else {
                            buttons[finalI][finalY].setText("0");
                            buttons[finalI][finalY].setBackground(Color.yellow);
                        }

                        boolean[][] data = new boolean[6][6];

                        for (int i = 0; i < 6; i++) {
                            for (int y = 0; y < 6; y++) {
                                if (Objects.equals(buttons[i][y].getText(), "1")){
                                    data[i][y] = true;
                                } else {
                                    data[i][y] = false;
                                }
                            }
                        }

                        c.newData(data);
                    }
                });
            }
        }
    }
    private void addComponents() {
        add(nextGen);
        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 6; y++) {
                add(buttons[i][y]);
            }
        }

    }

    public void showGeneration(int[][] pGeneration) {
        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 6; y++) {
                buttons[i][y].setText(String.valueOf(pGeneration[y][i]));
                if (Objects.equals(buttons[i][y].getText(), "0")){
                    buttons[i][y].setBackground(Color.green);
                } else {
                    buttons[i][y].setBackground(Color.yellow);
                }
            }
        }
    }
}


