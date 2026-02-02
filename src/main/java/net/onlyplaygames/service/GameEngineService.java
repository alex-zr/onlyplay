package net.onlyplaygames.service;

import net.onlyplaygames.domain.init.ResponseInit;
import net.onlyplaygames.domain.request.ResponseConfig;

public interface GameEngineService {
    ResponseInit init();

    Object processRequest(String id);

    boolean isValidIdParam(String id);
}
