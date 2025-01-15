package fr.uga.l3miage.pc.prisonersdilemma.Strategies;
/*
import fr.uga.l3miage.pc.strategies.ToujoursTrahirStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToujoursTrahirStrategieTest {

    private ToujoursTrahirStrategie strategie;

    @BeforeEach
    void setUp() {
        strategie = new ToujoursTrahirStrategie();
    }

    @Test
    void testProchainCoupRetourneToujoursTrahir() {
        // Arrange & Act & Assert
        assertEquals("t", strategie.prochainCoup(), "La stratégie doit toujours retourner 't'");

        // Vérifier la consistance sur plusieurs appels
        for (int i = 0; i < 5; i++) {
            assertEquals("t", strategie.prochainCoup(),
                    "La stratégie doit continuer à retourner 't' même après plusieurs appels");
        }
    }

    @Test
    void testStrategyImmutability() {
        // Arrange & Act
        String premierCoup = strategie.prochainCoup();
        String deuxiemeCoup = strategie.prochainCoup();
        String troisiemeCoup = strategie.prochainCoup();

        // Assert
        assertEquals(premierCoup, deuxiemeCoup, "Les coups successifs doivent être identiques");
        assertEquals(deuxiemeCoup, troisiemeCoup, "Les coups successifs doivent être identiques");
        assertEquals("t", premierCoup, "Tous les coups doivent être 't'");
    }
}


 */