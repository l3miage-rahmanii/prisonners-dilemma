package fr.uga.l3miage.pc.prisonersdilemma.Strategies;


import fr.uga.l3miage.pc.strategies.DonnantPourDeuxDonnants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DonnantPourDeuxDonnantsTest {

    private DonnantPourDeuxDonnants strategie;

    @BeforeEach
    void setUp() {
        // Historique de taille 10 pour simuler les coups précédents
        String[] historique = new String[10];
        strategie = new DonnantPourDeuxDonnants(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup doit être toujours "c" (coopérer), car il n'y a pas d'historique suffisant
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testDeuxDerniersCoupsTrahison() {
        // Simuler deux coups de trahison
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("t");

        // Le prochain coup doit être "t"
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testDernierCoupCooperation() {
        // Simuler un mélange de coopération et trahison
        strategie.miseAJourDernierCoupAdversaire("c"); // Adversaire coopère
        strategie.miseAJourDernierCoupAdversaire("t"); // Adversaire trahit

        // Le prochain coup doit être "c" car les deux derniers coups ne sont pas tous "t"
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testProchainCoupAvecHistoriqueMixte() {
        // Simuler un historique complet avec un mélange
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("c");

        // Vérifier le prochain coup
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testProchainCoupAvecTrahisonsSuccessives() {
        // Simuler un historique où les deux derniers coups sont "t"
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("t");

        // Le prochain coup doit être "t"
        assertEquals("t", strategie.prochainCoup());
    }
}
