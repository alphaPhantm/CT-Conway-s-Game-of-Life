package gui;

import game.Control;

public class GUI {

    private  final String title = "Conway's game if Live";

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

    public void createGameWindow(int cellCountX, int cellCountY){
        gameWindow = new GameWindow(title, control, cellCountX, cellCountY);
    }

    public void createControlWindow(){
        controlWindow = new ControlWindow();
    }

    public void showGrid(boolean[][] grid){
        gameWindow.showGrid(grid);
    }


}
