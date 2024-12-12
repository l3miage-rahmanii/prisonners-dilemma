package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.ToujoursTrahirStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToujoursTrahirStrategieTest {

    private ToujoursTrahirStrategie strategie;

    @BeforeEach
    void setUp() {
        // Initialisation de la stratégie
        strategie = new ToujoursTrahirStrategie();
    }

    @Test
    void testPremierCoup() {
        // Vérifie que le premier coup est toujours "t"
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testToujoursTrahison() {
        // Vérifie que la stratégie trahit toujours
        for (int i = 0; i < 10; i++) {
            assertEquals("t", strategie.prochainCoup());
        }
    }
}
