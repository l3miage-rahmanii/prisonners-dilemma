package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.DonnantPourDeuxDonnants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DonnantPourDeuxDonnantsTest {

    private DonnantPourDeuxDonnants strategy;

    @BeforeEach
    void setUp() {
        strategy = new DonnantPourDeuxDonnants();
    }

    @Test
    void whenLastTwoMovesAreSame_shouldReturnThatMove() {
        // Test when last two moves are both COOPERER
        List<CoupEnum> historique1 = Arrays.asList(CoupEnum.TRAHIR, CoupEnum.COOPERER, CoupEnum.COOPERER);
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(historique1));

        // Test when last two moves are both TRAHIR
        List<CoupEnum> historique2 = Arrays.asList(CoupEnum.COOPERER, CoupEnum.TRAHIR, CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(historique2));
    }

    @Test
    void whenLastTwoMovesAreDifferent_shouldReturnSecondToLastMove() {
        // Test when last moves are COOPERER, TRAHIR
        List<CoupEnum> historique1 = Arrays.asList(CoupEnum.TRAHIR, CoupEnum.COOPERER, CoupEnum.TRAHIR);
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(historique1));

        // Test when last moves are TRAHIR, COOPERER
        List<CoupEnum> historique2 = Arrays.asList(CoupEnum.COOPERER, CoupEnum.TRAHIR, CoupEnum.COOPERER);
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(historique2));
    }

    @Test
    void testWithLongerHistory() {
        List<CoupEnum> historique = Arrays.asList(
                CoupEnum.COOPERER,
                CoupEnum.TRAHIR,
                CoupEnum.COOPERER,
                CoupEnum.COOPERER,
                CoupEnum.COOPERER
        );
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(historique));
    }
}