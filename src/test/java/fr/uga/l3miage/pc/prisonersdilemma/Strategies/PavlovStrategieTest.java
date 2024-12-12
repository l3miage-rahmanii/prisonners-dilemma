package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.PavlovStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PavlovStrategieTest {

    private PavlovStrategie strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        // Historique initial vide, taille 10 pour simuler plusieurs coups
        historique = new String[10];
        strategie = new PavlovStrategie(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup doit toujours être "c" (coopérer)
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testRepeteCoupAvecScore5() {
        // Si le score est 5, répète le dernier coup
        strategie.miseAJourDernierCoupAdversaire("c", 5);
        assertEquals("c", strategie.prochainCoup());

        strategie.miseAJourDernierCoupAdversaire("t", 5);
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testRepeteCoupAvecScore3() {
        // Si le score est 3, répète le dernier coup
        strategie.miseAJourDernierCoupAdversaire("c", 3);
        assertEquals("c", strategie.prochainCoup());

        strategie.miseAJourDernierCoupAdversaire("t", 3);
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testChangeCoupAvecScoreNon3Ou5() {
        // Si le score n'est ni 3 ni 5, change de coup
        strategie.miseAJourDernierCoupAdversaire("c", 1);
        assertEquals("c", strategie.prochainCoup());

        strategie.miseAJourDernierCoupAdversaire("t", 2);
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testHistoriqueApresPlusieursTours() {
        // Simuler plusieurs mises à jour
        strategie.miseAJourDernierCoupAdversaire("c", 5);
        strategie.miseAJourDernierCoupAdversaire("t", 3);
        strategie.miseAJourDernierCoupAdversaire("c", 1);

        // Vérifier l'état de l'historique
        assertEquals("c", historique[0]);
        assertEquals("t", historique[1]);
        assertEquals("c", historique[2]);
    }

    @Test
    void testAlternanceEntreCooperationEtTrahison() {
        // Simuler une alternance de coups
        strategie.miseAJourDernierCoupAdversaire("t", 1);
        assertEquals("t", strategie.prochainCoup()); // Change à "t"

        strategie.miseAJourDernierCoupAdversaire("c", 1);
        assertEquals("c", strategie.prochainCoup()); // Change à "c"
    }
}
