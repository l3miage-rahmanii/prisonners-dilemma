package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.RancunierDoux;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RancunierDouxTest {

    private RancunierDoux strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        // Historique initial vide, taille 10 pour simuler plusieurs coups
        historique = new String[10];
        strategie = new RancunierDoux(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup doit toujours être "c" (coopérer)
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testDevientRancunierApresTrahison() {
        // Simuler une trahison de l'adversaire
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifier que la stratégie devient rancunière et répond par "t"
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testPunitTroisFoisPuisReprendCooperation() {
        // Simuler une trahison pour déclencher les représailles
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifier les trois représailles
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());

        // Après les représailles, elle coopère à nouveau
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testResteCooperativeSiPasDeTrahison() {
        // Simuler plusieurs tours sans trahison de l'adversaire
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("c");

        // La stratégie doit continuer de coopérer
        assertEquals("c", strategie.prochainCoup());
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testHistoriqueApresPlusieursTours() {
        // Simuler plusieurs mises à jour
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("c");

        // Vérifier que l'historique est mis à jour correctement
        assertEquals("c", historique[0]);
        assertEquals("t", historique[1]);
        assertEquals("c", historique[2]);
    }

    @Test
    void testTrahisonNeProlongePasLesRepresailles() {
        // Simuler une trahison initiale
        strategie.miseAJourDernierCoupAdversaire("t");

        // Punit trois fois
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());

        // Coopère à nouveau
        assertEquals("c", strategie.prochainCoup());

        // Une nouvelle trahison ne prolonge pas les représailles en cours
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test comportement initial")
    void testComportementInitial() {
        assertEquals("c", strategie.prochainCoup(),
                "Devrait coopérer au premier coup");
    }

    @Test
    @DisplayName("Test coopération continue sans trahison")
    void testCooperationContinue() {
        // Plusieurs coups de coopération consécutifs
        for (int i = 0; i < 5; i++) {
            assertEquals("c", strategie.prochainCoup(),
                    "Devrait continuer à coopérer tant qu'il n'y a pas de trahison");
            strategie.miseAJourDernierCoupAdversaire("c");
        }
    }

    @Test
    @DisplayName("Test réaction à une trahison")
    void testReactionTrahison() {
        // Premier coup - coopération normale
        assertEquals("c", strategie.prochainCoup());

        // L'adversaire trahit
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie les trois coups de représailles
        assertEquals("t", strategie.prochainCoup(), "Premier coup de représailles");
        assertEquals("t", strategie.prochainCoup(), "Deuxième coup de représailles");
        assertEquals("t", strategie.prochainCoup(), "Troisième coup de représailles");

        // Vérifie le retour à la coopération
        assertEquals("c", strategie.prochainCoup(),
                "Devrait retourner à la coopération après les représailles");
    }

    @Test
    @DisplayName("Test multiples séquences de trahison")
    void testMultiplesTrahisons() {
        // Première séquence
        assertEquals("c", strategie.prochainCoup());
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie les représailles
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());
        assertEquals("c", strategie.prochainCoup());

        // Deuxième séquence
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie les nouvelles représailles
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test trahison pendant les représailles")
    void testTrahisonPendantRepresailles() {
        // Déclenche les représailles
        assertEquals("c", strategie.prochainCoup());
        strategie.miseAJourDernierCoupAdversaire("t");

        // Premier coup de représailles
        assertEquals("t", strategie.prochainCoup());

        // L'adversaire trahit pendant les représailles
        strategie.miseAJourDernierCoupAdversaire("t");

        // Continue les représailles normalement
        assertEquals("t", strategie.prochainCoup());
        assertEquals("t", strategie.prochainCoup());
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test mise à jour historique")
    void testMiseAJourHistorique() {
        String[] sequence = {"c", "t", "c", "t", "c"};

        for (String coup : sequence) {
            strategie.miseAJourDernierCoupAdversaire(coup);
        }

        // Vérifie que l'historique a été correctement mis à jour
        for (int i = 0; i < sequence.length; i++) {
            assertEquals(sequence[i], historique[i],
                    "L'historique devrait contenir la séquence exacte des coups");
        }
    }

    @Test
    @DisplayName("Test comportement après représailles")
    void testComportementApresRepresailles() {
        // Déclenche les représailles
        assertEquals("c", strategie.prochainCoup());
        strategie.miseAJourDernierCoupAdversaire("t");

        // Effectue les trois coups de représailles
        for (int i = 0; i < 3; i++) {
            assertEquals("t", strategie.prochainCoup());
            strategie.miseAJourDernierCoupAdversaire("c");
        }

        // Vérifie le retour à la coopération et la persistance
        for (int i = 0; i < 3; i++) {
            assertEquals("c", strategie.prochainCoup());
            strategie.miseAJourDernierCoupAdversaire("c");
        }
    }
}
