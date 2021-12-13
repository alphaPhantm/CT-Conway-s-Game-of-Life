package database;

import java.util.ArrayList;
import java.util.List;

public class ArrayParser {

    public String serialize(final boolean[][] grid) {
        final StringBuilder erg = new StringBuilder();

        for (boolean[] booleans : grid) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    erg.append("1");
                } else {
                    erg.append("0");
                }
            }
        }

        return erg.toString();
    }

    public boolean[][] deserialize(int width, int height, String string) {

        boolean[][] erg = new boolean[height][width];
        List<Integer> ints = new ArrayList<>();

        for (String s : string.split("")) ints.add(Integer.parseInt(s));

        int index = 0;
        for (int y = 0; y < erg.length; y++) {
            System.out.println(erg[y].length);
            for (int x = 0; x < erg.length; x++) {
                erg[y][x] = ints.get(index) == 1;
                index++;
            }
        }

        return erg;

    }
}
