package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.RancunierStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RancunierStrategieTest {

    private static final int TAILLE_HISTORIQUE = 10;
    private RancunierStrategie strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        historique = new String[TAILLE_HISTORIQUE];
        strategie = new RancunierStrategie(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup devrait être une coopération
        assertEquals("c", strategie.prochainCoup(),
                "Le premier coup devrait être une coopération ('c')");
    }

    @Test
    void testCooperationInitiale() {
        // Test de plusieurs coups de coopération
        for (int i = 0; i < 5; i++) {
            assertEquals("c", strategie.prochainCoup(),
                    "Devrait coopérer tant qu'il n'y a pas eu de trahison");
            strategie.miseAJourDernierCoupAdversaire("c");
        }
    }

    @Test
    void testReactionTrahison() {
        // Coopération initiale
        strategie.prochainCoup();
        // L'adversaire trahit
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie que la stratégie trahit en retour
        assertEquals("t", strategie.prochainCoup(),
                "Devrait trahir après une trahison de l'adversaire");
    }

    @Test
    void testRancunePermanente() {
        // Coopération initiale suivie d'une trahison
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie que la stratégie continue de trahir même si l'adversaire revient à la coopération
        for (int i = 0; i < 5; i++) {
            assertEquals("t", strategie.prochainCoup(),
                    "Devrait continuer à trahir même après un retour à la coopération de l'adversaire");
            strategie.miseAJourDernierCoupAdversaire("t");
        }
    }


    @Test
    void testDepassementHistorique() {
        // Remplit l'historique jusqu'à sa limite
        for (int i = 0; i < TAILLE_HISTORIQUE; i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire("c");
        }

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> strategie.miseAJourDernierCoupAdversaire("c"),
                "Une exception devrait être levée lors du dépassement de l'historique");
    }

    @Test
    void testConsistanceHistorique() {
        // Vérifie que l'historique est correctement mis à jour
        String[] coups = {"c", "t", "c"};

        for (int i = 0; i < coups.length; i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire(coups[i]);
            assertEquals(coups[i], historique[i],
                    "L'historique n'est pas correctement mis à jour à l'index " + i);
        }
    }
}
