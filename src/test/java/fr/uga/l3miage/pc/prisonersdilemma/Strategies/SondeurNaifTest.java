package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import fr.uga.l3miage.pc.strategies.SondeurNaif;

import java.util.List;
import java.util.Random;

class SondeurNaifTest {

    private SondeurNaif sondeurNaif;
    private Random mockedRandom;

    @BeforeEach
    void setUp() {
        mockedRandom = mock(Random.class);

        sondeurNaif = new SondeurNaif();
        sondeurNaif.setRandom(mockedRandom);
    }

    @Test
    void testProchainCoupWhenRandomTriggersTrahir() {
        when(mockedRandom.nextDouble()).thenReturn(0.05);

        List<CoupEnum> historique = List.of(CoupEnum.COOPERER, CoupEnum.TRAHIR);
        CoupEnum result = sondeurNaif.prochainCoup(historique);

        assertEquals(CoupEnum.TRAHIR, result);
    }

    @Test
    void testProchainCoupWhenRandomDoesNotTriggerTrahir() {
        when(mockedRandom.nextDouble()).thenReturn(0.5);

        List<CoupEnum> historique = List.of(CoupEnum.COOPERER, CoupEnum.TRAHIR);
        CoupEnum result = sondeurNaif.prochainCoup(historique);

        assertEquals(CoupEnum.TRAHIR, result);
    }

    @Test
    void testProchainCoupWithSingleElementHistorique() {
        when(mockedRandom.nextDouble()).thenReturn(0.5);

        List<CoupEnum> historique = List.of(CoupEnum.COOPERER);
        CoupEnum result = sondeurNaif.prochainCoup(historique);

        assertEquals(CoupEnum.COOPERER, result);
    }

    @Test
    void testProchainCoupWithEmptyHistorique() {
        List<CoupEnum> historique = List.of();

        CoupEnum result = sondeurNaif.prochainCoup(historique);

        assertEquals(CoupEnum.TRAHIR, result, "Expected result to be TRAHIR for empty history");
    }

}