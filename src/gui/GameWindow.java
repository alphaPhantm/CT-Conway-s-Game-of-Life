package gui;

import draw.Draw;
import game.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow {

    private final JFrame jfGame;
    private final Draw draw;

    private final int cellCountX;
    private final int cellCountY;

    private int width, height;
    private final float ratio;

    private boolean mouseLeft = false, mouseRight = false;

    public GameWindow(String title, Control control, int cellCountX, int cellCountY) {
        this.cellCountX = cellCountX;
        this.cellCountY = cellCountY;


        jfGame = new JFrame(title);

        jfGame.getContentPane().setPreferredSize(new Dimension(800, 800));
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

        jfGame.getContentPane().addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseLeft) {
                    control.setCell(e.getX(), e.getY(), true);
                } else if (mouseRight) {
                    control.setCell(e.getX(), e.getY(), false);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        jfGame.getContentPane().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                switch (e.getButton()) {
                    case 1 -> {
                        mouseLeft = true;
                        control.setCell(e.getX(), e.getY(), true);
                    }
                    case 3 -> {
                        mouseRight = true;
                        control.setCell(e.getX(), e.getY(), false);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()) {
                    case 1 -> mouseLeft = false;
                    case 3 -> mouseRight = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        jfGame.setResizable(false);

        ratio = (float) (cellCountY) / (float) (cellCountX);
        draw = new Draw(this.cellCountX, this.cellCountY, 800, 800, ratio);
        draw.setBounds(0, 0, 800, 800);

        draw.setVisible(true);

        jfGame.add(draw);

        jfGame.pack();
        jfGame.setLayout(null);
        jfGame.setLocationRelativeTo(null);
        jfGame.setVisible(true);
    }

    public void showGrid(boolean[][] grid) {
        if (draw != null) {
            draw.showGrid(grid);
        } else {
            System.err.println("you need to build the game gameWindow before showing a grid");
        }
    }



}
