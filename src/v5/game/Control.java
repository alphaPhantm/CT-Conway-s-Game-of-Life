package v5.game;

import java.util.concurrent.ThreadLocalRandom;

public class Control {

    public static final int CELLCOUNT_X = 6;
    public static final int CELLCOUNT_Y = 6;
    public static boolean[][] cells = new boolean[CELLCOUNT_X][CELLCOUNT_Y];
    public static boolean[][] newcells = new boolean[CELLCOUNT_X][CELLCOUNT_Y];

    int startCells = 6;
    static int gen = 0;


    public void create(){
//        for(int i = 0; i < startCells; i++){
//            int x = rand(0, CELLCOUNT_X);
//            int y = rand(0, CELLCOUNT_Y);
//
//            cells[x][y] = true;
//        }


        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                cells[x][y] = false;
            }
        }

        cells[1][2] = true;
        cells[2][2] = true;
        cells[2][4] = true;
        cells[3][1] = true;
        cells[3][3] = true;
        cells[4][3] = true;


        for (int x = 0; x < CELLCOUNT_X; x++) {
            for (int y = 0; y < CELLCOUNT_Y; y++) {
                newcells[x][y] = cells[x][y];
            }
        }

    }

    public static void nextGen(){
        gen ++;
        System.out.println("Generation:" + gen);

        for (int x = 0; x < CELLCOUNT_X; x++){
            for (int y = 0; y < CELLCOUNT_Y; y++) {
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

        for (int x = 0; x < CELLCOUNT_X; x++) {
            for (int y = 0; y < CELLCOUNT_Y; y++) {
                cells[x][y] = newcells[x][y];
            }
        }

    }

    public static int aliveNeigbours(int x, int y){
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

    public static int rand(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private void calcDimension(int cellsX, int cellsY){

    }

}
