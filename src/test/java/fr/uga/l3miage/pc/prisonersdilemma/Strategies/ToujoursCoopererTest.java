package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.ToujoursCooperer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToujoursCoopererTest {

    private ToujoursCooperer strategie;

    @BeforeEach
    void setUp() {
        // Initialisation de la stratégie
        strategie = new ToujoursCooperer();
    }

    @Test
    void testPremierCoup() {
        // Vérifie que le premier coup est toujours "c"
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testToujoursCooperatif() {
        // Vérifie que la stratégie coopère toujours
        for (int i = 0; i < 10; i++) {
            assertEquals("c", strategie.prochainCoup());
        }
    }
}
