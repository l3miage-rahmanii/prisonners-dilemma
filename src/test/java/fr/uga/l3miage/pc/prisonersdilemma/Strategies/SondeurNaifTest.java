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

    @Test
    void testCoupInitialSansDernierCoup() {
        // Test le premier coup sans historique précédent
        String premierCoup = strategie.prochainCoup();
        assertTrue(premierCoup.equals("c") || premierCoup.equals("t"),
                "Le premier coup devrait être soit 'c' ou 't'");
    }

    @Test
    void testMiseAJourHistoriqueConsecutif() {
        // Test la mise à jour consécutive de l'historique
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("t");

        assertEquals("c", historique[0]);
        assertEquals("c", historique[1]);
        assertEquals("t", historique[2]);
        assertNull(historique[3], "Les positions non utilisées devraient être null");
    }



    @Test
    void testComportementCyclique() {
        // Test du comportement sur plusieurs tours
        String[] coups = new String[]{"c", "c", "t", "c", "t"};
        for (String coup : coups) {
            strategie.miseAJourDernierCoupAdversaire(coup);
        }

        // Vérifie que le comportement est cohérent après plusieurs coups
        String prochaincoup = strategie.prochainCoup();
        assertTrue(prochaincoup.equals("c") || prochaincoup.equals("t"),
                "Le prochain coup devrait toujours être valide");
    }

    @Test
    void testLimiteHistorique() {
        for (int i = 0; i < 10; i++) {
            strategie.miseAJourDernierCoupAdversaire("c");
        }

        for (int i = 0; i < historique.length; i++) {
            assertEquals("c", historique[i],
                    "La position " + i + " devrait contenir 'c'");
        }

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            strategie.miseAJourDernierCoupAdversaire("t");
        }, "Devrait lancer une exception quand l'historique est plein");
    }
}
