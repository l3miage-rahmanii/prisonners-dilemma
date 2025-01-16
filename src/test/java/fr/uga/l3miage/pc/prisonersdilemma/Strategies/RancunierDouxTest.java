package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.RancunierDoux;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
class RancunierDouxTest {

    private RancunierDoux strategy;

    @BeforeEach
    void setUp() {
        strategy = new RancunierDoux();
    }

    @Test
    void testInitialCooperation() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.COOPERER);

        // When
        CoupEnum result = strategy.prochainCoup(history);

        // Then
        assertEquals(CoupEnum.COOPERER, result, "Should cooperate initially when opponent cooperates");
    }

    @Test
    void testReactionToTrahison() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.TRAHIR);

        // When - First move after trahison will be COOPERER but sets up vengeance
        CoupEnum firstResult = strategy.prochainCoup(history);

        // Then
        assertEquals(CoupEnum.COOPERER, firstResult, "Should cooperate on first move but become grudging");

        // Verify the vengeance starts on next move
        CoupEnum secondResult = strategy.prochainCoup(history);
        assertEquals(CoupEnum.TRAHIR, secondResult, "Should start betraying on second move");
    }

    @Test
    void testCompleteCycleOfVengeance() {
        List<CoupEnum> history = Arrays.asList(CoupEnum.TRAHIR);

        // First move will be COOPERER but sets estRancunier to true
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "First move should be COOPERER");

        // Next two moves should be TRAHIR (compteurRepresailles = 2, 1)
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(history), "Second move should be TRAHIR");
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(history), "Third move should be TRAHIR");

        // Next three moves should be COOPERER (compteurRepresailles = 0, -1, -2)
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "Fourth move should return to COOPERER");
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "Fifth move should be COOPERER");
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "Sixth move should be COOPERER");

        // Should return to normal cooperative behavior
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "Should return to normal cooperation");
    }

    @Test
    void testMultipleCyclesOfVengeance() {
        List<CoupEnum> history = new ArrayList<>();

        // First cycle
        history.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "First revenge cycle - move 1");
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(history), "First revenge cycle - move 2");
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(history), "First revenge cycle - move 3");
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "First revenge cycle - move 4");
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "First revenge cycle - move 5");
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "First revenge cycle - move 6");

        // Second cycle
        history.clear();
        history.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.COOPERER, strategy.prochainCoup(history), "Second revenge cycle - move 1");
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(history), "Second revenge cycle - move 2");
        assertEquals(CoupEnum.TRAHIR, strategy.prochainCoup(history), "Second revenge cycle - move 3");
    }

    @Test
    void testCooperationAfterVengeance() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.TRAHIR);

        // Execute full vengeance cycle
        for (int i = 0; i < 5; i++) {
            strategy.prochainCoup(history);
        }

        // Change history to show cooperation
        history = Arrays.asList(CoupEnum.COOPERER);

        // When
        CoupEnum result = strategy.prochainCoup(history);

        // Then
        assertEquals(CoupEnum.COOPERER, result, "Should cooperate after vengeance cycle is complete");
    }

    @Test
    void testConsecutiveTrahisons() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.TRAHIR);

        CoupEnum firstResult = strategy.prochainCoup(history);
        assertEquals(CoupEnum.COOPERER, firstResult, "First move should be COOPERER");

        CoupEnum secondResult = strategy.prochainCoup(history);
        assertEquals(CoupEnum.TRAHIR, secondResult, "Second move should be TRAHIR");

        CoupEnum thirdResult = strategy.prochainCoup(history);
        assertEquals(CoupEnum.TRAHIR, thirdResult, "Third move should be TRAHIR");

        CoupEnum fourthResult = strategy.prochainCoup(history);
        assertEquals(CoupEnum.COOPERER, fourthResult, "Fourth move should be COOPERER");

        CoupEnum fifthResult = strategy.prochainCoup(history);
        assertEquals(CoupEnum.COOPERER, fifthResult, "Fifth move should be COOPERER");

        CoupEnum sixthResult = strategy.prochainCoup(history);
        assertEquals(CoupEnum.COOPERER, sixthResult, "Sixth move should be COOPERER");
    }
}

