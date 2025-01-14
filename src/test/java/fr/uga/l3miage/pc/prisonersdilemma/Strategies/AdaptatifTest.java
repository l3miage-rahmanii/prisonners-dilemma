package fr.uga.l3miage.pc.prisonersdilemma.Strategies;


import fr.uga.l3miage.pc.strategies.Adaptatif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class AdaptatifTest {
    /*
    private static final int HISTORY_SIZE = 100;
    private String[] historique;
    private Adaptatif strategie;

    @BeforeEach
    void setUp() {
        historique = new String[HISTORY_SIZE];
        strategie = new Adaptatif(historique);
    }

    @Test
    void testInitialSequence() {
        String expected = "cccccccttttt";
        for (int i = 0; i < expected.length(); i++) {
            String coup = strategie.prochainCoup();
            String expectedChar = String.valueOf(expected.charAt(i));

            assertEquals(expectedChar, coup,
                    String.format("Move %d should match initial sequence", i));

            // Simulate some opponent move and score
            strategie.miseAJourDernierCoupAdversaire("c", 1);
        }
    }


    @Test
    void testAdaptationToCooperation() {
        // Play through initial sequence
        String initial = "cccccccttttt";
        for (int i = 0; i < initial.length(); i++) {
            String coup = strategie.prochainCoup();
            // Simulate opponent always cooperating with high scores for cooperation
            strategie.miseAJourDernierCoupAdversaire("c", coup.equals("c") ? 3 : 1);
        }

        // After initial sequence, strategy should prefer cooperation due to higher scores
        assertEquals("c", strategie.prochainCoup(),
                "Should choose cooperation when it has historically yielded better scores");
    }

    @Test
    void testAdaptationToBetrayals() {
        // Play through initial sequence
        String initial = "cccccccttttt";
        for (int i = 0; i < initial.length(); i++) {
            String coup = strategie.prochainCoup();
            // Simulate opponent always betraying with high scores for betrayal
            strategie.miseAJourDernierCoupAdversaire("t", coup.equals("t") ? 5 : 0);
        }

        // After initial sequence, strategy should prefer betrayal due to higher scores
        assertEquals("t", strategie.prochainCoup(),
                "Should choose betrayal when it has historically yielded better scores");
    }

    @Test
    void testEqualScores() {
        // Play through initial sequence with equal scores for both strategies
        String initial = "cccccccttttt";
        for (int i = 0; i < initial.length(); i++) {
            String coup = strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire("c", 2);
        }

        // When scores are equal, should default to cooperation (>= in the comparison)
        assertEquals("c", strategie.prochainCoup(),
                "Should choose cooperation when scores are equal");
    }

    @Test
    void testDivisionByZero() {
        // This test verifies that the strategy handles the case where no moves of a certain type have been made
        String initial = "cccccccttttt";
        for (int i = 0; i < initial.length(); i++) {
            strategie.prochainCoup();
            // Don't update scores, leaving counters at 0
        }

        // Should not throw exception due to division by zero
        assertDoesNotThrow(() -> strategie.prochainCoup(),
                "Should handle division by zero case gracefully");
    }

    @Test
    void testHistoryUpdate() {
        // Test that the history array is properly updated
        String[] expectedHistory = new String[HISTORY_SIZE];
        String initial = "cccccccttttt";

        for (int i = 0; i < initial.length(); i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire("c", 1);
            expectedHistory[i] = "c";
        }

        // Verify the first 12 entries of history
        for (int i = 0; i < initial.length(); i++) {
            assertEquals(expectedHistory[i], historique[i],
                    "History should be properly updated at position " + i);
        }
    }

     */
}