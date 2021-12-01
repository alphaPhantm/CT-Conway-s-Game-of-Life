package game;

import data.database.DataBase;
import gui.GUI;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Control {

    private GUI gui;
    private Clock clock;
    private DataBase dataBase;

    private int xSize, ySize, cellCount, gen;

    private boolean[][] startingCells;
    private boolean[][] cells;
    private boolean[][] newcells;

    private int x = 0;
    private int y = 0;

    private int width, height;

    private boolean mouseLocked;

    public void start() {

        clock = new Clock(this, gui);
        gui = new GUI(this);
        dataBase = new DataBase();
        gui.createMenuWindow();

    }

    public void startGame() {
        clock.start();
    }

    public void buildGameWindow(int xSize, int ySize, int cellCount, String startMode) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellCount = cellCount;

        clock.setFirstGen(true);

        gen = 0;
        mouseLocked = true;

        cells = new boolean[this.xSize][this.ySize];
        newcells = new boolean[this.xSize][this.ySize];
        startingCells = new boolean[this.xSize][this.ySize];


        switch (startMode) {

            case "Task1" -> {
                generateTask1();
            }
            case "Randomized" -> {
                generateRandomized();
            }
            case "Manuel" -> {
                buildManuelGame();
            }
            default -> {
                getFromDatabase(startMode);
            }
        }

        saveFirstGrid();

        calcSize();

        gui.createGameWindow(this.xSize, this.ySize, this.width, this.height);
        setRunning(true);

        if (startMode == "Manuel") {
            setRunning(false);
            gui.buildControlWindow();
            this.mouseLocked = false;
        }

        startGame();

    }

    private void buildManuelGame() {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = false;
            }
        }

        syncCells();

    }

    private void getFromDatabase(String name){

        cells = dataBase.getGrid(name);
        gen = dataBase.getGen(name);
        syncCells();

    }

    private void generateTask1() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = false;
            }
        }


        cells[1][2] = true;
        cells[2][2] = true;
        cells[2][4] = true;
        cells[3][1] = true;
        cells[3][3] = true;
        cells[4][3] = true;


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

    public void saveFirstGrid(){
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                startingCells[x][y] = cells[x][y];
            }
        }
    }

    private void resetCells(){
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                cells[x][y] = startingCells[x][y];
            }
        }
    }

    public boolean[][] getCells() {
        return cells;
    }

    public void nextGen() {

        gen++;

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                int n = aliveNeigbours(x, y);

                switch (n) {
                    case 3:
                        newcells[x][y] = true;
                        break;
                    case 2:
                        break;
                    default:
                        newcells[x][y] = false;
                        break;
                }

            }
        }

        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                cells[x][y] = newcells[x][y];
            }
        }
        gui.updateGameWindowTitle(gen);
    }

    public void nextGenLight() {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                int n = aliveNeigbours(x, y);

                switch (n) {
                    case 3:
                        newcells[x][y] = true;
                        break;
                    case 2:
                        break;
                    default:
                        newcells[x][y] = false;
                        break;
                }

            }
        }

        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                cells[x][y] = newcells[x][y];
            }
        }
    }

    public void previousGen(){
        if(gen > 0) {
            resetCells();
            syncCells();
            for (int i = 0; i < gen - 1; i++) {
                nextGenLight();
            }
            gen--;
            gui.updateGameWindowTitle(gen);
        }
    }

//    public void checkN(Boolean[][] arr, Boolean[][] futureGen, int columns, int rows) {
//        ArrayList<Thread> threads = new ArrayList<>();
//        final int NUM_OF_THREADS = 8; //Or can be passed as an argument
//        for (int tid = 0; tid < NUM_OF_THREADS; tid++) {
//            Integer thread_local_row_start = tid * rows / NUM_OF_THREADS;
//            Integer thread_local_row_end = (tid + 1) * rows / NUM_OF_THREADS;
//            Thread t = new Thread(() -> {
//                for (int row = thread_local_row_start; row < thread_local_row_end; row++) {
//                    for (int column = 0; column < columns; column++) {
//                        countNeighbors(arr, row, column, rows, columns);
//                        evaluateNeighbors(arr[row][column], futureGen[row][column]);
//                    }
//                }
//            });
//            t.start();
//            threads.add(t);
//        }
//        for (Thread t : threads) {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public boolean[][] nextGenBackup() {

        gen++;

//        System.out.println("Generation:" + gen);

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


    public void setVisibility(boolean value) {
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

        if ((x >= 0 && x < xSize && y >= 0 && y < ySize && (checkCellCount() || !value)) && !mouseLocked)
            cells[x][y] = value;

        syncCells();
        gui.showGrid(cells);
    }

    private void calcSize() {

        float ratio = (float) (xSize) / (float) (ySize);
        if (xSize > ySize) {
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

    public boolean isRunning() {
        return clock.isRunning();
    }

    public void setRunning(boolean state) {
        clock.setRunning(state);
    }

    public void setVelocity(int velocity) {
        clock.setVelocity(velocity);
    }

    public int getVelocity() {
        return clock.getVelocity();
    }

    public boolean isMouseLocked() {
        return mouseLocked;
    }

    public void setMouseLocked(boolean mouseLocked) {
        this.mouseLocked = mouseLocked;
    }

    public void clearGrid() {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = false;
            }
        }

        syncCells();

    }

    public void multipleGenSkip(int generations) {
        for (int i = 0; i < generations; i++) {
            nextGen();
        }
    }

    public int getGeneration(){
        return gen;
    }

    public void jump2Gen(int wantedtGen){
        if (wantedtGen <= gen){
            resetCells();
            syncCells();

            int counter = ((gen - wantedtGen) / (wantedtGen + 1) );

            for (int i = 0; i < wantedtGen; i++) {
                nextGenLight();
                gen = gen - counter;
                gui.updateGameWindowTitle(gen);

            }

            gen = wantedtGen;
            gui.updateGameWindowTitle(gen);

        } else {
            multipleGenSkip(wantedtGen - gen);
        }
    }

    public List<String> getAllGrids(){
        return dataBase.getAllGrids();
    }
}



