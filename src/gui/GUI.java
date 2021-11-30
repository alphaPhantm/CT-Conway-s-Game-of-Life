package gui;

import game.Control;
import game.StartMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {

    private  final String title = "Conway's game if Live";

    private final Control control;

    private Font head;
    private Font text;
    private MouseAdapter hover;
    private boolean sliderLocked = false;

    private final int offset = 20;

    private MenuWindow menuWindow;
    private GameWindow gameWindow;
    private ControlWindow controlWindow;

    public GUI(Control control){
        this.control = control;

        initPublicComponents();
    }

    public void initPublicComponents(){

        head = new Font("Segoe UI Light", Font.PLAIN, 24);
        text = new Font("Segoe UI Light", Font.PLAIN, 16);

        hover = new MouseAdapter() {
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
        };



    }

    public void createMenuWindow(){
        menuWindow = new MenuWindow(title, this);
    }

    public void createGameWindow(int cellCountX, int cellCountY, int width, int height){
        gameWindow = new GameWindow(title, this, cellCountX, cellCountY, width, height, offset);
    }

    public void createControlWindow(){
        controlWindow = new ControlWindow(gameWindow, this, offset);
    }

    public void showGrid(boolean[][] grid){
        gameWindow.showGrid(grid);
    }

    public void buildGameWindow(int xSize, int ySize, int cellCount, StartMode startMode){
        control.buildGameWindow(xSize, ySize, cellCount, startMode);
    }

    public void buildControlWindow(){
        createControlWindow();
    }

    public void setCell(int mouseX, int mouseY, boolean value){
        control.setCell(mouseX, mouseY, value, offset);
    }

    public void setRunning(boolean state){
        control.setRunning(state);
    }

    public boolean isRunning(){
        return control.isRunning();
    }

    public MenuWindow getMenuWindow(){
        return menuWindow;
    }

    public void setVelocity(int velocity){
        control.setVelocity(velocity);
    }

    public Font getHead() {
        return head;
    }

    public Font getText() {
        return text;
    }

    public boolean isSliderLocked() {
        return sliderLocked;
    }

    public void setSliderLocked(boolean sliderLocked) {
        this.sliderLocked = sliderLocked;
    }

    public MouseAdapter getHover() {
        return hover;
    }

    public void nextGen(){
        control.nextGen();
    }

    public boolean[][] getCells(){ return control.getCells(); }

    public boolean isMouseLocked(){
        return control.isMouseLocked();
    }

    public void setMouseLocked(boolean value){
        control.setMouseLocked(value);
    }

    public void clearGrid(){ control.clearGrid(); }

    public void multipleGenSkip(int generations){
        control.multipleGenSkip(generations);
    }

    public int getGeneration(){
        return control.getGeneration();
    }

    public void updateGameWindowTitle(int gen){
        gameWindow.updateTitle(gen);
    }

    public int getVelocity(){
        return control.getVelocity();
    }

    //TODO: Delet this Methods. Those are shit.
    public void setVisibility(boolean value){
        controlWindow.setVisibility(value);
    }

    public ControlWindow getControlWindow() {
        return controlWindow;
    }


}
