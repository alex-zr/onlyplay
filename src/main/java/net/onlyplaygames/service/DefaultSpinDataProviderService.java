package net.onlyplaygames.service;

import java.util.List;
import java.util.Random;

public class DefaultSpinDataProviderService implements SpinDataProviderService {
    public static final int REELS_COUNT = 5;
    public static final int ROWS_COUNT = 3;
    private final Random random = new Random();

    @Override
    public int[][] getSpinTable(List<List<Integer>> reels) {
        int[][] table = new int[ROWS_COUNT][REELS_COUNT]; // reel x row

        for (int reel = 0; reel < ROWS_COUNT; reel++) {
            for (int row = 0; row < REELS_COUNT; row++) {
                List<Integer> symbols = reels.get(reel);
                table[reel][row] = symbols.get(random.nextInt(symbols.size()));
            }
        }
        return table;
    }
}
