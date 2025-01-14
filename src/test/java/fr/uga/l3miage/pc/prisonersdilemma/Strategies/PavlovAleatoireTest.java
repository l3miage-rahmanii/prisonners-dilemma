package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Random;

import fr.uga.l3miage.pc.strategies.PavlovAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class PavlovAleatoireTest {
    private static final int HISTORY_SIZE = 100;
    private String[] historique;
    private PavlovAleatoire strategie;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        historique = new String[HISTORY_SIZE];
        mockRandom = mock(Random.class);

        try {
            strategie = new PavlovAleatoire(historique);
            java.lang.reflect.Field randomField = strategie.getClass().getDeclaredField("random");
            randomField.setAccessible(true);
            randomField.set(strategie, mockRandom);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test comportement aléatoire - coopération")
    void testRandomCooperation() {
        // Force random behavior (10% chance) and choose cooperation
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(true);

        assertEquals("c", strategie.prochainCoup(),
                "Should cooperate when random behavior is triggered and returns true");
    }

    @Test
    @DisplayName("Test comportement aléatoire - trahison")
    void testRandomBetrayal() {
        // Force random behavior (10% chance) and choose betrayal
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(false);

        assertEquals("t", strategie.prochainCoup(),
                "Should betray when random behavior is triggered and returns false");
    }

    @Test
    @DisplayName("Test comportement Pavlov hérité - premier coup")
    void testPavlovFirstMove() {
        // Force inherited behavior (90% chance)
        when(mockRandom.nextDouble()).thenReturn(0.5);

        assertEquals("c", strategie.prochainCoup(),
                "First move should be cooperation when using inherited behavior");
    }

    @Test
    @DisplayName("Test comportement Pavlov hérité - récompense")
    void testPavlovReward() {
        // Force inherited behavior
        when(mockRandom.nextDouble()).thenReturn(0.5);

        // Play initial move
        strategie.prochainCoup();

        // Simulate mutual cooperation (reward)
        strategie.miseAJourDernierCoupAdversaire("c", 3);  // High score for cooperation

        // Should stick with cooperation after reward
        assertEquals("c", strategie.prochainCoup(),
                "Should cooperate after mutual cooperation with high score");
    }

    @Test
    @DisplayName("Test comportement Pavlov hérité - punition")
    void testPavlovPunishment() {
        // Force inherited behavior
        when(mockRandom.nextDouble()).thenReturn(0.5);

        // Play initial move
        strategie.prochainCoup();

        // Simulate being betrayed
        strategie.miseAJourDernierCoupAdversaire("t", 0);  // Low score for being betrayed

        // Should switch to betrayal after being betrayed
        assertEquals("t", strategie.prochainCoup(),
                "Should betray after being betrayed with low score");
    }

    @Test
    @DisplayName("Test séquence mixte de coups")
    void testMixedSequence() {
        // Set up sequence: random, inherited, random
        when(mockRandom.nextDouble())
                .thenReturn(0.05)  // Random (10%)
                .thenReturn(0.5)   // Inherited (90%)
                .thenReturn(0.05); // Random (10%)

        when(mockRandom.nextBoolean())
                .thenReturn(true)  // Random cooperation
                .thenReturn(false); // Random betrayal

        // First move (random cooperation)
        assertEquals("c", strategie.prochainCoup());
        strategie.miseAJourDernierCoupAdversaire("c", 3);

        // Second move (inherited - should cooperate after mutual cooperation)
        assertEquals("c", strategie.prochainCoup());
        strategie.miseAJourDernierCoupAdversaire("t", 0);

        // Third move (random betrayal)
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test limites du seuil aléatoire")
    void testRandomThresholdBoundaries() {
        // Test exactly at threshold (should use inherited behavior)
        when(mockRandom.nextDouble()).thenReturn(0.1);
        assertEquals("c", strategie.prochainCoup());

        // Test just below threshold (should use random behavior)
        when(mockRandom.nextDouble()).thenReturn(0.099);
        when(mockRandom.nextBoolean()).thenReturn(true);
        assertEquals("c", strategie.prochainCoup());

        // Test just above threshold (should use inherited behavior)
        when(mockRandom.nextDouble()).thenReturn(0.101);
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test séquence de scores variés")
    void testVariousScores() {
        // Force inherited behavior
        when(mockRandom.nextDouble()).thenReturn(0.5);

        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("c", 5);  // High reward
        assertEquals("c", strategie.prochainCoup(), "Should cooperate after high reward");

        strategie.miseAJourDernierCoupAdversaire("t", 1);  // Low punishment
        assertEquals("t", strategie.prochainCoup(), "Should betray after low score");

        strategie.miseAJourDernierCoupAdversaire("c", 3);  // Medium reward
        assertEquals("c", strategie.prochainCoup(), "Should cooperate after medium reward");
    }
}