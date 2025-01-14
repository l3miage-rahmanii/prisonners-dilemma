package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.SondeurRepentant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SondeurRepentantTest {

    private static final int TAILLE_HISTORIQUE = 10;
    private SondeurRepentant strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        historique = new String[TAILLE_HISTORIQUE];
        strategie = new SondeurRepentant(historique);
    }

    @Test
    void testPremierCoupPossible() {
        // Le premier coup devrait être soit "c" soit "t" (à cause de l'aléatoire)
        String premierCoup = strategie.prochainCoup();
        assertTrue(premierCoup.equals("c") || premierCoup.equals("t"),
                "Le premier coup devrait être 'c' ou 't'");
    }


    void testDistributionAleatoire() {
        // Sur un grand nombre d'essais, on devrait voir environ 10% de trahisons
        int nombreEssais = 1000;
        int nombreTrahisons = 0;

        for (int i = 0; i < nombreEssais; i++) {
            if (strategie.prochainCoup().equals("t")) {
                nombreTrahisons++;
            }
        }

        // Vérifie que le taux de trahison est proche de 10% (entre 5% et 15% pour être large)
        double tauxTrahison = (double) nombreTrahisons / nombreEssais;
        assertTrue(tauxTrahison >= 0.05 && tauxTrahison <= 0.15,
                "Le taux de trahison devrait être proche de 10% mais était de " + (tauxTrahison * 100) + "%");
    }

    @Test
    void testRepentanceApresTrahison() {
        // Force une situation où l'adversaire trahit
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        // Le prochain coup devrait être une coopération (repentance)
        assertEquals("c", strategie.prochainCoup(),
                "Devrait coopérer après une trahison de l'adversaire (repentance)");
    }

    @Test
    void testFinRepentance() {
        // Vérifie que la repentance ne dure qu'un tour
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");

        // Premier coup de repentance
        strategie.prochainCoup();

        // Les coups suivants devraient suivre le pattern normal (possibilité de trahir)
        Set<String> coups = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            coups.add(strategie.prochainCoup());
        }

        assertTrue(coups.contains("t"),
                "Après la repentance, la stratégie devrait pouvoir trahir à nouveau");
    }

    @Test
    void testDepassementHistorique() {
        // Remplit l'historique jusqu'à sa limite
        for (int i = 0; i < TAILLE_HISTORIQUE; i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire("c");
        }

        // Vérifie qu'une exception est levée lors du dépassement
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

    @Test
    void testReactionApresTrahisonSansRepentance() {
        // Vérifie le comportement quand on force une trahison sans repentance
        String premierCoup = strategie.prochainCoup();
        // Simule une série de coopérations pour établir un pattern
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.prochainCoup(); // Coup de repentance

        // Les coups suivants devraient suivre le pattern normal
        boolean trahisonTrouvee = false;
        for (int i = 0; i < 100 && !trahisonTrouvee; i++) {
            if (strategie.prochainCoup().equals("t")) {
                trahisonTrouvee = true;
            }
        }

        assertTrue(trahisonTrouvee,
                "La stratégie devrait pouvoir trahir après la période de repentance");
    }
}
