package v5.draw;

import v5.gui.GUI;
import v5.game.Control;

import javax.swing.*;
import java.awt.*;

public class Draw extends JLabel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g.setColor(Color.RED);
        g.drawRect(9 ,9, Control.CELLCOUNT_X + 1, Control.CELLCOUNT_Y + 1);


        repaint();

//        for (int x = 0; x < Control.CELLCOUNT_X; x++) {
//            for (int y = 0; y < Control.CELLCOUNT_Y; y++) {
//                if (Control.cells[x][y]) {
//                    g.setColor(Color.BLACK);
//                    g.fillRect(x + GUI.XOFF, y + GUI.YOFF, 1, 1);
//                }
//            }
//        }


        for (int x = 0; x < Control.CELLCOUNT_X; x++) {
            for (int y = 0; y < Control.CELLCOUNT_X; y++) {
                if (Control.cells[x][y]) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * 85 + GUI.XOFF, y * 85 + GUI.YOFF, 85, 85);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * 85 + GUI.XOFF, y * 85 + GUI.YOFF, 85, 85);
                }
            }
        }
    }
}