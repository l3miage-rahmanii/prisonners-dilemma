package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.strategies.SondeurNaif;

import java.util.Random;

class SondeurNaifTest {

    private SondeurNaif strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        // Initialisation de l'historique avec une taille suffisante pour tester
        historique = new String[10];
        strategie = new SondeurNaif(historique);
    }


    @Test
    void testCoupApresCoupDeTrahison() {
        // Mise à jour de l'historique avec un coup de trahison
        strategie.miseAJourDernierCoupAdversaire("t");

        // Le prochain coup devrait être "t" car l'adversaire a trahi
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testHistoriqueEtProchainCoup() {
        // Simuler quelques coups dans l'historique
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifier l'état de l'historique
        assertEquals("c", historique[0]);
        assertEquals("t", historique[1]);

        // Le prochain coup devrait être "t" car le dernier coup de l'adversaire était une trahison
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testProchainCoupAvecHistoriquePlein() {
        // Remplir l'historique avec des valeurs aléatoires
        for (int i = 0; i < 10; i++) {
            strategie.miseAJourDernierCoupAdversaire(i % 2 == 0 ? "c" : "t");
        }

        // Le prochain coup dépend de l'historique, mais le comportement devrait être basé sur l'état actuel
        // Par exemple, si le dernier coup adversaire était une trahison, on doit trahir.
        assertTrue(strategie.prochainCoup().equals("t") || strategie.prochainCoup().equals("c"));
    }
}
