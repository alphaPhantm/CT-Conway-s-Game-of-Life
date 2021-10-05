package game;

import gui.GUI;

import java.util.concurrent.ThreadLocalRandom;

public class Control {

    private GUI gui;
    private Clock clock;

    private int xSize, ySize, cellCount, gen;

    private boolean[][] cells;
    private boolean[][] newcells;

    private int x = 0;
    private int y = 0;

    private int width, height;


    public void start() {

        clock = new Clock(this, gui, 1000);
        gui = new GUI(this);
        gui.createMenuWindow();

    }

    public void startGame() {
        clock.start();
    }

    public void buildGameWindow(int xSize, int ySize, int cellCount, StartMode startMode) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellCount = cellCount;

        gen = 0;

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


        calcSize();

        gui.createGameWindow(this.xSize, this.ySize, this.width, this.height);
        setRunning(true);

        if (startMode == StartMode.Manuel){
            setRunning(false);
            gui.buildControlWindow();
        }

        startGame();

    }

    private void buildManuelGame() {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = false;
            }
        }

    }

    private void generateTask1() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = false;
            }
        }


//        cells[1][2] = true;
//        cells[2][2] = true;
//        cells[2][4] = true;
//        cells[3][1] = true;
//        cells[3][3] = true;
//        cells[4][3] = true;

        cells[1][0] = true;
        cells[1][1] = true;
        cells[1][2] = true;


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
        checkCellCount();
        gen++;
        System.out.println("Generation:" + gen);

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                int n = aliveNeigbours(x, y);

                if (n == 3 && !cells[x][y]) {
                    newcells[x][y] = true;
                }

                if (n < 2) {
                    newcells[x][y] = false;
                }

                if (n == 2 || n == 3) {

                }

                if (n > 3) {
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


    public boolean[][] nextGen_GridTest() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = false;
            }
        }


        if (x == xSize) {
            x = 0;
            y++;
        }
        if (y == ySize) y = 0;


        cells[x][y] = true;
        x++;

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


    public void showGrid(boolean[][] grid) {
        gui.showGrid(grid);
    }


    public void setVisibility(boolean value){
        gui.setVisibility(value);
    }

    public void setCell(int mouseX, int mouseY, boolean value, int offset) {

        float ratio = (float) (xSize) / (float) (ySize);

        if (ySize > xSize) {

            x = (int) ((float) (mouseX - offset) / (800f / ((float) xSize / ratio)));
            y = (int) ((float) (mouseY - offset) / (800f / ((float) ySize)));

        } else {

            x = (int) ((float) (mouseX - offset) / (800f / ((float) xSize)));
            y = (int) ((float) (mouseY - offset) / (800f / ((float) ySize * ratio)));

        }

        if (x >= 0 && x < xSize && y >= 0 && y < ySize && (checkCellCount() || !value))
            cells[x][y] = value;

        syncCells();
        gui.showGrid(cells);
    }

    private void calcSize(){

        float ratio = (float) (xSize) / (float) (ySize);
        if (xSize > ySize){
            width = 800;
            height = (int) (width / ratio);
        } else {
            height = 800;
            width = (int) (height * ratio);
        }


    }

    private boolean checkCellCount() {

        int counter = 0;

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (cells[x][y])
                    counter++;
            }
        }

        return counter < cellCount;

    }

    public void setRunning(boolean state){
        clock.setRunning(state);
    }

    public boolean isRunning(){
        return clock.isRunning();
    }

    public void setVelocity(int velocity){
        clock.setVelocity(velocity);
    }
}



