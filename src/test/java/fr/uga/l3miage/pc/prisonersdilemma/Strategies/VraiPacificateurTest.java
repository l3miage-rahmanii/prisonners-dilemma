package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.VraiPacificateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class VraiPacificateurTest {
    private VraiPacificateur vraiPacificateur;
    private Random mockRandom;

    @BeforeEach
    void setUp() throws Exception {
        // Create a mock Random instance
        mockRandom = Mockito.mock(Random.class);

        // Instantiate VraiPacificateur
        vraiPacificateur = new VraiPacificateur();

        // Use reflection to inject the mocked Random into the private 'random' field
        Field randomField = VraiPacificateur.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(vraiPacificateur, mockRandom);
    }
    @Test
    void testProchainCoup_LessThanTwoMoves() {
        List<CoupEnum> historiqueAdversaire = List.of(CoupEnum.COOPERER);
        CoupEnum resultat = vraiPacificateur.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, resultat, "Should cooperate when history has less than two moves.");
    }

    @Test
    void testProchainCoup_NoConsecutiveTrahir() {
        List<CoupEnum> historiqueAdversaire = List.of(CoupEnum.COOPERER, CoupEnum.COOPERER);
        CoupEnum resultat = vraiPacificateur.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, resultat, "Should cooperate when the last two moves are not both TRAHIR.");
    }

    @Test
    void testProchainCoup_TwoConsecutiveTrahir_RandomCooperate() {
        List<CoupEnum> historiqueAdversaire = List.of(CoupEnum.COOPERER, CoupEnum.TRAHIR, CoupEnum.TRAHIR);

        // Mock the random chance to ensure cooperation (20% chance)
        when(mockRandom.nextDouble()).thenReturn(0.1);

        CoupEnum resultat = vraiPacificateur.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, resultat, "Should cooperate 20% of the time when the last two moves are TRAHIR.");
    }

    @Test
    void testProchainCoup_TwoConsecutiveTrahir_RandomTrahir() {
        List<CoupEnum> historiqueAdversaire = List.of(CoupEnum.COOPERER, CoupEnum.TRAHIR, CoupEnum.TRAHIR);

        // Mock the random chance to ensure betrayal (80% chance)
        when(mockRandom.nextDouble()).thenReturn(0.5);

        CoupEnum resultat = vraiPacificateur.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.TRAHIR, resultat, "Should betray 80% of the time when the last two moves are TRAHIR.");
    }

    @Test
    void testProchainCoup_AllCooperate() {
        List<CoupEnum> historiqueAdversaire = List.of(CoupEnum.COOPERER, CoupEnum.COOPERER, CoupEnum.COOPERER);
        CoupEnum resultat = vraiPacificateur.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, resultat, "Should always cooperate if the adversary has always cooperated.");
    }

    @Test
    void testProchainCoup_MixedButNoConsecutiveTrahir() {
        List<CoupEnum> historiqueAdversaire = List.of(CoupEnum.COOPERER, CoupEnum.TRAHIR, CoupEnum.COOPERER, CoupEnum.TRAHIR);
        CoupEnum resultat = vraiPacificateur.prochainCoup(historiqueAdversaire);
        assertEquals(CoupEnum.COOPERER, resultat, "Should cooperate if there are no two consecutive TRAHIR moves.");
    }

}