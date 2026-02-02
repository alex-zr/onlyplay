package net.onlyplaygames.domain.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Chili {
    private List<Integer> chili;
    private int chiliCoef;
}
