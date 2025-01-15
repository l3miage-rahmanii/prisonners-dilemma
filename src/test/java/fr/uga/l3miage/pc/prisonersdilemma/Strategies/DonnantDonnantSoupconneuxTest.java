package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import fr.uga.l3miage.pc.strategies.DonnantDonnantSoupconneux;
import static org.junit.jupiter.api.Assertions.*;

public class DonnantDonnantSoupconneuxTest {
    /*
    private static final int TAILLE_HISTORIQUE = 10;
    private DonnantDonnantSoupconneux strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        historique = new String[TAILLE_HISTORIQUE];
        strategie = new DonnantDonnantSoupconneux(historique);
    }

        @Test
        void testPremierCoup() {
            assertEquals("t", strategie.prochainCoup(),
                    "Le premier coup devrait être une trahison ('t')");
        }

        @Test
        void testReactionCooperationAdversaire() {
            // Premier coup (trahison)
            strategie.prochainCoup();

            // L'adversaire coopère
            strategie.miseAJourDernierCoupAdversaire("c");

            // La stratégie devrait coopérer en retour
            assertEquals("c", strategie.prochainCoup(),
                    "Devrait coopérer après une coopération de l'adversaire");
        }

        @Test
        void testReactionTrahisonAdversaire() {
            // Premier coup (trahison)
            strategie.prochainCoup();

            // L'adversaire trahit
            strategie.miseAJourDernierCoupAdversaire("t");

            // La stratégie devrait trahir en retour
            assertEquals("t", strategie.prochainCoup(),
                    "Devrait trahir après une trahison de l'adversaire");
        }

        @Test
        void testSequenceComplexe() {
            // Test d'une séquence de coups
            String[] sequenceAdversaire = {"c", "t", "c", "c", "t"};
            String[] reponseAttendue = {"t", "c", "t", "c", "c"};

            for (int i = 0; i < sequenceAdversaire.length; i++) {
                String coupJoue = strategie.prochainCoup();
                assertEquals(reponseAttendue[i], coupJoue,
                        "Coup incorrect à l'itération " + i);
                strategie.miseAJourDernierCoupAdversaire(sequenceAdversaire[i]);
            }
        }

    @Test
    void testDepassementHistorique() {
        for (int i = 0; i < TAILLE_HISTORIQUE; i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire("c");
        }

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> strategie.miseAJourDernierCoupAdversaire("c"),
                "Une exception devrait être levée lors du dépassement de l'historique");
    }

        @Test
        void testMiseAJourHistorique() {
            strategie.prochainCoup(); // Premier coup

            // Vérifie que l'historique est correctement mis à jour
            strategie.miseAJourDernierCoupAdversaire("c");
            assertEquals("c", historique[0],
                    "L'historique devrait contenir le coup de l'adversaire");

            strategie.miseAJourDernierCoupAdversaire("t");
            assertEquals("t", historique[1],
                    "L'historique devrait contenir le deuxième coup de l'adversaire");
        }

     */
}
