package fr.uga.l3miage.pc.prisonersdilemma.Strategies;


import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.Graduel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraduelTest {
    private Graduel strategie;
    private List<CoupEnum> historiqueAdversaire;

    @BeforeEach
    void setUp() {
        strategie = new Graduel();
        historiqueAdversaire = new ArrayList<>();
    }

    @Test
    void testCooperationInitiale() {
        // Given an initial betrayal
        historiqueAdversaire.add(CoupEnum.TRAHIR);

        // When we get next move
        CoupEnum result = strategie.prochainCoup(historiqueAdversaire);

        // Then it should betray once
        assertEquals(CoupEnum.TRAHIR, result);

        // Add a cooperation move to history
        historiqueAdversaire.add(CoupEnum.COOPERER);

        // And then cooperate twice
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result);

        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result);

        // And then continue cooperating
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result);
    }

    @Test
    void testMultipleBetrayals() {
        // Given three consecutive betrayals
        historiqueAdversaire.add(CoupEnum.TRAHIR);
        CoupEnum result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result);

        historiqueAdversaire.add(CoupEnum.TRAHIR);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result);

        historiqueAdversaire.add(CoupEnum.TRAHIR);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result);

        // Then it should betray two more times and then cooperate
        // First betrayal
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result, "Should betray on first cooperation");

        // Second betrayal
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result, "Should betray on second cooperation");

        // Should start cooperating
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result, "Should start cooperating after represailles");

        // Followed by two cooperations
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result, "Should cooperate first time after betrayals");

        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result, "Should cooperate second time after betrayals");

        // And then return to normal cooperation
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result, "Should continue cooperating");
    }

    @Test
    void testMixedBehavior() {
        // Test alternating betrayal and cooperation
        historiqueAdversaire.add(CoupEnum.TRAHIR);
        CoupEnum result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result);

        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result);

        historiqueAdversaire.add(CoupEnum.TRAHIR);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result);

        // Should first complete the remaining betrayal
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, result);

        // Then follow with two cooperations
        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result);

        historiqueAdversaire.add(CoupEnum.COOPERER);
        result = strategie.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, result);
    }

    @Test
    void testLongTermCooperation() {
        // Test that after initial betrayal and response cycle, it maintains cooperation
        historiqueAdversaire.add(CoupEnum.TRAHIR);
        strategie.prochainCoup(historiqueAdversaire); // Initial betrayal response

        // Add several cooperative moves
        for (int i = 0; i < 5; i++) {
            historiqueAdversaire.add(CoupEnum.COOPERER);
            CoupEnum result = strategie.prochainCoup(historiqueAdversaire);
            if (i < 2) {
                assertEquals(CoupEnum.COOPERER, result,
                        "Should cooperate during mandatory cooperation phase");
            } else {
                assertEquals(CoupEnum.COOPERER, result,
                        "Should maintain cooperation after mandatory phase");
            }
        }
    }
}