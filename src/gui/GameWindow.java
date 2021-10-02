package gui;

import draw.Draw;
import game.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow {

    private final JFrame jfGame;
    private final Draw draw;

    private final int cellCountX;
    private final int cellCountY;

    private int width, height;
    private float ratio;

    public GameWindow(String title, Control control, int cellCountX, int cellCountY) {
        this.cellCountX = cellCountX;
        this.cellCountY = cellCountY;

        calcWindowSize();

        jfGame = new JFrame(title);

        jfGame.getContentPane().setPreferredSize(new Dimension(width, height));
        jfGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jfGame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.isAltDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    control.createControlWindow();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        jfGame.setResizable(false);

        ratio = (float) (cellCountY) / (float) (cellCountX);
        draw = new Draw(this.cellCountX, this.cellCountY, width, height, ratio);
        draw.setBounds(0, 0, width, height);

        draw.setVisible(true);

        jfGame.add(draw);

        jfGame.pack();
        jfGame.setLayout(null);
        jfGame.setLocationRelativeTo(null);
        jfGame.setVisible(true);
    }

    public void showGrid(boolean[][] grid)
    {
        if(draw != null)
        {
            draw.showGrid(grid);
        }
        else
        {
            System.err.println("you need to build the game gameWindow before showing a grid");
        }
    }

    private void calcWindowSize(){

        width = (int) (cellCountX * ratio );
        height = (int) (cellCountY *  ratio);

    }
}
