package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.DonnantDonnantStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DonnantDonnantStrategieTest {
/*
    private DonnantDonnantStrategie strategie;

    @BeforeEach
    void setUp() {
        String[] historique = new String[10];
        strategie = new DonnantDonnantStrategie(historique);
    }

    @Test
    void testPremierCoup() {
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testMiseAJourEtProchainCoup() {
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup());

        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());
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
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("c");

        assertEquals(2, strategie.getIndex());
    }

    @Test
    void testProchainCoupAvecHistoriquePlein() {
        for (int i = 0; i < 10; i++) {
            strategie.miseAJourDernierCoupAdversaire(i % 2 == 0 ? "c" : "t");
        }

        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test premier coup sans mise à jour")
    void testPremierCoupSansMiseAJour() {
        assertEquals("c", strategie.prochainCoup());
        assertEquals("c", strategie.prochainCoup(),
                "Le deuxième coup sans mise à jour devrait aussi être 'c'");
    }

    @Test
    @DisplayName("Test séquence alternée complète")
    void testSequenceAlternee() {
        String[] sequence = {"c", "t", "c", "t", "c"};

        for (String coup : sequence) {
            strategie.miseAJourDernierCoupAdversaire(coup);
            assertEquals(coup, strategie.prochainCoup());
        }
    }

    @Test
    @DisplayName("Test comportement avec des coups identiques consécutifs")
    void testCoupsConsecutifsIdentiques() {
        String[] sequence = {"c", "c", "c", "t", "t", "t"};

        for (String coup : sequence) {
            strategie.miseAJourDernierCoupAdversaire(coup);
            assertEquals(coup, strategie.prochainCoup());
        }
    }

    @Test
    @DisplayName("Test limite de l'historique")
    void testLimiteHistorique() {
        // Remplir l'historique
        for (int i = 0; i < 10; i++) {
            strategie.miseAJourDernierCoupAdversaire("c");
        }

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> strategie.miseAJourDernierCoupAdversaire("c"),
                "Devrait lever une exception quand l'historique est plein");
    }

    @Test
    @DisplayName("Test cohérence entre index et historique")
    void testCoherenceIndexHistorique() {
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals(1, strategie.getIndex());
        assertEquals("t", strategie.getHistorique()[0]);

        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals(2, strategie.getIndex());
        assertEquals("c", strategie.getHistorique()[1]);
    }

    @Test
    @DisplayName("Test répétition de prochain coup sans mise à jour")
    void testRepetitionProchainCoup() {
        strategie.miseAJourDernierCoupAdversaire("t");
        String premierCoup = strategie.prochainCoup();
        String deuxiemeCoup = strategie.prochainCoup();
        String troisiemeCoup = strategie.prochainCoup();

        assertEquals(premierCoup, deuxiemeCoup,
                "Les coups consécutifs sans mise à jour devraient être identiques");
        assertEquals(deuxiemeCoup, troisiemeCoup,
                "Les coups consécutifs sans mise à jour devraient être identiques");
    }



    @Test
    @DisplayName("Test initialisation avec historique vide")
    void testInitialisationHistoriqueVide() {
        DonnantDonnantStrategie strategieVide = new DonnantDonnantStrategie(new String[0]);
        assertEquals("c", strategieVide.prochainCoup(),
                "Devrait retourner 'c' même avec un historique vide");

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> strategieVide.miseAJourDernierCoupAdversaire("c"),
                "Devrait lever une exception lors de la mise à jour avec un historique vide");
    }

 */
}
