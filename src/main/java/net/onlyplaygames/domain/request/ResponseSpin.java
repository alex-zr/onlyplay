package net.onlyplaygames.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseSpin {
    private boolean success;
    private double totalWin;
    private double betAmount;
    private double balance;
    private int isFinished;
    @JsonProperty("isScatters")
    private boolean isScatters;
    private List<List<Integer>> scatters;
    private double scattersWinCoef;
    private double scattersWin;
    private List<Integer> chili;
    @JsonProperty("chiliCoef")
    private double chiliCoef;
    private int decimals;
    private int decimal;
    private Spin spin;
}
