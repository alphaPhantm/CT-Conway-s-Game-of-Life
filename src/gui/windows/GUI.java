package gui.windows;

import gamecontrol.Control;

import java.awt.*;
import java.util.List;

public abstract class GUI {

    protected final String title = "Conway's game if Live";
    protected final int offset = 20;
    protected Control control;
    protected Font head;
    protected Font text;
    protected boolean sliderLocked = false;

    public GUI(Control control){
        this.control = control;
    }

    public void initPublicComponents() {

        head = new Font("Segoe UI Light", Font.PLAIN, 24);
        text = new Font("Segoe UI Light", Font.PLAIN, 16);

    }


    public void buildGameWindow(int xSize, int ySize, int cellCount, String startMode) {
        control.buildGameWindow(xSize, ySize, cellCount, startMode);
    }


    public void setCell(int mouseX, int mouseY, boolean value) {
        control.setCell(mouseX, mouseY, value, offset);
    }

    public boolean isRunning() {
        return control.isRunning();
    }

    public void setRunning(boolean state) {
        control.setRunning(state);
    }

    public boolean isSliderLocked() {
        return sliderLocked;
    }

    public void setSliderLocked(boolean sliderLocked) {
        this.sliderLocked = sliderLocked;
    }

    public void nextGen() {
        control.nextGen();
    }

    public boolean[][] getCells() {
        return control.getCells();
    }

    public boolean isMouseLocked() {
        return control.isMouseLocked();
    }

    public void setMouseLocked(boolean value) {
        control.setMouseLocked(value);
    }

    public void clearGrid() {
        control.clearGrid();
    }

    public void multipleGenSkip(int generations) {
        control.multipleGenSkip(generations);
    }

    public int getGeneration() {
        return control.getGeneration();
    }


    public int getVelocity() {
        return control.getVelocity();
    }

    public void setVelocity(int velocity) {
        control.setVelocity(velocity);
    }

    public void saveFirstGrid() {
        control.saveFirstGrid();
    }

    public void previousGen() {
        control.previousGen();
    }

    public void jump2Gen(int wantedtGen) {
        control.jump2Gen(wantedtGen);
    }

    public void saveGridInDB(String gridName) {
        control.saveGridInDB(gridName);
    }

    public List<String> getAllGrids() {
        return control.getAllGrids();
    }

    public boolean checkName(String name) {
        return control.checkName(name);
    }

}
