package gui;

import game.Control;
import game.StartMode;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private  final String title = "Conway's game if Live";

    private final Control control;

    private final int offset = 10;

    private MenuWindow menuWindow;
    private GameWindow gameWindow;
    private ControlWindow controlWindow;

    public GUI(Control control){
        this.control = control;
    }

    public void createMenuWindow(){
        menuWindow = new MenuWindow(title, this);
    }

    public void createGameWindow(int cellCountX, int cellCountY, int width, int height){
        gameWindow = new GameWindow(title, this, cellCountX, cellCountY, width, height);
    }

    public void createControlWindow(){
        controlWindow = new ControlWindow(gameWindow, offset);
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
        control.setCell(mouseX, mouseY, value);
    }


    //TODO: Delet this Methods. Those are shit.
    public void setVisibility(boolean value){
        controlWindow.setVisibility(value);
    }

    public ControlWindow getControlWindow() {
        return controlWindow;
    }



}
