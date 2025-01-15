package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.ToujoursTrahirStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToujoursTrahirStrategieTest {
    private ToujoursTrahirStrategie strategie;
    private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new ToujoursTrahirStrategie();
        historique = new ArrayList<>();
    }

    @Test
    void testHistoriqueVide() {
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Doit TRAHIR même avec un historique vide");
    }

    @Test
    void testApresCooperation() {
        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Doit TRAHIR même après une coopération de l'adversaire");
    }

    @Test
    void testApresTrahison() {
        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Doit TRAHIR même après une trahison de l'adversaire");
    }

    @Test
    void testSequenceMultipleCoups() {
        // Test avec une séquence variée de coups
        for (int i = 0; i < 5; i++) {
            assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
                "Doit toujours TRAHIR quel que soit le nombre de coups joués");
            historique.add(i % 2 == 0 ? CoupEnum.COOPERER : CoupEnum.TRAHIR);
        }
    }
}