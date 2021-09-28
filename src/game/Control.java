package game;

import gui.GUI;

public class Control {

    private GUI gui;

    private int xSize, ySize, cellCount;

    public Control(){

        gui = new GUI(this);

        gui.buildControlWindow();

    }

    public void buildGameWindow(int xSize, int ySize, int cellCount){
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellCount = cellCount;


        gui.buildGameWindow();
    }

}



