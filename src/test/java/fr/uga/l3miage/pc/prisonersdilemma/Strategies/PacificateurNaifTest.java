package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Random;

import fr.uga.l3miage.pc.strategies.PacificateurNaif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class PacificateurNaifTest {
    /*
    private static final int HISTORY_SIZE = 100;
    private String[] historique;
    private PacificateurNaif strategie;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        historique = new String[HISTORY_SIZE];
        mockRandom = mock(Random.class);

        try {
            strategie = new PacificateurNaif(historique);
            java.lang.reflect.Field randomField = strategie.getClass().getDeclaredField("random");
            randomField.setAccessible(true);
            randomField.set(strategie, mockRandom);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test premier coup sans historique")
    void testPremierCoup() {
        // Premier coup devrait être "c" quand nextDouble >= 0.1
        when(mockRandom.nextDouble()).thenReturn(0.5);
        assertEquals("c", strategie.prochainCoup(),
                "Le premier coup devrait être 'c' sans historique");
    }

    @Test
    @DisplayName("Test comportement pacificateur (10% de chance)")
    void testComportementPacificateur() {
        // Simuler l'adversaire qui trahit
        strategie.miseAJourDernierCoupAdversaire("t");

        // Simuler le comportement pacificateur (nextDouble < 0.1)
        when(mockRandom.nextDouble()).thenReturn(0.05);

        assertEquals("c", strategie.prochainCoup(),
                "Devrait coopérer quand le comportement pacificateur est déclenché");
    }

    @Test
    @DisplayName("Test réponse à la trahison (90% de chance)")
    void testReponseATrahison() {
        // Simuler l'adversaire qui trahit
        strategie.miseAJourDernierCoupAdversaire("t");

        // Simuler le comportement normal (nextDouble >= 0.1)
        when(mockRandom.nextDouble()).thenReturn(0.5);

        assertEquals("t", strategie.prochainCoup(),
                "Devrait trahir en réponse à une trahison (hors comportement pacificateur)");
    }

    @Test
    @DisplayName("Test réponse à la coopération")
    void testReponseACooperation() {
        // Simuler l'adversaire qui coopère
        strategie.miseAJourDernierCoupAdversaire("c");

        // Simuler le comportement normal (nextDouble >= 0.1)
        when(mockRandom.nextDouble()).thenReturn(0.5);

        assertEquals("c", strategie.prochainCoup(),
                "Devrait coopérer en réponse à une coopération");
    }

    @Test
    @DisplayName("Test séquence de coups")
    void testSequenceDeCoups() {
        // Séquence de comportements: normal, pacificateur, normal
        when(mockRandom.nextDouble())
                .thenReturn(0.5)   // Normal - suivre l'historique
                .thenReturn(0.05)  // Pacificateur - coopérer
                .thenReturn(0.5);  // Normal - suivre l'historique

        // Premier coup (sans historique)
        assertEquals("c", strategie.prochainCoup());

        // Adversaire trahit
        strategie.miseAJourDernierCoupAdversaire("t");

        // Deuxième coup (pacificateur)
        assertEquals("c", strategie.prochainCoup());

        // Adversaire trahit encore
        strategie.miseAJourDernierCoupAdversaire("t");

        // Troisième coup (normal)
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test limites du seuil aléatoire")
    void testLimitesSeuilAleatoire() {
        strategie.miseAJourDernierCoupAdversaire("t");

        // Test exactement à 0.1 (devrait suivre le comportement normal)
        when(mockRandom.nextDouble()).thenReturn(0.1);
        assertEquals("t", strategie.prochainCoup());

        // Test juste en-dessous de 0.1 (devrait être pacificateur)
        when(mockRandom.nextDouble()).thenReturn(0.099);
        assertEquals("c", strategie.prochainCoup());

        // Test juste au-dessus de 0.1 (devrait suivre le comportement normal)
        when(mockRandom.nextDouble()).thenReturn(0.101);
        assertEquals("t", strategie.prochainCoup());
    }

    @Test
    @DisplayName("Test mise à jour de l'historique")
    void testMiseAJourHistorique() {
        String[] sequence = {"c", "t", "c", "t", "c"};

        for (int i = 0; i < sequence.length; i++) {
            strategie.miseAJourDernierCoupAdversaire(sequence[i]);
            assertEquals(sequence[i], historique[i],
                    "L'historique devrait être correctement mis à jour");
        }
    }

    @Test
    @DisplayName("Test comportement avec historique plein")
    void testHistoriquePlein() {
        // Remplir l'historique
        for (int i = 0; i < HISTORY_SIZE; i++) {
            strategie.miseAJourDernierCoupAdversaire("c");
        }

        // Vérifier que la tentative d'ajout suivante lève une exception
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> strategie.miseAJourDernierCoupAdversaire("c"),
                "Devrait lever une exception quand l'historique est plein");
    }

     */
}
