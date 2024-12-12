package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.DonnantDonnantStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DonnantDonnantStrategieTest {

    private DonnantDonnantStrategie strategie;

    @BeforeEach
    void setUp() {
        // Historique de taille 10 pour simuler les coups précédents
        String[] historique = new String[10];
        strategie = new DonnantDonnantStrategie(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup doit être toujours "c" (coopérer)
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testMiseAJourEtProchainCoup() {
        // Simuler les coups adverses et vérifier les réponses
        strategie.miseAJourDernierCoupAdversaire("t"); // Adversaire trahit
        assertEquals("t", strategie.prochainCoup()); // Stratégie réagit avec "t"

        strategie.miseAJourDernierCoupAdversaire("c"); // Adversaire coopère
        assertEquals("c", strategie.prochainCoup()); // Stratégie réagit avec "c"
    }

    @Test
    void testHistoriqueApresPlusieursTours() {
        // Simuler plusieurs tours
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifier l'état de l'historique
        String[] historique = strategie.getHistorique();
        assertEquals("t", historique[0]);
        assertEquals("c", historique[1]);
        assertEquals("t", historique[2]);
    }

    @Test
    void testIndexApresPlusieursTours() {
        // Simuler plusieurs tours
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("c");

        // Vérifier que l'index a été correctement incrémenté
        assertEquals(2, strategie.getIndex());
    }

    @Test
    void testProchainCoupAvecHistoriquePlein() {
        // Simuler un historique complet
        for (int i = 0; i < 10; i++) {
            strategie.miseAJourDernierCoupAdversaire(i % 2 == 0 ? "c" : "t"); // Alternance "c" et "t"
        }

        // Vérifier le prochain coup
        assertEquals("t", strategie.prochainCoup());
    }
}
