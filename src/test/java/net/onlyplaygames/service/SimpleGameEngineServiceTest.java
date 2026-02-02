package net.onlyplaygames.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import net.onlyplaygames.domain.request.Chili;
import net.onlyplaygames.domain.request.ResponseSpin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SimpleGameEngineServiceTest {

    @Spy
    private ChiliGameplayService chiliGameplayService;

    @Mock
    private SpinDataProviderService spinDataProviderService;

    @Mock
    private BetProviderService betProviderService;

    @InjectMocks
    private SimpleGameEngineService gameEngineService;

    @Test
    void processRequestSpinNoWin() {
        when(betProviderService.getBet()).thenReturn(1.0);
        List<Integer> chili = List.of(9,10,11);
        when(chiliGameplayService.spin()).thenReturn(Chili.builder().chiliCoef(1).chili(chili).build());
        when(spinDataProviderService.getSpinTable(anyList())).thenReturn(new int[][] {
            new int[] {1, 2, 1, 2, 1},
            new int[] {1, 2, 1, 2, 1},
            new int[] {1, 2, 1, 2, 1}
        });

        ResponseSpin spin = (ResponseSpin) gameEngineService.processRequest("spin");

        assertEquals(0.0, spin.getTotalWin());
        assertTrue(spin.isSuccess());
        assertEquals(1.0, spin.getChiliCoef());
        assertEquals(chili, spin.getChili());
        assertEquals(Map.of(), spin.getSpin().getWinLines());
        assertEquals(0.0, spin.getSpin().getTotalWin());
        assertEquals(Map.of(), spin.getSpin().getLinesIcons());
        assertEquals(5, spin.getSpin().getReels().size());
        assertEquals(1, spin.getBetAmount());
        assertEquals(9998.0, spin.getBalance());
        assertEquals(0, spin.getIsFinished());
        assertFalse(spin.isScatters());
        assertEquals(List.of(), spin.getScatters());
        assertEquals(0.0, spin.getScattersWin());
        assertEquals(1.0, spin.getScattersWinCoef());
    }

    @Test
    void processRequestSpinWinLine17Line18() {
        List<Integer> chili = List.of(9,10,11);
        when(betProviderService.getBet()).thenReturn(1.0);
        when(chiliGameplayService.spin()).thenReturn(Chili.builder().chiliCoef(1).chili(chili).build());
        when(spinDataProviderService.getSpinTable(anyList())).thenReturn(new int[][] {
            new int[] {1, 1, 2, 1, 1},
            new int[] {2, 2, 1, 2, 2},
            new int[] {1, 1, 2, 1, 1}
        });

        ResponseSpin spin = (ResponseSpin) gameEngineService.processRequest("spin");

        assertEquals(10800.0, spin.getTotalWin());
        assertTrue(spin.isSuccess());
        assertEquals(1.0, spin.getChiliCoef());
        assertEquals(chili, spin.getChili());
        assertEquals(Map.of(), spin.getSpin().getWinLines());
        assertEquals(10800, spin.getSpin().getTotalWin());
        assertEquals(Map.of(), spin.getSpin().getLinesIcons());
        assertEquals(5, spin.getSpin().getReels().size());
        assertEquals(1, spin.getBetAmount());
        assertEquals(20798, spin.getBalance());
        assertEquals(0, spin.getIsFinished());
        assertFalse(spin.isScatters());
        assertEquals(List.of(), spin.getScatters());
        assertEquals(0.0, spin.getScattersWin());
        assertEquals(1.0, spin.getScattersWinCoef());
    }

    @Test
    void processRequestSpinWinLine17() {
        List<Integer> chili = List.of(9,10,11);

        when(betProviderService.getBet()).thenReturn(1.0);
        when(chiliGameplayService.spin()).thenReturn(Chili.builder().chiliCoef(1).chili(chili).build());
        when(spinDataProviderService.getSpinTable(anyList())).thenReturn(new int[][] {
            new int[] {1, 1, 2, 1, 1},
            new int[] {2, 2, 1, 2, 2},
            new int[] {1, 3, 3, 3, 1}
        });

        ResponseSpin spin = (ResponseSpin) gameEngineService.processRequest("spin");

        assertEquals(400.0, spin.getTotalWin());
        assertTrue(spin.isSuccess());
        assertEquals(1.0, spin.getChiliCoef());
        assertEquals(chili, spin.getChili());
        assertEquals(Map.of(), spin.getSpin().getWinLines());
        assertEquals(400, spin.getSpin().getTotalWin());
        assertEquals(Map.of(), spin.getSpin().getLinesIcons());
        assertEquals(5, spin.getSpin().getReels().size());
        assertEquals(1, spin.getBetAmount());
        assertEquals(10398, spin.getBalance());
        assertEquals(0, spin.getIsFinished());
        assertFalse(spin.isScatters());
        assertEquals(List.of(), spin.getScatters());
        assertEquals(0.0, spin.getScattersWin());
        assertEquals(1.0, spin.getScattersWinCoef());
    }

    @Test
    void processRequestSpin3ScattersWinLine17() {
        List<Integer> chili = List.of(9,10,11);

        when(chiliGameplayService.spin()).thenReturn(Chili.builder().chiliCoef(1).chili(chili).build());
        when(betProviderService.getBet()).thenReturn(1.0);
        when(spinDataProviderService.getSpinTable(anyList())).thenReturn(new int[][] {
            new int[] {1, 1, 2, 1, 1},
            new int[] {2, 2, 1, 2, 2},
            new int[] {1, 8, 8, 8, 1}
        });

        ResponseSpin spin = (ResponseSpin) gameEngineService.processRequest("spin");

        assertEquals(410.0, spin.getTotalWin());
        assertTrue(spin.isSuccess());
        assertEquals(1.0, spin.getChiliCoef());
        assertEquals(chili, spin.getChili());
        assertEquals(Map.of(), spin.getSpin().getWinLines());
        assertEquals(410, spin.getSpin().getTotalWin());
        assertEquals(Map.of(), spin.getSpin().getLinesIcons());
        assertEquals(5, spin.getSpin().getReels().size());
        assertEquals(1, spin.getBetAmount());
        assertEquals(10408, spin.getBalance());
        assertEquals(0, spin.getIsFinished());
        assertTrue(spin.isScatters());
        assertEquals(List.of(
            List.of(2, 1),
            List.of(2, 2),
            List.of(2, 3)
        ), spin.getScatters());
        assertEquals(10.0, spin.getScattersWin());
        assertEquals(10.0, spin.getScattersWinCoef());
    }

    @Test
    void processRequestSpin3Scatter() {
        List<Integer> chili = List.of(9,10,11);

        when(chiliGameplayService.spin()).thenReturn(Chili.builder().chiliCoef(1).chili(chili).build());
        when(betProviderService.getBet()).thenReturn(1.0);
        when(spinDataProviderService.getSpinTable(anyList())).thenReturn(new int[][] {
            new int[] {1, 1, 8, 1, 1},
            new int[] {2, 2, 1, 2, 2},
            new int[] {1, 8, 3, 8, 1}
        });

        ResponseSpin spin = (ResponseSpin) gameEngineService.processRequest("spin");

        assertEquals(10.0, spin.getTotalWin());
        assertTrue(spin.isSuccess());
        assertEquals(1.0, spin.getChiliCoef());
        assertEquals(chili, spin.getChili());
        assertEquals(Map.of(), spin.getSpin().getWinLines());
        assertEquals(10, spin.getSpin().getTotalWin());
        assertEquals(Map.of(), spin.getSpin().getLinesIcons());
        assertEquals(5, spin.getSpin().getReels().size());
        assertEquals(1, spin.getBetAmount());
        assertEquals(10008, spin.getBalance());
        assertEquals(0, spin.getIsFinished());
        assertTrue(spin.isScatters());
        assertEquals(List.of(
            List.of(0, 2),
            List.of(2, 1),
            List.of(2, 3)
        ), spin.getScatters());
        assertEquals(10.0, spin.getScattersWin());
        assertEquals(10.0, spin.getScattersWinCoef());
    }

    @Test
    void processRequestSpinWinLine17Chili() {
        List<Integer> chili = List.of(9, 9, 9);

        when(betProviderService.getBet()).thenReturn(3.0);
        when(chiliGameplayService.spin()).thenReturn(Chili.builder().chiliCoef(1).chili(chili).build());
        when(spinDataProviderService.getSpinTable(anyList())).thenReturn(new int[][] {
            new int[] {1, 1, 2, 1, 1},
            new int[] {2, 2, 1, 2, 2},
            new int[] {1, 3, 3, 3, 1}
        });

        ResponseSpin spin = (ResponseSpin) gameEngineService.processRequest("spin");

        assertEquals(1200.0, spin.getTotalWin());
        assertTrue(spin.isSuccess());
        assertEquals(1.0, spin.getChiliCoef());
        assertEquals(chili, spin.getChili());
        assertEquals(Map.of(), spin.getSpin().getWinLines());
        assertEquals(1200, spin.getSpin().getTotalWin());
        assertEquals(Map.of(), spin.getSpin().getLinesIcons());
        assertEquals(5, spin.getSpin().getReels().size());
        assertEquals(3, spin.getBetAmount());
        assertEquals(11196, spin.getBalance());
        assertEquals(0, spin.getIsFinished());
        assertFalse(spin.isScatters());
        assertEquals(List.of(), spin.getScatters());
        assertEquals(0.0, spin.getScattersWin());
        assertEquals(1.0, spin.getScattersWinCoef());
    }
}