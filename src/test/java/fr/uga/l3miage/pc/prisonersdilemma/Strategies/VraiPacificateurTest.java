package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.VraiPacificateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VraiPacificateurTest {

    private VraiPacificateur strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        // Initialisation de l'historique pour simuler les coups précédents
        historique = new String[10];
        strategie = new VraiPacificateur(historique);
    }

    @Test
    void testPremierCoup() {
        // Vérifie que le premier coup est toujours "c" (coopération)
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testCooperationSansTrahison() {
        // Simule des coups où l'adversaire ne trahit pas
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testReponseApresDoubleTrahison() {
        // Simule des trahisons consécutives par l'adversaire
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie que le coup suivant est aléatoire, soit "c" ou "t"
        String coup = strategie.prochainCoup();
        assertTrue(coup.equals("c") || coup.equals("t"));
    }

    @Test
    void testProbabiliteApresDoubleTrahison() {
        // Simule plusieurs parties pour tester la probabilité de coopération après une double trahison
        int coopCount = 0;
        int betrayCount = 0;

        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("t");

        // Effectuer 100 essais pour vérifier la distribution
        for (int i = 0; i < 100; i++) {
            String coup = strategie.prochainCoup();
            if (coup.equals("c")) {
                coopCount++;
            } else if (coup.equals("t")) {
                betrayCount++;
            }
        }

        // Vérifie qu'environ 30% des coups sont "c" (coopération)
        double coopRate = coopCount / 100.0;
        assertTrue(coopRate >= 0.2 && coopRate <= 0.4, "La probabilité de coopération doit être proche de 30%");
    }
}
