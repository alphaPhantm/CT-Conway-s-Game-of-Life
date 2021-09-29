package game;

import gui.GUI;

import java.util.concurrent.ThreadLocalRandom;

public class Control {

    private GUI gui;
    private Clock clock;

    private int xSize, ySize, cellCount, gen = 0;

    private boolean[][] cells;
    private boolean[][] newcells;


    public void start() {

        gui = new GUI(this);
        gui.controlWindow();

    }

    public void startGame() {
        clock = new Clock();
        clock.start(this, gui);
    }

    public void buildGameWindow(int xSize, int ySize, int cellCount, StartMode startMode) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellCount = cellCount;

        cells = new boolean[this.xSize][this.ySize];
        newcells = new boolean[this.xSize][this.ySize];


        switch (startMode) {

            case Task1 -> {
                generateTask1();
            }
            case Randomized -> {
                generateRandomized();
            }
            case Manuel -> {
                buildManuelGame();
            }

        }

        if (gui == null){
            gui = new GUI(this);
        }

        gui.gameWindow(this.xSize, this.ySize);

        startGame();

    }

    private void buildManuelGame() {
        System.out.println("Test");
    }

    private void generateTask1() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = true;
            }
        }

//        cells[1][2] = true;
//        cells[2][2] = true;
//        cells[2][4] = true;
//        cells[3][1] = true;
//        cells[3][3] = true;
//        cells[4][3] = true;

//        cells[1][0] = true;
//        cells[1][1] = true;
//        cells[1][2] = true;


        syncCells();
    }

    private void generateRandomized() {
        for (int i = 0; i < this.cellCount; i++) {
            int x = rand(0, this.xSize);
            int y = rand(0, this.ySize);
            if (!cells[x][y]) {
                cells[x][y] = true;
            } else {
                i--;
            }
        }
        syncCells();

    }

    private void syncCells() {
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                newcells[x][y] = cells[x][y];
            }
        }
    }

    public boolean[][] getCells() {
        return cells;
    }

    public boolean[][] nextGen() {
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

        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                cells[x][y] = newcells[x][y];
            }
        }

        return cells;
    }


    private int aliveNeigbours(int x, int y) {
        int count = 0;

        int[] xoff = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] yoff = {0, 1, 1, 1, 0, -1, -1, -1};

        for (int i = 0; i < 8; i++) {
            try {
                if (cells[x + xoff[i]][y + yoff[i]]) {
                    count++;
                }
            } catch (Exception e) {
            }
        }
        return count;
    }


    private int rand(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }


    public void showGrid() {

    }
}



