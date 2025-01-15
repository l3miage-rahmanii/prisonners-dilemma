package fr.uga.l3miage.pc.prisonersdilemma.Strategies;
/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.Random;

import fr.uga.l3miage.pc.strategies.DonnantDonnantAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonnantDonnantAleatoireTest {

    private static final int HISTORY_SIZE = 100;
    private String[] historique;
    private DonnantDonnantAleatoire strategie;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        historique = new String[HISTORY_SIZE];
        mockRandom = mock(Random.class);

        try {
            strategie = new DonnantDonnantAleatoire(historique);
            java.lang.reflect.Field randomField = strategie.getClass().getDeclaredField("random");
            randomField.setAccessible(true);
            randomField.set(strategie, mockRandom);
        } catch (Exception e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }

    @Test
    void testInitialMoveWithRandomTrue() {
        // Mock random to choose random behavior and return "c"
        when(mockRandom.nextBoolean())
                .thenReturn(true)  // Choose random behavior
                .thenReturn(true); // Choose "c"

        String result = strategie.prochainCoup();
        assertEquals("c", result);
    }

    @Test
    void testInitialMoveWithRandomFalse() {
        // Mock random to choose random behavior and return "t"
        when(mockRandom.nextBoolean())
                .thenReturn(true)   // Choose random behavior
                .thenReturn(false); // Choose "t"

        String result = strategie.prochainCoup();
        assertEquals("t", result);
    }

    @Test
    void testInheritedBehavior() {
        // Mock random to use inherited behavior
        when(mockRandom.nextBoolean()).thenReturn(false);

        // First move should be "c" (inherited behavior's default)
        String result = strategie.prochainCoup();
        assertEquals("c", result);

        // Update with opponent's move
        strategie.miseAJourDernierCoupAdversaire("t");

        // Next move should match opponent's last move
        result = strategie.prochainCoup();
        assertEquals("t", result);
    }

    @Test
    void testSequenceOfMoves() {
        // Mock a sequence of choices
        when(mockRandom.nextBoolean())
                .thenReturn(true, true)   // First move: random "c"
                .thenReturn(false)        // Second move: use inherited
                .thenReturn(true, false); // Third move: random "t"

        // First move
        String result = strategie.prochainCoup();
        assertEquals("c", result);

        // Update opponent's move
        strategie.miseAJourDernierCoupAdversaire("t");

        // Second move (inherited should copy opponent's "t")
        result = strategie.prochainCoup();
        assertEquals("t", result);

        // Update opponent's move
        strategie.miseAJourDernierCoupAdversaire("c");

        // Third move (random "t")
        result = strategie.prochainCoup();
        assertEquals("t", result);
    }

    @Test
    void testBasicPatternWithoutRandom() {
        // Always use inherited behavior
        when(mockRandom.nextBoolean()).thenReturn(false);

        // First move should be "c"
        String result = strategie.prochainCoup();
        assertEquals("c", result);

        // Opponent plays "t"
        strategie.miseAJourDernierCoupAdversaire("t");

        // Should copy opponent's "t"
        result = strategie.prochainCoup();
        assertEquals("t", result);

        // Opponent plays "c"
        strategie.miseAJourDernierCoupAdversaire("c");

        // Should copy opponent's "c"
        result = strategie.prochainCoup();
        assertEquals("c", result);
    }
    `

}

 */