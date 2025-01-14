package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.PavlovAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

class PavlovAleatoireTest {

    private PavlovAleatoire strategie;

    @BeforeEach
    void setUp() {
        // Initialisation de l'historique avec une taille suffisante pour tester
        String[] historique = new String[10];
        strategie = new PavlovAleatoire(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup devrait être "c", car il n'y a pas d'historique
        assertEquals("c", strategie.prochainCoup());
    }
    
}
