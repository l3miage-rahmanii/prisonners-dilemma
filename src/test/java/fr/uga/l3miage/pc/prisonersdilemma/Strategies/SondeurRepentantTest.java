package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.SondeurRepentant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SondeurRepentantTest {

    private SondeurRepentant strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        // Initialisation de l'historique pour simuler les coups précédents
        historique = new String[10];
        strategie = new SondeurRepentant(historique);
    }

    @Test
    void testReponseApresTrahison() {
        // Simule la trahison de l'adversaire
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie que le prochain coup sera "c" (repentance) après trahison
        assertEquals("c", strategie.prochainCoup());
    }

   /* @Test
    void testProbabiliteDeTrahison() {
        // Simule plusieurs tours pour tester la probabilité de trahison
        int trahisonCount = 0;

        for (int i = 0; i < 10; i++) {
            // Mise à jour avec "c" pour coopérer et tester la chance de trahir
            strategie.miseAJourDernierCoupAdversaire("c");
            String coup = strategie.prochainCoup();
            if (coup.equals("t")) {
                trahisonCount++;
            }
        }

        // Vérifie que la probabilité de trahison est autour de 10%
        double trahisonRate = trahisonCount / 100.0;
        assertTrue(trahisonRate >= 0.05 && trahisonRate <= 0.15, "La probabilité de trahison doit être autour de 10%");
    } */

    @Test
    void testRepentanceRetourneCoupCoopere() {
        // Simule la trahison de l'adversaire
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie que la stratégie coopère après la trahison (repentance)
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testHistoriqueApresMiseAJour() {
        // Simule plusieurs tours
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("c");

        // Vérifie l'état de l'historique
        assertEquals("c", historique[0]);
        assertEquals("t", historique[1]);
        assertEquals("c", historique[2]);
    }
}
