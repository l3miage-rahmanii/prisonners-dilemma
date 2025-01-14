package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.DonnantPourDeuxDonnants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DonnantPourDeuxDonnantsTest {

    private static final int TAILLE_HISTORIQUE = 10;
    private DonnantPourDeuxDonnants strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        historique = new String[TAILLE_HISTORIQUE];
        strategie = new DonnantPourDeuxDonnants(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup devrait être une coopération
        assertEquals("c", strategie.prochainCoup(),
                "Le premier coup devrait être une coopération ('c')");
    }

    @Test
    void testCooperationParDefaut() {
        // Test avec une seule trahison
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        assertEquals("c", strategie.prochainCoup(),
                "Devrait coopérer après une seule trahison");
    }

    @Test
    void testReactionDeuxTrahisonsConsecutives() {
        // Première séquence
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        // Deuxième séquence
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        // Devrait trahir après deux trahisons
        assertEquals("t", strategie.prochainCoup(),
                "Devrait trahir après deux trahisons consécutives");
    }

    @Test
    void testRetourCooperationApresTrahisonNonConsecutive() {
        // Séquence: t, c, t
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("c");

        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        assertEquals("c", strategie.prochainCoup(),
                "Devrait coopérer car les trahisons ne sont pas consécutives");
    }

    @Test
    void testSequenceComplexe() {
        // Test d'une séquence complexe de coups
        String[] sequenceAdversaire = {"t", "t", "c", "t", "t", "c"};
        String[] reponseAttendue = {"c", "c", "t", "c", "c", "t"};

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
}
