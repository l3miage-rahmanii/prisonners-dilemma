package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.DonnantPourDeuxDonnants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DonnantPourDeuxDonnantsTest {

    /**
     * Optional: If you need something done before every parameterized test,
     * you can initialize fields here. Otherwise, you can just do everything
     * inside the test method as well.
     */
    @BeforeEach
    void setUp() {
        // No-op here, or you could do something else if needed
    }

    /**
     * This single parameterized test will replace all the previous
     * individual tests by providing the scenario inputs and the
     * expected outcome via MethodSource.
     */
    @ParameterizedTest(name = "[{index}] With moves = {0}, expected next move = {1}")
    @MethodSource("provideScenarios")
    void testDonnantPourDeuxDonnants(String[] adversaryMoves, String expectedNextMove) {
        // Create the strategy with an empty (size 10) history
        String[] historique = new String[10];
        DonnantPourDeuxDonnants strategie = new DonnantPourDeuxDonnants(historique);

        // Simulate adversary moves
        for (String move : adversaryMoves) {
            strategie.miseAJourDernierCoupAdversaire(move);
        }

        // Check the strategy’s next move
        assertEquals(expectedNextMove, strategie.prochainCoup());
    }

    /**
     * Supplies test cases:
     *  1) No moves yet => first move should be "c".
     *  2) Two trahisons => next move "t".
     *  3) Mix of coop and trahison => next "c".
     *  4) t, t, c, c => next "c".
     *  5) Another scenario with t, t => next "t" (similar to #2).
     */
    private static Stream<Arguments> provideScenarios() {
        return Stream.of(
                // Test 1: First move always "c" if no history
                Arguments.of(new String[]{}, "c"),

                // Test 2: Two last moves are "t" => next is "t"
                Arguments.of(new String[]{"t", "t"}, "t"),

                // Test 3: c, t => not both 't' => next is "c"
                Arguments.of(new String[]{"c", "t"}, "c"),

                // Test 4: t, t, c, c => last two not both 't' => next is "c"
                Arguments.of(new String[]{"t", "t", "c", "c"}, "c"),

                // Test 5: Another scenario with two trahisons => next is "t"
                Arguments.of(new String[]{"t", "t"}, "t")
        );
    }
}
