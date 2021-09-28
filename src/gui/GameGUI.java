package gui;

import javax.swing.*;

public class GameGUI {

    private JFrame jf;

    public GameGUI(){

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


        //initComponents();
        //addComponents();


        jf.setLocationRelativeTo(null);
        jf.setVisible(true);


    }

}
