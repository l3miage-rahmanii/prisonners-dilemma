package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.RancunierStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RancunierStrategieTest {
    private RancunierStrategie strategie;
    private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new RancunierStrategie();
        historique = new ArrayList<>();
        historique.add(CoupEnum.COOPERER);  // Initialisation pour éviter IndexOutOfBoundsException
    }

    @Test
    void testPremiereCooperation() {
        // Vérifie que la stratégie commence par COOPERER si l'adversaire coopère
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Devrait COOPERER au début si l'adversaire coopère");
    }

    @Test
    void testCooperationContinue() {
        // Vérifie que la coopération continue tant que l'adversaire coopère
        historique.add(CoupEnum.COOPERER);
        historique.add(CoupEnum.COOPERER);
        historique.add(CoupEnum.COOPERER);

        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Devrait continuer à COOPERER tant que l'adversaire coopère");
    }

    @Test
    void testReactionTrahison() {
        // Vérifie que la stratégie passe à TRAHIR après une trahison
        historique.clear();
        historique.add(CoupEnum.TRAHIR);

        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait TRAHIR après une trahison de l'adversaire");
    }

    @Test
    void testRancunePermanente() {
        // Vérifie que la stratégie continue de TRAHIR même si l'adversaire revient à COOPERER
        historique.clear();
        historique.add(CoupEnum.TRAHIR);  // Première trahison
        strategie.prochainCoup(historique);  // Pour marquer la trahison

        historique.clear();
        historique.add(CoupEnum.COOPERER);  // L'adversaire revient à COOPERER

        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait continuer à TRAHIR même après un retour à la coopération");

        // Vérifie sur plusieurs coups
        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait maintenir TRAHIR sur le long terme");
    }

    @Test
    void testSequenceComplexe() {
        // Test d'une séquence complexe de coups
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Premier coup devrait être COOPERER");

        historique.clear();
        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Devrait continuer à COOPERER");

        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait passer à TRAHIR après trahison");

        historique.clear();
        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait maintenir TRAHIR malgré la coopération");
    }

    @Test
    void testEtatInitial() {
        // Vérifie que l'état initial est correct
        assertFalse(strategie.aTrahi,
            "La variable aTrahi devrait être false au début");
    }

    @Test
    void testChangementEtat() {
        // Vérifie que l'état change correctement après une trahison
        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        strategie.prochainCoup(historique);

        assertTrue(strategie.aTrahi,
            "La variable aTrahi devrait être true après une trahison");
    }
}