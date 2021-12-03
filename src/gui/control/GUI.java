package gui.control;

import gamecontrol.Control;
import gui.windows.ControlWindow;
import gui.windows.GameWindow;
import gui.windows.MenuWindow;

import java.awt.*;
import java.util.List;

public class GUI {

    private final String title = "Conway's game if Live";

    private final Control control;

    private Font head;
    private Font text;
    private boolean sliderLocked = false;

    private final int offset = 20;

    private MenuWindow menuWindow;
    private GameWindow gameWindow;
    private ControlWindow controlWindow;

    public GUI(Control control){
        this.control = control;

        initPublicComponents();
    }

    public void initPublicComponents(){

        head = new Font("Segoe UI Light", Font.PLAIN, 24);
        text = new Font("Segoe UI Light", Font.PLAIN, 16);

    }

    public void createMenuWindow(){
        menuWindow = new MenuWindow(title, this);
    }

    public void createGameWindow(int cellCountX, int cellCountY, int width, int height){
        gameWindow = new GameWindow(title, this, cellCountX, cellCountY, width, height, offset);
    }

    public void createControlWindow(){
        controlWindow = new ControlWindow(gameWindow, this, offset);
    }

    public void showGrid(boolean[][] grid){
        gameWindow.showGrid(grid);
    }

    public void buildGameWindow(int xSize, int ySize, int cellCount, String startMode){
        control.buildGameWindow(xSize, ySize, cellCount, startMode);
    }

    public void buildControlWindow(){
        createControlWindow();
    }

    public void setCell(int mouseX, int mouseY, boolean value){
        control.setCell(mouseX, mouseY, value, offset);
    }

    public void setRunning(boolean state){
        control.setRunning(state);
    }

    public boolean isRunning(){
        return control.isRunning();
    }

    public MenuWindow getMenuWindow(){
        return menuWindow;
    }

    public void setVelocity(int velocity){
        control.setVelocity(velocity);
    }

    public Font getHead() {
        return head;
    }

    public Font getText() {
        return text;
    }

    public boolean isSliderLocked() {
        return sliderLocked;
    }

    public void setSliderLocked(boolean sliderLocked) {
        this.sliderLocked = sliderLocked;
    }

    public void nextGen(){
        control.nextGen();
    }

    public boolean[][] getCells(){ return control.getCells(); }

    public boolean isMouseLocked(){
        return control.isMouseLocked();
    }

    public void setMouseLocked(boolean value){
        control.setMouseLocked(value);
    }

    public void clearGrid(){ control.clearGrid(); }

    public void multipleGenSkip(int generations){
        control.multipleGenSkip(generations);
    }

    public int getGeneration(){
        return control.getGeneration();
    }

    public void updateGameWindowTitle(int gen){
        gameWindow.updateTitle(gen);
    }

    public int getVelocity(){
        return control.getVelocity();
    }

    public void saveFirstGrid(){
        control.saveFirstGrid();
    }

    public void previousGen(){
        control.previousGen();
    }

    public void jump2Gen(int wantedtGen){
        control.jump2Gen(wantedtGen);
    }

    public void saveGridInDB(String gridName){
        control.saveGridInDB(gridName);
    }

    public List<String> getAllGrids(){
        return control.getAllGrids();
    }

    public boolean checkName(String name){
        return control.checkName(name);
    }

    //TODO: Delet this Methods. Those are shit.
    public void setVisibility(boolean value){
        controlWindow.setVisibility(value);
    }

    public ControlWindow getControlWindow() {
        return controlWindow;
    }



}
