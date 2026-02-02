package net.onlyplaygames.domain.init;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PartnerConfig {
    List<Double> availableBets;
}
