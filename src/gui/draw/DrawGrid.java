package gui.draw;

import javax.swing.*;
import java.awt.*;

public class DrawGrid extends JPanel {
    private int xSize, ySize;
    private int width, height;
    private float ratio;
    private int offset;

    private Graphics2D g2d;
    private boolean[][] currentGrid;

    private boolean drawLocked = false;

    public DrawGrid(int gridSizeX, int gridSizeY, int width, int height, float ratio, int offset) {
        this.xSize = gridSizeX;
        this.ySize = gridSizeY;
        this.width = width;
        this.height = height;
        this.ratio = ratio;
        this.offset = offset;
        currentGrid = new boolean[gridSizeX][gridSizeY];


    }

    public void showGrid(boolean[][] grid) {
        currentGrid = new boolean[xSize][ySize];

        for (int x = 0; x < xSize; x++) {
            if (ySize >= 0) System.arraycopy(grid[x], 0, currentGrid[x], 0, ySize);
        }
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(offset));
        g2d.drawRect(offset / 2, offset / 2, width + offset, height + offset);

        g2d.setColor(Color.DARK_GRAY);

        float cellSizeX = 800f / xSize;
        float cellSizeY = 800f / ySize;

        if(ySize > xSize)
        {
            cellSizeX /= ratio;
        }
        else
        {
            cellSizeY *= ratio;
        }

        for (int y = 0; y < xSize; y++)
        {
            for (int x = 0; x < ySize; x++)
            {
                if (currentGrid[y][x])
                {
                    g2d.fillRect((int) ((y * cellSizeY) + offset), (int) ((x * cellSizeX) + offset), (int) ((cellSizeY + 1)), (int) (cellSizeX + 1));

                }
            }
        }

        repaint();
    }
}