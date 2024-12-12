package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.RancunierDoux;
import org.junit.jupiter.api.BeforeEach;
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
}
