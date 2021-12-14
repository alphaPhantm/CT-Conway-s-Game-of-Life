package gui.fundamental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Hover extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        JButton b = (JButton) e.getSource();
        b.setForeground(Color.decode("#C40233"));
        b.setBounds(b.getX() - 10, b.getY() - 10, b.getWidth() + 20, b.getHeight() + 20);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        JButton b = (JButton) e.getSource();
        b.setForeground(Color.decode("#121212"));
        b.setBounds(b.getX() + 10, b.getY() + 10, b.getWidth() - 20, b.getHeight() - 20);

    }
}

