package net.onlyplaygames.domain.request;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Spin {
    private List<List<Integer>> reels;
    private Map<Integer, Double> winLines;
    private Map<Integer, Integer> linesIcons;
    private double totalWin;
}
