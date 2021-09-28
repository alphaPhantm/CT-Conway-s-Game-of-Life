package v5.gui;

import v5.draw.Draw;
import v5.game.Control;



public class GUI {

    JFrame jf;

    public static Draw d;

    public static final int WIDTH = 512, HEIGHT = 512, XOFF = 10, YOFF = 10;



    public void create(){
        jf = new JFrame("Game of Live");

        jf.getContentPane().setPreferredSize(new Dimension(Control.CELLCOUNT_X + 19, Control.CELLCOUNT_Y + 19));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setResizable(true);

        d = new Draw();
        d.setVisible(true);

        jf.add(d);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

}
