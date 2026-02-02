package net.onlyplaygames.service;

import static net.onlyplaygames.service.DefaultSpinDataProviderService.REELS_COUNT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.onlyplaygames.domain.init.PartnerConfig;
import net.onlyplaygames.domain.init.ResponseInit;
import net.onlyplaygames.domain.request.Chili;
import net.onlyplaygames.domain.request.ResponseConfig;
import net.onlyplaygames.domain.request.ResponseSpin;
import net.onlyplaygames.domain.request.Scaters;
import net.onlyplaygames.domain.request.SlotMachine;
import net.onlyplaygames.domain.request.Spin;

public class SimpleGameEngineService implements GameEngineService {
    public static final String CONFIG_REQUEST_TYPE = "config";
    public static final String SPIN_REQUEST_TYPE = "spin";
    public static final double WINLINE_MULTIPLIER = 1.0;
    private double balance = 9999;
    public static final List<List<Integer>> REEL_ICONS = List.of(
        List.of(1, 2, 3, 4, 5, 6, 7, 8),
        List.of(1, 2, 3, 4, 5, 6, 7, 8),
        List.of(1, 2, 3, 4, 5, 6, 7, 8),
        List.of(1, 2, 3, 4, 5, 6, 7, 8),
        List.of(1, 2, 3, 4, 5, 6, 7, 8),
        List.of(1, 2, 3, 4, 5, 6, 7, 8)
    );
    public static final Map<Integer, Integer> SCATTER_PAYTABLE = Map.of(
        2, 2,
        3, 10,
        4, 100
    );
    public static final Map<Integer, List<Integer>> PAYTABLE = Map.ofEntries(
        Map.entry(0, List.of()),
        Map.entry(1, List.of(0, 0, 50, 500, 5000)),
        Map.entry(2, List.of(0, 0, 20, 80, 400)),
        Map.entry(3, List.of(0, 0, 20, 80, 400)),
        Map.entry(4, List.of(0, 0, 15, 40, 200)),
        Map.entry(5, List.of(0, 0, 15, 40, 200)),
        Map.entry(6, List.of(0, 0, 10, 25, 100)),
        Map.entry(7, List.of(0, 0, 10, 25, 100)),
        Map.entry(8, List.of(0, 0, 2, 10, 50))
    );
    public static final Map<Integer, List<int[]>> WIN_LINES = Map.ofEntries(
        Map.entry(1, List.of(new int[] {1, 0}, new int[] {1, 1}, new int[] {1, 2}, new int[] {1, 3}, new int[] {1, 4})),
        Map.entry(2, List.of(new int[] {0, 0}, new int[] {0, 1}, new int[] {0, 2}, new int[] {0, 3}, new int[] {0, 4})),
        Map.entry(3, List.of(new int[] {2, 0}, new int[] {2, 1}, new int[] {2, 2}, new int[] {2, 3}, new int[] {2, 4})),
        Map.entry(4, List.of(new int[] {0, 0}, new int[] {1, 1}, new int[] {2, 2}, new int[] {1, 3}, new int[] {0, 4})),
        Map.entry(5, List.of(new int[] {2, 0}, new int[] {1, 1}, new int[] {0, 2}, new int[] {1, 3}, new int[] {2, 4})),

        Map.entry(6, List.of(new int[] {0, 0}, new int[] {0, 1}, new int[] {1, 2}, new int[] {2, 3}, new int[] {2, 4})),
        Map.entry(7, List.of(new int[] {2, 0}, new int[] {2, 1}, new int[] {1, 2}, new int[] {0, 3}, new int[] {0, 4})),

        Map.entry(8, List.of(new int[] {1, 0}, new int[] {2, 1}, new int[] {1, 2}, new int[] {0, 3}, new int[] {1, 4})),
        Map.entry(9, List.of(new int[] {1, 0}, new int[] {0, 1}, new int[] {1, 2}, new int[] {2, 3}, new int[] {1, 4})),

        Map.entry(10, List.of(new int[] {1, 0}, new int[] {0, 1}, new int[] {0, 2}, new int[] {0, 3}, new int[] {0, 4})),
        Map.entry(11, List.of(new int[] {1, 0}, new int[] {2, 1}, new int[] {2, 2}, new int[] {2, 3}, new int[] {2, 4})),
        Map.entry(12, List.of(new int[] {0, 0}, new int[] {1, 1}, new int[] {1, 2}, new int[] {1, 3}, new int[] {1, 4})),
        Map.entry(13, List.of(new int[] {2, 0}, new int[] {1, 1}, new int[] {1, 2}, new int[] {1, 3}, new int[] {1, 4})),

        Map.entry(14, List.of(new int[] {0, 0}, new int[] {1, 1}, new int[] {0, 2}, new int[] {1, 3}, new int[] {0, 4})),
        Map.entry(15, List.of(new int[] {2, 0}, new int[] {1, 1}, new int[] {2, 2}, new int[] {1, 3}, new int[] {2, 4})),

        Map.entry(16, List.of(new int[] {1, 0}, new int[] {1, 1}, new int[] {0, 2}, new int[] {1, 3}, new int[] {1, 4})),
        Map.entry(17, List.of(new int[] {1, 0}, new int[] {1, 1}, new int[] {2, 2}, new int[] {1, 3}, new int[] {1, 4})),

        Map.entry(18, List.of(new int[] {0, 0}, new int[] {0, 1}, new int[] {2, 2}, new int[] {0, 3}, new int[] {0, 4})),
        Map.entry(19, List.of(new int[] {2, 0}, new int[] {2, 1}, new int[] {0, 2}, new int[] {2, 3}, new int[] {2, 4})),

        Map.entry(20, List.of(new int[] {0, 0}, new int[] {2, 1}, new int[] {0, 2}, new int[] {2, 3}, new int[] {0, 4}))
    );

    public static final Integer SCATTER_SYMBOL = 8;


    private SpinDataProviderService spinDataProviderService;
    private BetProviderService betProviderService;
    private ChiliGameplayService chiliGameplayService;

    public SimpleGameEngineService(SpinDataProviderService spinDataProviderService, BetProviderService betProviderService,
                                   ChiliGameplayService chiliGameplayService) {
        this.spinDataProviderService = spinDataProviderService;
        this.betProviderService = betProviderService;
        this.chiliGameplayService = chiliGameplayService;
    }

    @Override
    public ResponseInit init() {
        return ResponseInit.builder()
            .partnerConfig(PartnerConfig.builder()
                .availableBets(betProviderService.getAvailableBets())
                .build())
            .build();
    }

    @Override
    public Object processRequest(String id) {
        return switch (id) {
            case CONFIG_REQUEST_TYPE -> processConfigRequest();
            case SPIN_REQUEST_TYPE -> processSpinRequest();
            default -> null;
        };
    }

    @Override
    public boolean isValidIdParam(String id) {
        return CONFIG_REQUEST_TYPE.equals(id) || SPIN_REQUEST_TYPE.equals(id);
    }

    private ResponseSpin processSpinRequest() {
        double bet = betProviderService.getBet();

        debit(bet);

        int[][] spinTable = spinDataProviderService.getSpinTable(REEL_ICONS);

        Spin spin = calculateSpinData(spinTable, bet);

        Scaters scaters = getScaters(spinTable, bet);

        Chili chili = chiliGameplayService.spin();

        double totalWin = (spin.getTotalWin() + scaters.getScattersWin()) * chili.getChiliCoef();

        change(totalWin);

        return ResponseSpin.builder()
            .success(true)
            .totalWin(totalWin)
            .betAmount(bet)
            .balance(balance)
            .isFinished(0)
            .isScatters(scaters.isScatters())
            .scattersWinCoef(scaters.getScattersWinCoef())
            .scattersWin(scaters.getScattersWin())
            .scatters(scaters.getScatters())
            .chili(chili.getChili())
            .chiliCoef(chili.getChiliCoef())
            .decimal(2)
            .decimals(2)
            .spin(Spin.builder()
                .reels(List.of(
                    List.of(8, 7, 7), List.of(6, 6, 6), List.of(2, 2, 2), List.of(7, 8, 5), List.of(4, 4, 8)
                ))
                .winLines(Map.of())
                .linesIcons(Map.of())
                .totalWin(totalWin)
                .build())
            .build();
    }

    private Spin calculateSpinData(int[][] spinTable, double bet) {
        double total = 0;
        List<List<Integer>> reels = getReels(spinTable);
        Map<Integer, Double> winLines = new HashMap<>();
        Map<Integer, Integer> linesIcons = new HashMap<>();

        for (Map.Entry<Integer, List<int[]>> winLine : WIN_LINES.entrySet()) {
            Integer firstIcon = iconByPos(spinTable, winLine.getValue().get(0));
            if (firstIcon.equals(SCATTER_SYMBOL)) {
                continue;
            }

            boolean match = true;
            for (int[] pos : winLine.getValue()) {
                if (!iconByPos(spinTable, pos).equals(firstIcon)) {
                    match = false;
                    break;
                }
            }

            if (match) {
                int multiplier = 1;
                int fiveCountIconMultiplier = PAYTABLE.get(firstIcon).get(4);
                winLines.put(winLine.getKey(), WINLINE_MULTIPLIER);
                linesIcons.put(winLine.getKey(), firstIcon);
                total += bet * multiplier * fiveCountIconMultiplier;
            }
        }
        return Spin.builder()
            .totalWin(total)
            .reels(reels)
            .linesIcons(linesIcons)
            .winLines(winLines)
            .build();
    }

    private List<List<Integer>> getReels(int[][] spinTable) {
        List<List<Integer>> reels = new ArrayList<>();
        for (int i = 0; i < DefaultSpinDataProviderService.ROWS_COUNT; i++) {
            List<Integer> reel = new ArrayList<>();
            reel.add(spinTable[0][i]);
            reel.add(spinTable[1][i]);
            reel.add(spinTable[2][i]);
            reels.add(reel);
        }
        return reels;
    }

    private Integer iconByPos(int[][] table, int[] pos) {
        return table[pos[0]][pos[1]];
    }

    private int countScatters(int[][] table) {
        int count = 0;
        for (int[] reel : table) {
            for (int symbol : reel) {
                if (symbol == SCATTER_SYMBOL) {
                    count++;
                }
            }
        }
        return count;
    }

    private List<List<Integer>> getScatterPositions(int[][] spinTable) {
        List<List<Integer>> scatters = new ArrayList<>();
        for (int i = 0; i < spinTable.length; i++) {
            int[] reel = spinTable[i];
            for (int j = 0; j < reel.length; j++) {
                Integer symbol = reel[j];
                if (symbol.equals(SCATTER_SYMBOL)) {
                    scatters.add(List.of(i, j));
                }
            }
        }
        return scatters;
    }

    public void debit(double amount) {
        synchronized (this) {
            if (amount > balance) {
                throw new IllegalArgumentException("Недостаточно средств");
            }
            balance -= amount;
        }
    }

    public void change(double amount) {
        synchronized (this) {
            balance += amount;
        }
    }

    private Scaters getScaters(int[][] spinTable, double bet) {
        int countScatters = countScatters(spinTable);
        Integer scattersWinCoef = SCATTER_PAYTABLE.get(countScatters);
        double scatterWin = scattersWinCoef == null ? 0 : bet * scattersWinCoef;
        List<List<Integer>> scatterPositions = getScatterPositions(spinTable);

        if (scatterWin == 0.0) {
            return Scaters.builder()
                .isScatters(false)
                .scatters(List.of())
                .scattersWinCoef(1)
                .scattersWin(0.0)
                .build();
        } else {
            return Scaters.builder()
                .isScatters(true)
                .scatters(scatterPositions)
                .scattersWinCoef(scattersWinCoef)
                .scattersWin(scatterWin)
                .build();
        }
    }

    private ResponseConfig processConfigRequest() {
        // TODO implement
        return ResponseConfig.builder()
            .success(true)
            .balance(this.balance)
            .currency("eur")
            .decimals(2)
            .decimal(2)
            .bets(betProviderService.getAvailableBets())
            .betsAvailable(betProviderService.getAvailableBets())
            .betAmount(1)
            .slotMachine(SlotMachine.builder()
                .bets(betProviderService.getAvailableBets())
                .reels(REELS_COUNT)
                .paytable(PAYTABLE)
                .winAmount(1000)
                .totalWin(1000)
                .build())
            .build();
    }


}
