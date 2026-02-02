package net.onlyplaygames.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.onlyplaygames.domain.request.Chili;

public class DefaultChiliGameplayService implements ChiliGameplayService {
    public static final int REELS_COUNT = 3;
    private final Random random = new Random();

    private final List<List<Integer>> reels = List.of(
        List.of(9, 10, 11),
        List.of(9, 10, 11),
        List.of(9, 10, 11)
    );
    private final Map<Integer, Integer> paytable = Map.of(
        9, 3,
        10, 6,
        11, 9
    );

    @Override
    public Chili spin() {
        List<Integer> chili = new ArrayList<>();
        for (int i = 0; i < REELS_COUNT; i++) {
            List<Integer> reel = reels.get(i);
            chili.add(reel.get(random.nextInt(reel.size())));
        }

        int chiliCoef = 1;
        if (chili.get(0).equals(chili.get(1)) && chili.get(1).equals(chili.get(2))) {
            chiliCoef = paytable.get(chili.get(0));
        }

        return Chili.builder()
            .chiliCoef(chiliCoef)
            .chili(chili)
            .build();
    }
}
