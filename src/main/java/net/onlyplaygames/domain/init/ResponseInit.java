package net.onlyplaygames.domain.init;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseInit {
    private PartnerConfig partnerConfig;
}
