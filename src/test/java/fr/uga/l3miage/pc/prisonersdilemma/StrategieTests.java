package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.strategies.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StrategieTests {

    private String[] historique;

    @BeforeEach
    void setup() {
        historique = new String[100]; // Initialiser un historique de taille suffisante
    }

    // Test pour la strategie Donnant Donnant
    @Test
    void testDonnantDonnantStrategie() {
        DonnantDonnantStrategie strategie = new DonnantDonnantStrategie(historique);
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());

        historique[1] = "t";
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup());
    }

    /*
    // Test pour la strategie Donnant Donnant Aleatoire
    @Test
    void testDonnantDonnantAleatoire() {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextBoolean()).thenReturn(false); // Forcer le comportement de Donnant-Donnant standard
        DonnantDonnantAleatoire strategie = new DonnantDonnantAleatoire(historique);

        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup()); // Devrait maintenant passer
    }

     */

    // Test pour la strategie Donnant pour Deux Donnants
    @Test
    void testDonnantPourDeuxDonnants() {
        DonnantPourDeuxDonnants strategie = new DonnantPourDeuxDonnants(historique);
        historique[0] = "c";
        historique[1] = "t";
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("c", strategie.prochainCoup());

        historique[2] = "t";
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup());
    }

    /*
    // Test pour la strategie Donnant pour Deux Donnants et Aleatoire
    @Test
    void testDonnantPourDeuxDonnantsAleatoire() {
        DonnantPourDeuxDonnantsAleatoire strategie = new DonnantPourDeuxDonnantsAleatoire(historique);
        historique[0] = "c";
        historique[1] = "t";
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("c", strategie.prochainCoup());
    }

     */

    // Test pour la strategie Toujours Trahir
    @Test
    void testToujoursTrahir() {
        ToujoursTrahirStrategie strategie = new ToujoursTrahirStrategie();
        assertEquals("t", strategie.prochainCoup());
    }

    // Test pour la strategie Toujours Cooperer
    @Test
    void testToujoursCooperer() {
        ToujoursCooperer strategie = new ToujoursCooperer();
        assertEquals("c", strategie.prochainCoup());
    }

    // Test pour la strategie Rancunier
    @Test
    void testRancunierStrategie() {
        RancunierStrategie strategie = new RancunierStrategie(historique);
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());

        historique[1] = "t";
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup());
    }

    // Test pour la strategie Pavlov
    @Test
    void testPavlovStrategie() {
        PavlovStrategie strategie = new PavlovStrategie(historique);

        // 1er coup : score de 3, donc repeter la cooperation
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c", 3);
        assertEquals("c", strategie.prochainCoup());

        // 2ème coup : score de 1, donc changer de coup à la trahison
        historique[1] = "c";
        strategie.miseAJourDernierCoupAdversaire("t", 1);
        assertEquals("t", strategie.prochainCoup());

        // 3ème coup : score de 5, donc repeter la trahison
        historique[2] = "t";
        strategie.miseAJourDernierCoupAdversaire("t", 5);
        assertEquals("t", strategie.prochainCoup());

        // 4ème coup : score de 0, donc changer de coup à la cooperation
        historique[3] = "t";
        strategie.miseAJourDernierCoupAdversaire("c", 0);
        assertEquals("c", strategie.prochainCoup());
    }

/*
    // Test pour la strategie Sondeur Naïf
    @Test
    void testSondeurNaif() {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.2); // Forcer une valeur > 0.1 pour éviter le coup aléatoire "t"
        SondeurNaif strategie = new SondeurNaif(historique);

        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup()); // Maintenant, le test devrait réussir
    }



    // Test pour la strategie Sondeur Repentant
    @Test
    void testSondeurRepentant() {
        SondeurRepentant strategie = new SondeurRepentant(historique);
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());
    }
 */

    @Test
    void testToujoursTrahirSimple() {
        ToujoursTrahirStrategie strategie = new ToujoursTrahirStrategie();
        assertEquals("t", strategie.prochainCoup()); // Vérifie que le coup est toujours "t"
    }

    @Test
    void testToujoursCoopererSimple() {
        ToujoursCooperer strategie = new ToujoursCooperer();
        assertEquals("c", strategie.prochainCoup()); // Vérifie que le coup est toujours "c"
    }

    @Test
    void testAleatoireSimple() {
        Aleatoire strategie = new Aleatoire();
        String coup = strategie.prochainCoup();
        assertTrue(coup.equals("c") || coup.equals("t")); // Le coup doit être "c" ou "t"
    }

    @Test
    void testRancunierSimple() {
        RancunierStrategie strategie = new RancunierStrategie(historique);
        historique[0] = "c";
        assertEquals("c", strategie.prochainCoup()); // Devrait coopérer au début
    }

    @Test
    void testPavlovSimple() {
        PavlovStrategie strategie = new PavlovStrategie(historique);
        assertEquals("c", strategie.prochainCoup()); // Vérifie que le premier coup est "c"
    }

    @Test
    void testSondeurRepentantSimple() {
        SondeurRepentant strategie = new SondeurRepentant(historique);
        historique[0] = "c"; // Le premier coup est "c"
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testPacificateurNaifSimple() {
        PacificateurNaif strategie = new PacificateurNaif(historique);
        historique[0] = "c"; // Le premier coup doit être "c"
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testVraiPacificateurSimple() {
        VraiPacificateur strategie = new VraiPacificateur(historique);
        historique[0] = "t"; // Peu importe l'adversaire, le vrai pacificateur coopère toujours
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testDonnantDonnantSoupconneuxSimple() {
        DonnantDonnantSoupconneux strategie = new DonnantDonnantSoupconneux(historique);
        assertEquals("t", strategie.prochainCoup()); // Premier coup est une trahison
    }

    @Test
    void testDonnantDonnantSimple() {
        DonnantDonnantStrategie strategie = new DonnantDonnantStrategie(historique);
        historique[0] = "c"; // Le premier coup est "c"
        assertEquals("c", strategie.prochainCoup());
    }

    @Test
    void testDonnantPourDeuxDonnantsSimple() {
        DonnantPourDeuxDonnants strategie = new DonnantPourDeuxDonnants(historique);
        historique[0] = "c"; // Le premier coup est "c"
        assertEquals("c", strategie.prochainCoup());
    }


    // Test pour la strategie Pacificateur Naïf
    @Test
    void testPacificateurNaif() {
        PacificateurNaif strategie = new PacificateurNaif(historique);
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());
    }

    // Test pour la strategie Vrai Pacificateur
    @Test
    void testVraiPacificateur() {
        VraiPacificateur strategie = new VraiPacificateur(historique);
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());
    }

    // Test pour la strategie Aleatoire
    @Test
    void testAleatoire() {
        Aleatoire strategie = new Aleatoire();
        // Just test that it returns either "c" or "t", without verifying the exact value since it's random
        String coup = strategie.prochainCoup();
        assert(Arrays.asList("c", "t").contains(coup));
    }

    // Test pour la strategie Adaptatif
    @Test
    void testAdaptatif() {
        Adaptatif strategie = new Adaptatif(historique);
        assertEquals("c", strategie.prochainCoup());
    }

    // Test pour la strategie Graduel
    @Test
    void testGraduel() {
        Graduel strategie = new Graduel();
        assertEquals("c", strategie.prochainCoup());
    }

    // Test pour la strategie Donnant Donnant Soupconneux
    @Test
    void testDonnantDonnantSoupconneux() {
        DonnantDonnantSoupconneux strategie = new DonnantDonnantSoupconneux(historique);
        assertEquals("t", strategie.prochainCoup()); // Premier coup est "t"
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup()); // Coopère après
    }

    // Test pour la strategie Rancunier Doux
    @Test
    void testRancunierDoux() {
        RancunierDoux strategie = new RancunierDoux(historique);
        historique[0] = "c";
        strategie.miseAJourDernierCoupAdversaire("c");
        assertEquals("c", strategie.prochainCoup());

        historique[1] = "t";
        strategie.miseAJourDernierCoupAdversaire("t");
        assertEquals("t", strategie.prochainCoup());
    }
}
