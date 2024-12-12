package fr.uga.l3miage.pc.prisonersdilemma.Strategies;


import fr.uga.l3miage.pc.strategies.Graduel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraduelTest {

    private Graduel strategie;

    @BeforeEach
    void setUp() {
        strategie = new Graduel();
    }

    @Test
    void testPremierCoup() {
        // Le premier coup doit être "c" (coopérer)
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testCooperationSansTrahisonAdverse() {
        // Si l'adversaire coopère tout le temps, la stratégie doit toujours coopérer
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());

        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testTrahisonUniqueAdverse() {
        // Si l'adversaire trahit une fois, la stratégie doit répliquer avec une trahison unique
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup()); // Représaille
        assertEquals("c", strategie.prochainCoup()); // Coopération après représailles
    }

    @Test
    void testTrahisonsSuccessivesAdverses() {
        // Si l'adversaire trahit plusieurs fois, la stratégie doit répliquer avec autant de trahisons
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("t");

        assertEquals("t", strategie.prochainCoup()); // Première représaille
        assertEquals("t", strategie.prochainCoup()); // Deuxième représaille
        assertEquals("c", strategie.prochainCoup()); // Coopération après représailles
    }

    @Test
    void testAlternanceTrahisonEtCooperationAdverse() {
        // Si l'adversaire alterne entre trahison et coopération
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup()); // Représaille pour la trahison
        assertEquals("c", strategie.prochainCoup()); // Coopération après représailles

        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup()); // Coopération après coopération

        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup()); // Représaille pour la nouvelle trahison
    }
}
