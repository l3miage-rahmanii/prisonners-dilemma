package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Random;

import fr.uga.l3miage.pc.strategies.DonnantPourDeuxDonnantsAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class DonnantPourDeuxDonnantsAleatoireTest {
    private static final int HISTORY_SIZE = 100;
    private String[] historique;
    private DonnantPourDeuxDonnantsAleatoire strategie;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        historique = new String[HISTORY_SIZE];
        mockRandom = mock(Random.class);

        try {
            strategie = new DonnantPourDeuxDonnantsAleatoire(historique);
            java.lang.reflect.Field randomField = strategie.getClass().getDeclaredField("random");
            randomField.setAccessible(true);
            randomField.set(strategie, mockRandom);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test random behavior with cooperation")
    void testRandomBehaviorCooperation() {
        // Mock 10% chance of random behavior (nextDouble < 0.1) and choose cooperation
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(true);

        assertEquals("c", strategie.prochainCoup(),
                "Should return 'c' when random is triggered and true is returned");
    }

    @Test
    @DisplayName("Test random behavior with betrayal")
    void testRandomBehaviorBetrayal() {
        // Mock 10% chance of random behavior (nextDouble < 0.1) and choose betrayal
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(false);

        assertEquals("t", strategie.prochainCoup(),
                "Should return 't' when random is triggered and false is returned");
    }

    @Test
    @DisplayName("Test inherited behavior (90% case)")
    void testInheritedBehavior() {
        // Mock 90% chance of inherited behavior (nextDouble >= 0.1)
        when(mockRandom.nextDouble()).thenReturn(0.2);

        // First move should be "c" (inherited behavior)
        assertEquals("c", strategie.prochainCoup());

        // Simulate two cooperative moves from opponent
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("c");

        // Should still use inherited behavior
        when(mockRandom.nextDouble()).thenReturn(0.5);
        assertEquals("c", strategie.prochainCoup(),
                "Should use inherited behavior when random is not triggered");
    }

    @Test
    @DisplayName("Test mixed sequence of moves")
    void testMixedSequence() {
        // First move: use inherited (90%)
        when(mockRandom.nextDouble()).thenReturn(0.5);
        assertEquals("c", strategie.prochainCoup());

        // Update opponent's move
        strategie.miseAJourDernierCoupAdversaire("t");

        // Second move: use random (10%) - choose cooperation
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(true);
        assertEquals("c", strategie.prochainCoup());

        // Update opponent's move
        strategie.miseAJourDernierCoupAdversaire("t");

        // Third move: use inherited (90%)
        when(mockRandom.nextDouble()).thenReturn(0.5);
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test boundary conditions for random threshold")
    void testRandomThresholdBoundaries() {
        // Test exactly at 0.1 (should use inherited behavior)
        when(mockRandom.nextDouble()).thenReturn(0.1);
        assertEquals("c", strategie.prochainCoup());

        // Test just below 0.1 (should use random behavior)
        when(mockRandom.nextDouble()).thenReturn(0.099);
        when(mockRandom.nextBoolean()).thenReturn(true);
        assertEquals("c", strategie.prochainCoup());

        // Test just above 0.1 (should use inherited behavior)
        when(mockRandom.nextDouble()).thenReturn(0.101);
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test long sequence with mixed behavior")
    void testLongSequence() {
        String[] opponentMoves = {"c", "c", "t", "t", "c"};

        for (String opponentMove : opponentMoves) {
            // Alternate between random and inherited behavior
            when(mockRandom.nextDouble())
                    .thenReturn(Math.random() < 0.1 ? 0.05 : 0.5);

            if (mockRandom.nextDouble() < 0.1) {
                when(mockRandom.nextBoolean()).thenReturn(true);
            }

            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire(opponentMove);
        }

        // Verify final state is consistent
        assertNotNull(strategie.getHistorique());
        assertTrue(strategie.getIndex() > 0);
    }
}
