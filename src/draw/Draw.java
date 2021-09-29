package draw;

import javax.swing.*;
import java.awt.*;

public class Draw extends JPanel {
    private int xSize, ySize;
    private float ratio;

    private Graphics2D g2d;
    private boolean[][] currentGrid;

    private boolean drawLocked = false;

    public Draw(int gridSizeX, int gridSizeY, float ratio) {
        this.xSize = gridSizeX;
        this.ySize = gridSizeY;
        this.ratio = ratio;
        currentGrid = new boolean[gridSizeX][gridSizeY];


    }

    public void showGrid(boolean[][] grid) {
        currentGrid = new boolean[xSize][ySize];

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                currentGrid[x][y] = grid[x][y];
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;

        float cellSizeX = 1024f / xSize;
        float cellSizeY = 1024f / ySize;

        if(ySize > xSize)
        {
            cellSizeX /= ratio;
        }
        else
        {
            cellSizeY *= ratio;
        }

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                if (currentGrid[x][y])
                {
                    g2d.fillRect((int) (x * cellSizeX), (int) (y * cellSizeY), (int) ((cellSizeX + 1)), (int) (cellSizeY + 1));
                }
            }
        }

        repaint();
    }
}