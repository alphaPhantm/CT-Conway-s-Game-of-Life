package gui;

import draw.Draw;
import game.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow {

    private final JFrame gameWindow;
    private final Draw draw;

    private final int cellCountX;
    private final int cellCountY;

    private int width, height;
    private final float ratio;

    private boolean mouseLeft = false, mouseRight = false;

    public GameWindow(String title, Control control, int cellCountX, int cellCountY, int width, int height) {
        this.cellCountX = cellCountX;
        this.cellCountY = cellCountY;


        gameWindow = new JFrame(title);

        gameWindow.getContentPane().setPreferredSize(new Dimension(width, height));
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.isAltDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    if (control.getControlWindow() == null){
                        control.buildControlWindow();
                    } else {
                        control.setVisibility(true);
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        gameWindow.getContentPane().addMouseMotionListener(new MouseMotionListener() {

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

        gameWindow.getContentPane().addMouseListener(new MouseListener() {
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

        gameWindow.setResizable(false);

        ratio = (float) (cellCountY) / (float) (cellCountX);
        draw = new Draw(this.cellCountX, this.cellCountY, width, height, ratio);
        draw.setBounds(0, 0, width, height);

        draw.setVisible(true);

        gameWindow.add(draw);

        gameWindow.pack();
        gameWindow.setLayout(null);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }

    public void showGrid(boolean[][] grid) {
        if (draw != null) {
            draw.showGrid(grid);
        } else {
            System.err.println("you need to build the game gameWindow before showing a grid");
        }
    }

    public Point getPos(){
        return gameWindow.getLocation();
    }



}
