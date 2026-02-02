package net.onlyplaygames.service;

import java.util.List;

public interface SpinDataProviderService {
    int[][] getSpinTable(List<List<Integer>> reels);
}
