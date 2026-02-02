package net.onlyplaygames.service;

import java.util.List;

public interface BetProviderService {
    double getBet();

    List<Double> getAvailableBets();
}
