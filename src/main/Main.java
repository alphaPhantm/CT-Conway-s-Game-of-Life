package main;


import data.database.DataBase;
import game.Control;

public class Main {

    //TODO: Inputfield witch Changelistner to cange Slider, Generic ActionListener.
    //TODO: Window visibility, Control window create only one time. Visibility = false when game started, Visibility = true when game quited. :DONE Need a code Check when Brain is awake.
    //TODO: Database



    public static void main(String[] args) {
        
        Control control = new Control();
        control.start();
        DataBase dataBase = new DataBase();
//        dataBase.deleteTables();
//        dataBase.createTables();


//        boolean[][] cells = new boolean[44][66];
//
//        for (int x = 0; x < 6; x++) {
//            for (int y = 0; y < 6; y++) {
//                cells[x][y] = false;
//            }
//        }
//
//
//        cells[3][3] = true;
//
//        dataBase.saveGrid(cells, "whoa", 88);
//
//        System.out.println(dataBase.getAllGrids());

//        boolean[][] x = dataBase.getGrid("penis");


    }


}
