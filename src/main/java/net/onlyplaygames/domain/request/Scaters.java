package net.onlyplaygames.domain.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Scaters {
    private boolean isScatters;
    private List<List<Integer>> scatters;
    private double scattersWinCoef;
    private double scattersWin;
}
