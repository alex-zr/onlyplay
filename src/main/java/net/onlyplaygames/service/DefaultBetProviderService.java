package net.onlyplaygames.service;

import java.util.List;
import java.util.Random;

public class DefaultBetProviderService implements BetProviderService {
    private final Random random = new Random();
    private final List<Double> availableBets = List.of(1.0, 1.5, 3.0, 6.0, 10.0);

    @Override
    public double getBet() {
        return availableBets.get(random.nextInt(availableBets.size()));
    }

    @Override
    public List<Double> getAvailableBets() {
        return availableBets;
    }
}
