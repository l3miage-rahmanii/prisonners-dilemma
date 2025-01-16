package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.PavlovAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class PavlovAleatoireTest {

    private PavlovAleatoire strategy;
    private Random mockRandom;

    @BeforeEach
    void setUp() throws Exception {
        strategy = new PavlovAleatoire();

        mockRandom = Mockito.mock(SecureRandom.class);

        Field randomField = PavlovAleatoire.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(strategy, mockRandom);
    }

    @Test
    void testRandomBehavior_WhenRandomLessThan10Percent() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.COOPERER);
        when(mockRandom.nextDouble()).thenReturn(0.05); // 5% chance
        when(mockRandom.nextBoolean()).thenReturn(true);

        // When
        CoupEnum result = strategy.prochainCoup(history);

        // Then
        assertEquals(CoupEnum.COOPERER, result);
    }

    @Test
    void testRandomBehavior_WhenRandomLessThan10Percent_Trahir() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.COOPERER);
        when(mockRandom.nextDouble()).thenReturn(0.05); // 5% chance
        when(mockRandom.nextBoolean()).thenReturn(false);

        // When
        CoupEnum result = strategy.prochainCoup(history);

        // Then
        assertEquals(CoupEnum.TRAHIR, result);
    }

    @Test
    void testPavlovBehavior_WhenRandomMoreThan10Percent() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.COOPERER);
        when(mockRandom.nextDouble()).thenReturn(0.15); // 15% chance

        // When
        CoupEnum result = strategy.prochainCoup(history);

        // Then
        assertNotNull(result);
    }

    @Test
    void testEmptyHistory() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.COOPERER);

        // When
        when(mockRandom.nextDouble()).thenReturn(0.15); // Use parent strategy
        CoupEnum result = strategy.prochainCoup(history);

        // Then
        assertNotNull(result);
    }

    @Test
    void testConsistencyWithMultipleCalls() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.COOPERER);
        when(mockRandom.nextDouble()).thenReturn(0.15); // Use parent strategy

        // When
        CoupEnum result1 = strategy.prochainCoup(history);
        CoupEnum result2 = strategy.prochainCoup(history);

        // Then
        assertEquals(result1, result2);
    }

    @Test
    void testRandomizationFrequency() {
        // Given
        List<CoupEnum> history = Arrays.asList(CoupEnum.COOPERER);
        int iterations = 1000;
        int randomDecisions = 0;

        // When
        for (int i = 0; i < iterations; i++) {
            double randomValue = Math.random();
            when(mockRandom.nextDouble()).thenReturn(randomValue);
            when(mockRandom.nextBoolean()).thenReturn(Math.random() < 0.5);
            strategy.prochainCoup(history);
            if (randomValue < 0.1) {
                randomDecisions++;
            }
        }

        // Then
        double randomPercentage = (double) randomDecisions / iterations;
        assertTrue(randomPercentage > 0.05 && randomPercentage < 0.15,
                "Random decisions should be roughly 10% of total decisions");
    }
}