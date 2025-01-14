package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.PavlovStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PavlovStrategieTest {


    private static final int TAILLE_HISTORIQUE = 10;
    private PavlovStrategie strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        historique = new String[TAILLE_HISTORIQUE];
        strategie = new PavlovStrategie(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup devrait être une coopération
        assertEquals("c", strategie.prochainCoup(),
                "Le premier coup devrait être une coopération ('c')");
    }

    @Test
    void testRepeterCoupScore5() {
        // Test avec un score de 5 (récompense maximale)
        strategie.prochainCoup(); // Premier coup (c)
        strategie.miseAJourDernierCoupAdversaire("c", 5);

        // Devrait répéter le coup de l'adversaire (c)
        assertEquals("c", strategie.prochainCoup(),
                "Devrait répéter le coup de l'adversaire après un score de 5");
    }

    @Test
    void testRepeterCoupScore3() {
        // Test avec un score de 3
        strategie.prochainCoup(); // Premier coup (c)
        strategie.miseAJourDernierCoupAdversaire("t", 3);

        // Devrait répéter le coup de l'adversaire (t)
        assertEquals("t", strategie.prochainCoup(),
                "Devrait répéter le coup de l'adversaire après un score de 3");
    }

    @Test
    void testChangerCoupScore1() {
        // Test avec un score de 1
        strategie.prochainCoup(); // Premier coup (c)
        strategie.miseAJourDernierCoupAdversaire("t", 1);

        // Devrait changer de coup
        assertEquals("t", strategie.prochainCoup(),
                "Devrait changer de coup après un score de 1");
    }

    @Test
    void testChangerCoupScore0() {
        // Test avec un score de 0
        strategie.prochainCoup(); // Premier coup (c)
        strategie.miseAJourDernierCoupAdversaire("t", 0);

        // Devrait changer de coup
        assertEquals("t", strategie.prochainCoup(),
                "Devrait changer de coup après un score de 0");
    }

    @Test
    void testSequenceComplexe() {
        String[] coupAdversaire = {"c", "t", "c", "t"};
        int[] scores = {5, 1, 3, 0};
        String[] reponseAttendue = {"c", "c", "t", "c"};

        for (int i = 0; i < coupAdversaire.length; i++) {
            String coupJoue = strategie.prochainCoup();
            assertEquals(reponseAttendue[i], coupJoue,
                    "Coup incorrect à l'itération " + i);
            strategie.miseAJourDernierCoupAdversaire(coupAdversaire[i], scores[i]);
        }
    }

    @Test
    void testDepassementHistorique() {

        for (int i = 0; i < TAILLE_HISTORIQUE; i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire("c", 5);
        }

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> strategie.miseAJourDernierCoupAdversaire("c", 5),
                "Une exception devrait être levée lors du dépassement de l'historique");
    }

    @Test
    void testConsistanceHistorique() {
        // Vérifie que l'historique est correctement mis à jour
        String[] coups = {"c", "t", "c"};
        int[] scores = {5, 3, 1};

        for (int i = 0; i < coups.length; i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire(coups[i], scores[i]);
            assertEquals(coups[i], historique[i],
                    "L'historique n'est pas correctement mis à jour à l'index " + i);
        }
    }
}
