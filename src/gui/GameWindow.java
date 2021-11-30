package gui;

import draw.Draw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow {

    private final JFrame gameWindow;
    private final Draw draw;

    private final int cellCountX;
    private final int cellCountY;

    private String title;

    private int width, height;
    private final float ratio;

    private boolean mouseLeft = false, mouseRight = false;

    public GameWindow(String title, GUI gui, int cellCountX, int cellCountY, int width, int height, int offset) {
        this.cellCountX = cellCountX;
        this.cellCountY = cellCountY;

        this.title = title;

        gameWindow = new JFrame(title + " Generation: " + gui.getGeneration());

        gameWindow.getContentPane().setPreferredSize(new Dimension(width + (2 * offset), height + (2 * offset)));
        gameWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon("src/files/PNG/Icon.png");
        gameWindow.setIconImage(imageIcon.getImage());

        gameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                if (gui.getControlWindow() != null){
                    gui.getControlWindow().dispose();
                }
                gameWindow.dispose();
                gui.setRunning(false);
                gui.getMenuWindow().setVisibility(true);
                gui.getMenuWindow().setFocus();
            }
        });

        gameWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.isAltDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    if (gui.getControlWindow() == null){
                        gui.buildControlWindow();
                    } else {
                        gui.setVisibility(true);
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
                    gui.setCell(e.getX(), e.getY(), true);
                } else if (mouseRight) {
                    gui.setCell(e.getX(), e.getY(), false);
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
                        gui.setCell(e.getX(), e.getY(), true);
                    }
                    case 3 -> {
                        mouseRight = true;
                        gui.setCell(e.getX(), e.getY(), false);
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
        draw = new Draw(this.cellCountX, this.cellCountY, width, height, ratio, offset);
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
    public void updateTitle(int gen){
        gameWindow.setTitle(title + " Generation: " + gen);
    }


}
