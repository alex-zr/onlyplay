package net.onlyplaygames.domain.request;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SlotMachine {
    private List<Double> bets;
    private int reels;
    private Map<Integer, List<Integer>> paytable;
    private int winAmount;
    private double totalWin;
}
