package gui.windows;

import gamecontrol.Control;
import gui.draw.DrawGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * In this class the game is shown.
 * @author Noah Kessinger
 */
public class GameWindow extends GUI {

    private final JFrame gameWindow;
    private final DrawGrid drawGrid;

    private final int cellCountX;
    private final int cellCountY;


    private int width, height;
    private final float ratio;

    private boolean mouseLeft = false, mouseRight = false;

    public GameWindow(Control control, int cellCountX, int cellCountY, int width, int height) {
        super(control);
        this.cellCountX = cellCountX;
        this.cellCountY = cellCountY;

        initPublicComponents();

        gameWindow = new JFrame(title + " | Generation: " + getGeneration());
        gameWindow.requestFocus();

        gameWindow.getContentPane().setPreferredSize(new Dimension(width + (2 * offset), height + (2 * offset)));
        gameWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(iconPath);
        gameWindow.setIconImage(imageIcon.getImage());

        gameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (control.getControlWindow() != null){
                    control.getControlWindow().dispose();
                }
                gameWindow.dispose();
                setRunning(false);
                control.getMenuWindow().setVisibility(true);
                control.getMenuWindow().setFocus();
            }
        });

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
                        control.setControlVisibility(true);
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
                    setCell(e.getX(), e.getY(), true);
                } else if (mouseRight) {
                    setCell(e.getX(), e.getY(), false);
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
                        setCell(e.getX(), e.getY(), true);
                    }
                    case 3 -> {
                        mouseRight = true;
                        setCell(e.getX(), e.getY(), false);
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
        drawGrid = new DrawGrid(this.cellCountX, this.cellCountY, width, height, ratio, offset);
        drawGrid.setBounds(0, 0, width, height);



        drawGrid.setVisible(true);

        gameWindow.add(drawGrid);

        gameWindow.pack();
        gameWindow.setLayout(null);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }


    public void showGrid(boolean[][] grid) {
        if (drawGrid != null) {
            drawGrid.showGrid(grid);
        } else {
            System.err.println("you need to build the game gameWindow before showing a grid");
        }
    }

    public Point getPos(){
        return gameWindow.getLocation();
    }
    public void updateTitle(int gen){
        gameWindow.setTitle(title + " | Generation: " + gen);
    }


}
