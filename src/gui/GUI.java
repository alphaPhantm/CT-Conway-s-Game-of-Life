package gui;

import game.Control;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private  final String title = "Conway's game if Live";

    private final int offset = 10;

    private final Control control;

    private MenuWindow menuWindow;
    private GameWindow gameWindow;
    private ControlWindow controlWindow;

    public GUI(Control control){
        this.control = control;
    }

    public void createMenuWindow(){
        menuWindow = new MenuWindow(title, control);
    }


    public void createGameWindow(int cellCountX, int cellCountY, int width, int height){
        gameWindow = new GameWindow(title, control, cellCountX, cellCountY, width, height);
    }

    public void createControlWindow(){
        controlWindow = new ControlWindow(gameWindow, offset);
    }

    public void showGrid(boolean[][] grid){
        gameWindow.showGrid(grid);
    }

    public void setVisibility(boolean value){
        controlWindow.setVisibility(value);
    }

    public ControlWindow getControlWindow() {
        return controlWindow;
    }

}
