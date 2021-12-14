package gamecontrol;

import database.ReadDAO;
import database.WriteDAO;
import gui.windows.ControlWindow;
import gui.windows.DatabaseWindow;
import gui.windows.GameWindow;
import gui.windows.MenuWindow;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Control {

    private MenuWindow menuWindow;
    private GameWindow gameWindow;
    private ControlWindow controlWindow;
    private DatabaseWindow databaseWindow;


    private Clock clock;
    private ReadDAO readDAO;
    private WriteDAO writeDAO;

    private int xSize, ySize, cellCount, gen;

    private boolean[][] startingCells;
    private boolean[][] cells;
    private boolean[][] newcells;

    private int x = 0;
    private int y = 0;

    private int width, height;

    private boolean mouseLocked;
    private boolean manual;

    public void start() {

        clock = new Clock(this);
        this.readDAO = new ReadDAO();
        this.writeDAO = new WriteDAO();
        menuWindow = new MenuWindow(this);


    }


    public void buildGameWindow(int xSize, int ySize, int cellCount, String startMode) {

        manual = false;

        switch (startMode) {

            case "Randomized" -> {
                generateRandomized(xSize, ySize, cellCount);
            }
            case "Manuel" -> {
                setManual(true);
                buildManuelGame(xSize, ySize, cellCount);
            }
            default -> {
                getFromDatabase(startMode);
            }
        }

        saveFirstGrid();

        calcSize();

        gameWindow = new GameWindow(this, this.xSize, this.ySize, this.width, this.height);
        if (manual){
            buildControlWindow();
        }

        clock.start();

    }

    private void buildManuelGame(int xSize, int ySize, int cellCount) {

        this.xSize = xSize;
        this.ySize = ySize;
        this.cellCount = cellCount;

        clock.setFirstGen(true);

        gen = 0;
        mouseLocked = true;

        cells = new boolean[this.xSize][this.ySize];
        newcells = new boolean[this.xSize][this.ySize];
        startingCells = new boolean[this.xSize][this.ySize];

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] = false;
            }
        }

        syncCells();

        setRunning(false);

        this.mouseLocked = false;

    }

    private void getFromDatabase(String name) {

        cells = readDAO.getGrid(name);
        gen = Integer.parseInt(readDAO.getGameInfo(name, "GENERATION"));
        this.xSize = cells.length;
        this.ySize = cells[0].length;
        this.cellCount = this.xSize * this.ySize;
        newcells = new boolean[this.xSize][this.ySize];
        startingCells = new boolean[this.xSize][this.ySize];
        syncCells();
        setRunning(true);
    }

    private void generateRandomized(int xSize, int ySize, int cellCount) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellCount = cellCount;

        clock.setFirstGen(true);

        gen = 0;
        mouseLocked = true;

        cells = new boolean[this.xSize][this.ySize];
        newcells = new boolean[this.xSize][this.ySize];
        startingCells = new boolean[this.xSize][this.ySize];

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
        setRunning(true);
    }

    private void syncCells() {
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                newcells[x][y] = cells[x][y];
            }
        }
    }

    public void saveFirstGrid() {
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                startingCells[x][y] = cells[x][y];
            }
        }
    }

    private void resetCells() {
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
        gameWindow.updateTitle(gen);
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

    public void previousGen() {
        if (gen > 0) {
            resetCells();
            syncCells();
            for (int i = 0; i < gen - 1; i++) {
                nextGenLight();
            }
            gen--;
            gameWindow.updateTitle(gen);
        }
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
        gameWindow.showGrid(cells);
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

    public int getVelocity() {
        return clock.getVelocity();
    }

    public void setVelocity(int velocity) {
        clock.setVelocity(velocity);
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

    public int getGeneration() {
        return gen;
    }

    public void jump2Gen(int wantedtGen) {
        if (wantedtGen <= gen) {
            resetCells();
            syncCells();

            int counter = ((gen - wantedtGen) / (wantedtGen + 1));

            for (int i = 0; i < wantedtGen; i++) {
                nextGenLight();
                gen = gen - counter;
                gameWindow.updateTitle(gen);

            }

            gen = wantedtGen;
            gameWindow.updateTitle(gen);

        } else {
            multipleGenSkip(wantedtGen - gen);
        }
    }

    public List<String> getAllGrids() {
        return readDAO.getAllGridNames();
    }

    public void saveGridInDB(String gridName) {
        writeDAO.saveGrid(this.cells, gridName, this.gen, this.cellCount);
    }

    public boolean checkName(String name) {
        return readDAO.checkName(name);
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public void buildControlWindow() {
        controlWindow = new ControlWindow(this);
    }
    public void buildDatabaseWindow() {
        databaseWindow = new DatabaseWindow(this);
    }

    public Point getGameWindowPos() {
        return gameWindow.getPos();
    }

    public void showGrid(boolean[][] grid) {
        gameWindow.showGrid(grid);
    }

    public ControlWindow getControlWindow() {
        return controlWindow;
    }

    public DatabaseWindow getDatabaseWindow() {
        return databaseWindow;
    }

    public MenuWindow getMenuWindow() {
        return menuWindow;
    }

    public void setControlVisibility(boolean value) {
        controlWindow.setVisibility(value);
    }

    public void setDatabaseVisibility(boolean value) {
        databaseWindow.setVisibility(value);
    }

    public String[] getGameInfo(String gridName, String[] columnNames){
        return readDAO.getGameInfo(gridName, columnNames);
    }

    public void deleteEntry(String gridName){
        writeDAO.deleteEntry(gridName);
    }
}



