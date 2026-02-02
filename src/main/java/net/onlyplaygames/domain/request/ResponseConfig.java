package net.onlyplaygames.domain.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseConfig {
    private boolean success;
    private double balance;
    private String currency;
    private int decimals;
    private int decimal;
    private List<Double> bets;
    private List<Double> betsAvailable;
    private int betAmount;
    private SlotMachine slotMachine;
}
