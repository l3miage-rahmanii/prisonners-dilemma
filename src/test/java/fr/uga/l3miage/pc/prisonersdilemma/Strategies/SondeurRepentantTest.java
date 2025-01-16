package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.SondeurRepentant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SondeurRepentantTest {
private SondeurRepentant strategie;
private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new SondeurRepentant();
        historique = new ArrayList<>();
    }

    @Test
    void testPremierCoup() {
        // Le premier coup devrait toujours être COOPERER
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique));
    }

    @Test
    void testRepentirApresTrahison() {
        // On mocke le Random pour forcer une trahison
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextDouble()).thenReturn(0.05); // Valeur < 0.1 pour forcer la trahison

        // On injecte le mock dans la stratégie via réflexion
        try {
            java.lang.reflect.Field randomField = SondeurRepentant.class.getDeclaredField("random");
            randomField.setAccessible(true);
            randomField.set(strategie, mockRandom);
        } catch (Exception e) {
            fail("Erreur lors de l'injection du mock: " + e.getMessage());
        }

        // Premier coup : l'adversaire coopère
        historique.add(CoupEnum.COOPERER);
        // La stratégie devrait trahir (grâce au mock)
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique));

        // L'adversaire trahit en réponse
        historique.add(CoupEnum.TRAHIR);
        // La stratégie devrait se repentir et coopérer
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique));
    }

    @RepeatedTest(100)
    void testProbabiliteTrahison() {
        // Test statistique de la probabilité de trahison
        historique.add(CoupEnum.COOPERER);
        int trahisons = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.TRAHIR) {
                trahisons++;
            }
        }

        // On vérifie que le taux de trahison est proche de 10% avec une marge d'erreur
        double tauxTrahison = (double) trahisons / iterations;
        assertTrue(tauxTrahison > 0.05 && tauxTrahison < 0.15,
                "Le taux de trahison (" + tauxTrahison + ") devrait être proche de 0.1");
    }

    @Test
    void testSequenceComplexe() {
        // Test d'une séquence complexe de coups
        List<CoupEnum> sequence = List.of(
                CoupEnum.COOPERER,
                CoupEnum.TRAHIR,
                CoupEnum.COOPERER,
                CoupEnum.COOPERER,
                CoupEnum.TRAHIR
        );

        for (CoupEnum coup : sequence) {
            historique.add(coup);
            CoupEnum prochainCoup = strategie.prochainCoup(historique);
            assertNotNull(prochainCoup, "Le prochain coup ne devrait pas être null");
        }
    }
}