package fr.uga.l3miage.pc.prisonersdilemma.Strategies;


import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.ToujoursCooperer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToujoursCoopererTest {

    private ToujoursCooperer toujoursCooperer;

    @BeforeEach
    void setUp() {
        toujoursCooperer = new ToujoursCooperer();
    }

    @Test
    void testProchainCoupWithEmptyList() {
        // Call the method with an empty list
        List<CoupEnum> coups = List.of();
        CoupEnum result = toujoursCooperer.prochainCoup(coups);

        // Assert that the result is always COOPERER
        assertEquals(CoupEnum.COOPERER, result, "Expected COOPERER regardless of the history");
    }

    @Test
    void testProchainCoupWithSingleElementList() {
        // Call the method with a single element in the list
        List<CoupEnum> coups = List.of(CoupEnum.TRAHIR);
        CoupEnum result = toujoursCooperer.prochainCoup(coups);

        // Assert that the result is always COOPERER
        assertEquals(CoupEnum.COOPERER, result, "Expected COOPERER regardless of the history");
    }

    @Test
    void testProchainCoupWithMultipleElementsList() {
        // Call the method with multiple elements in the list
        List<CoupEnum> coups = List.of(CoupEnum.COOPERER, CoupEnum.TRAHIR, CoupEnum.COOPERER);
        CoupEnum result = toujoursCooperer.prochainCoup(coups);

        // Assert that the result is always COOPERER
        assertEquals(CoupEnum.COOPERER, result, "Expected COOPERER regardless of the history");
    }

    @Test
    void testProchainCoupWithNullList() {
        // Call the method with a null list
        CoupEnum result = toujoursCooperer.prochainCoup(null);

        // Assert that the result is always COOPERER
        assertEquals(CoupEnum.COOPERER, result, "Expected COOPERER even when history is null");
    }
}