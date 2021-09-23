package v4.game;

import java.util.concurrent.ThreadLocalRandom;

public class Control {

    public static final int CELLCOUNT = 512;
    public static boolean[][] cells = new boolean[CELLCOUNT][CELLCOUNT];

    int startCells = 10000;
    static int gen = 0;

    public void create(){
        for(int i = 0; i < startCells; i++){
            int x = rand(0, CELLCOUNT);
            int y = rand(0, CELLCOUNT);

            cells[x][y] = true;
        }
    }

    public static void nextGen(){
        gen ++;
        System.out.println("Generation:" + gen);

        for (int x = 0; x < CELLCOUNT; x++){
            for (int y = 0; y < CELLCOUNT; y++) {
                int n = aliveNeigbours(x, y);

                if (n == 3 && !cells[x][y]){
                    cells[x][y] = true;
                }

                if (n < 2){
                    cells[x][y] = false;
                }

                if (n == 2 || n == 3){

                }

                if (n > 3){
                    cells[x][y] = false;
                }

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

}
