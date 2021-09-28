package game;

import gui.GUI;

import java.util.concurrent.ThreadLocalRandom;

public class Control {

    private GUI gui;

    private int xSize, ySize, cellCount, gen = 0;

    private boolean[][] cells;
    private boolean[][] newcells;

    public Control(){

        gui = new GUI(this);
        gui.buildControlWindow();

    }

    public void buildGameWindow(int xSize, int ySize, int cellCount){
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellCount = cellCount;

        cells = new boolean[this.xSize][this.ySize];
        newcells = new boolean[this.xSize][this.ySize];

        gui.buildGameWindow();
    }

    private void nextGen(){
        gen ++;
        System.out.println("Generation:" + gen);

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++) {
                int n = aliveNeigbours(x, y);

                if (n == 3 && !cells[x][y]){
                    newcells[x][y] = true;
                }

                if (n < 2){
                    newcells[x][y] = false;
                }

                if (n == 2 || n == 3){

                }

                if (n > 3){
                    newcells[x][y] = false;
                }

            }
        }

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = newcells[x][y];
            }
        }

    }


    private int aliveNeigbours(int x, int y){
        int count = 0;

        int[] xoff = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] yoff = {0, 1, 1, 1, 0, -1, -1, -1};

        for (int i = 0; i < 8; i++){
            try {
                if (cells[x + xoff[i]][y + yoff[i]]){
                    count ++;
                }
            } catch (Exception e) {
            }
        }
        return count;
    }


    private int rand(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }


}



