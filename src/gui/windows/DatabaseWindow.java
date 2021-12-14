package gui.windows;

import gamecontrol.Control;
import gui.fundamental.Hover;
import gui.fundamental.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class DatabaseWindow extends GUI {

    private JFrame databaseWindow;

    private final Control control;

    private JLabel[] names;
    private JLabel[] widths;
    private JLabel[] heights;
    private JLabel[] generations;
    private JLabel[] max_cellcounts;
    private JButton[] deleteButtons;

    private JLabel heading;
    private JLabel name_heading;
    private JLabel width_heading;
    private JLabel height_heading;
    private JLabel generation_heading;
    private JLabel max_cellcount_heading;

    private List<String> allGrids;


    public DatabaseWindow(Control control) {
        super(control);
        this.control = control;
        initPublicComponents();

        databaseWindow = new JFrame();

        databaseWindow.setTitle(this.title + " Database Control");

        ImageIcon imageIcon = new ImageIcon("src/assets/icons/Icon.png");
        databaseWindow.setIconImage(imageIcon.getImage());


        databaseWindow.setUndecorated(false);


        databaseWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        databaseWindow.setAutoRequestFocus(false);
        databaseWindow.setLayout(null);
        databaseWindow.setSize(1400, getAllGrids().size() * 75 + 200);


        databaseWindow.setResizable(true);

        databaseWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                databaseWindow.dispose();

                control.getMenuWindow().setVisibility(true);
                control.getMenuWindow().setFocus();
            }
        });


        initComponents();
        addComponents();


        databaseWindow.setLocationRelativeTo(null);
        databaseWindow.setVisible(true);
        databaseWindow.requestFocus();


    }

    private void initComponents() {

        initHeading();

        allGrids = control.getAllGrids();

        names = new JLabel[allGrids.size()];
        widths = new JLabel[allGrids.size()];
        heights = new JLabel[allGrids.size()];
        generations = new JLabel[allGrids.size()];
        max_cellcounts = new JLabel[allGrids.size()];
        deleteButtons = new JButton[allGrids.size()];

        for (int i = 0; i < allGrids.size(); i++) {
            String[] attributes = control.getGameInfo(allGrids.get(i), new String[]{"NAME", "WIDTH", "HEIGHT", "GENERATION", "MAX_CELLCOUNT"});

            names[i] = new JLabel(attributes[0]);
            names[i].setBounds(50, 75 * i + 150, 200, 30);
            names[i].setFont(text);
            names[i].setForeground(Color.decode("#121212"));
            names[i].setVisible(true);

            widths[i] = new JLabel(attributes[1]);
            widths[i].setBounds(300, 75 * i + 150, 200, 30);
            widths[i].setFont(text);
            widths[i].setForeground(Color.decode("#121212"));
            widths[i].setVisible(true);

            heights[i] = new JLabel(attributes[2]);
            heights[i].setBounds(550, 75 * i + 150, 200, 30);
            heights[i].setFont(text);
            heights[i].setForeground(Color.decode("#121212"));
            heights[i].setVisible(true);

            generations[i] = new JLabel(attributes[3]);
            generations[i].setBounds(800, 75 * i + 150, 200, 30);
            generations[i].setFont(text);
            generations[i].setForeground(Color.decode("#121212"));
            generations[i].setVisible(true);

            max_cellcounts[i] = new JLabel(attributes[4]);
            max_cellcounts[i].setBounds(1050, 75 * i + 150, 200, 30);
            max_cellcounts[i].setFont(text);
            max_cellcounts[i].setForeground(Color.decode("#121212"));
            max_cellcounts[i].setVisible(true);

            deleteButtons[i] = new JButton();
            deleteButtons[i].setText("Delete");
            deleteButtons[i].setFont(text);
            deleteButtons[i].setForeground(Color.decode("#121212"));
            deleteButtons[i].setBorderPainted(true);
            deleteButtons[i].setFocusPainted(false);
            deleteButtons[i].setContentAreaFilled(false);
            deleteButtons[i].setBounds(1300, 75 * i + 150, 50, 30);
            deleteButtons[i].setVisible(true);
            deleteButtons[i].addMouseListener(new Hover());
            deleteButtons[i].setBorder(new RoundedBorder(25));
            deleteButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    control.deleteEntry(attributes[0]);
                    update();
                }
            });

        }

    }

    private void update(){
        databaseWindow.getContentPane().removeAll();
        databaseWindow.getContentPane().repaint();
        initComponents();
        databaseWindow.setSize(1400, getAllGrids().size() * 75 + 200);
        addComponents();
    }


    private void initHeading() {

        heading = new JLabel("Control over the Database");
        heading.setBounds(50, 25, 300, 30);
        heading.setFont(head);
        heading.setForeground(Color.decode("#121212"));
        heading.setVisible(true);

        name_heading = new JLabel("Grid Name");
        name_heading.setBounds(50, 75, 200, 30);
        name_heading.setFont(sub_head);
        name_heading.setForeground(Color.decode("#121212"));
        name_heading.setVisible(true);

        width_heading = new JLabel("Grid Width");
        width_heading.setBounds(300, 75, 200, 30);
        width_heading.setFont(sub_head);
        width_heading.setForeground(Color.decode("#121212"));
        width_heading.setVisible(true);

        height_heading = new JLabel("Grid height");
        height_heading.setBounds(550, 75, 200, 30);
        height_heading.setFont(sub_head);
        height_heading.setForeground(Color.decode("#121212"));
        height_heading.setVisible(true);

        generation_heading = new JLabel("Generation");
        generation_heading.setBounds(800, 75, 200, 30);
        generation_heading.setFont(sub_head);
        generation_heading.setForeground(Color.decode("#121212"));
        generation_heading.setVisible(true);

        max_cellcount_heading = new JLabel("Max Cellcount");
        max_cellcount_heading.setBounds(1050, 75, 200, 30);
        max_cellcount_heading.setFont(sub_head);
        max_cellcount_heading.setForeground(Color.decode("#121212"));
        max_cellcount_heading.setVisible(true);

    }

    private void addComponents() {

        databaseWindow.add(heading);
        databaseWindow.add(name_heading);
        databaseWindow.add(width_heading);
        databaseWindow.add(height_heading);
        databaseWindow.add(generation_heading);
        databaseWindow.add(max_cellcount_heading);

        for (int i = 0; i < names.length; i++) {
            databaseWindow.add(names[i]);
            databaseWindow.add(widths[i]);
            databaseWindow.add(heights[i]);
            databaseWindow.add(generations[i]);
            databaseWindow.add(max_cellcounts[i]);
            databaseWindow.add(deleteButtons[i]);
        }


    }

    public void setVisibility(boolean value) {
        databaseWindow.setVisible(value);
        databaseWindow.requestFocus();
    }
}
