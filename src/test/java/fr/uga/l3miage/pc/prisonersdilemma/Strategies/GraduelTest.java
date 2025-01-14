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
        // Le premier coup devrait être une coopération
        assertEquals("c", strategie.prochainCoup(),
                "Le premier coup devrait être une coopération ('c')");
    }

    @Test
    void testCooperationInitiale() {
        // Test de plusieurs coups sans trahison
        for (int i = 0; i < 5; i++) {
            assertEquals("c", strategie.prochainCoup(),
                    "Devrait toujours coopérer sans trahison de l'adversaire");
            strategie.miseAJourDernierCoupAdversaire("c");
        }
    }

    @Test
    void testPremiereRepresaille() {
        // L'adversaire trahit une fois
        strategie.prochainCoup(); // Coup initial
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie qu'il y a une seule trahison en représailles
        assertEquals("t", strategie.prochainCoup(),
                "Devrait trahir une fois après la première trahison");
        assertEquals("c", strategie.prochainCoup(),
                "Devrait revenir à la coopération après une représaille");
    }

    @Test
    void testDeuxiemeRepresaille() {
        // Première séquence de trahison/représaille
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.prochainCoup(); // Première trahison en représailles
        strategie.prochainCoup(); // Retour à la coopération

        // Deuxième trahison de l'adversaire
        strategie.miseAJourDernierCoupAdversaire("t");

        // Vérifie qu'il y a deux trahisons en représailles
        assertEquals("t", strategie.prochainCoup(), "Première trahison des représailles");
        assertEquals("t", strategie.prochainCoup(), "Deuxième trahison des représailles");
        assertEquals("c", strategie.prochainCoup(), "Retour à la coopération");
    }

    @Test
    void testSequenceComplexe() {
        String[] sequenceAdversaire = {"c", "t", "c", "t", "c", "c"};
        String[] reponseAttendue = {
                "c", // Coopération initiale
                "c", // Avant première représaille
                "t", // Première représaille
                "c", // Retour à coopération
                "t", // Première représaille de la deuxième séquence
                "t"  // Deuxième représaille de la deuxième séquence
        };

        for (int i = 0; i < sequenceAdversaire.length; i++) {
            String coupJoue = strategie.prochainCoup();
            assertEquals(reponseAttendue[i], coupJoue,
                    "Coup incorrect à l'itération " + i);
            strategie.miseAJourDernierCoupAdversaire(sequenceAdversaire[i]);
        }
    }

    @Test
    void testRepresaillesMultiples() {
        // Simule trois trahisons de l'adversaire
        for (int i = 0; i < 3; i++) {
            strategie.prochainCoup();
            strategie.miseAJourDernierCoupAdversaire("t");
        }

        // Vérifie que le nombre de représailles est de 3
        for (int i = 0; i < 3; i++) {
            assertEquals("t", strategie.prochainCoup(),
                    "Devrait trahir 3 fois en représailles");
        }
        assertEquals("c", strategie.prochainCoup(),
                "Devrait revenir à la coopération après les représailles");
    }

    @Test
    void testRetourCooperation() {
        // Vérifie le retour à la coopération après différentes séquences de représailles
        strategie.prochainCoup();
        strategie.miseAJourDernierCoupAdversaire("t");
        strategie.prochainCoup(); // Une trahison en représailles

        assertEquals("c", strategie.prochainCoup(),
                "Devrait revenir à la coopération après les représailles");

        // Maintient la coopération
        for (int i = 0; i < 3; i++) {
            assertEquals("c", strategie.prochainCoup(),
                    "Devrait maintenir la coopération");
            strategie.miseAJourDernierCoupAdversaire("c");
        }
    }
}
