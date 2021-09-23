package v3;

public class Control {

    private boolean[][] generation = new boolean[6][6];
    private GUI g = new GUI(this);

    public Control() {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                generation[x][y] = false;
            }
        }

        generation[1][2] = true;
        generation[2][2] = true;
        generation[2][4] = true;
        generation[3][1] = true;
        generation[3][3] = true;
        generation[4][3] = true;


    }


    public void start() {
        g.showGeneration(bool2int(generation));
    }

    public void update(){
        generation = calcNextGeneration();
        g.showGeneration(bool2int(generation));
    }

    private int[][] bool2int(boolean[][] booleans) {

        int[][] ret = new int[6][6];

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                if (booleans[x][y]){
                    ret[x][y] = 1;
                } else {
                    ret[x][y] = 0;
                }
            }
        }
        return ret;
    }

    private int countNeighboursAlive(int x, int y) {
        int count = 0;
        //Anfang mitte rechts, Uhrzeigersinn
        int[] xoff = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] yoff = {0, 1, 1, 1, 0, -1, -1, -1};

        for (int i = 0; i < 8; i++) {
            try {
                if (generation[x + xoff[i]][y + yoff[i]]) count++;
            } catch (Exception e) {

            }
        }


        System.out.println(count);
        return count;
    }

    public boolean[][] calcNextGeneration() {

       boolean[][] gen = new boolean[6][6];


        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                int n = countNeighboursAlive(x, y);
                //Regel 1: Wiederbeleben
                if (n == 3 && !gen[x][y]) {
                    gen[x][y] = true;
                }

                //Regel 2: Unterbevölkerung
                if (n < 2) {
                    gen[x][y] = false;
                }

                //Regel 3: Am Leben bleiben
                if (n == 2 || n == 3) {
                    //Zelle bleibt unverändert
                }

                //Regel 4: Überbevölkerung
                if (n > 3) {
                    gen[x][y] = false;
                }

            }
        }

        return gen;
    }


    public void newData(boolean[][] data) {

        generation = data;


    }
}


