package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.strategies.RancunierStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RancunierStrategieTest {

    private RancunierStrategie strategie;
    private String[] historique;

    @BeforeEach
    void setUp() {
        // Historique de taille 10 pour simuler les coups précédents
        historique = new String[10];
        strategie = new RancunierStrategie(historique);
    }

    @Test
    void testPremierCoup() {
        // Le premier coup doit toujours être "c" (coopérer)
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testResteCooperatifSiPasDeTrahison() {
        // Simuler des coups coopératifs de l'adversaire
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("c");

        // La stratégie doit rester coopérative
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testDevientRancunierApresTrahison() {
        // Simuler une trahison de l'adversaire
        strategie.miseAJourDernierCoupAdversaire("t");

        // La stratégie doit répondre par une trahison
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    void testResteRancunierApresPremiereTrahison() {
        // Simuler une première trahison
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifier que la stratégie reste rancunière pour les coups suivants
        assertEquals("t", strategie.prochainCoup());
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testHistoriqueApresPlusieursTours() {
        // Simuler plusieurs coups adverses
        strategie.miseAJourDernierCoupAdversaire("c");
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.miseAJourDernierCoupAdversaire("c");

        // Vérifier l'état de l'historique
        assertEquals("c", historique[0]);
        assertEquals("t", historique[1]);
        assertEquals("c", historique[2]);
    }
}
