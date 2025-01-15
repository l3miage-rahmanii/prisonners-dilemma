package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.DonnantDonnantStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DonnantDonnantStrategieTest {
    private DonnantDonnantStrategie strategie;

    @BeforeEach
    void setUp() {
        strategie = new DonnantDonnantStrategie();
    }

    @Test
    @DisplayName("Doit répliquer COOPERER quand l'adversaire a coopéré")
    void prochainCoup_DoitRepliquerCooperation() {
        // Given
        List<CoupEnum> historique = Arrays.asList(CoupEnum.TRAHIR, CoupEnum.COOPERER);

        // When
        CoupEnum resultat = strategie.prochainCoup(historique);

        // Then
        assertEquals(CoupEnum.COOPERER, resultat);
    }

    @Test
    @DisplayName("Doit répliquer TRAHIR quand l'adversaire a trahi")
    void prochainCoup_DoitRepliquerTrahison() {
        // Given
        List<CoupEnum> historique = Arrays.asList(CoupEnum.COOPERER, CoupEnum.TRAHIR);

        // When
        CoupEnum resultat = strategie.prochainCoup(historique);

        // Then
        assertEquals(CoupEnum.TRAHIR, resultat);
    }

    @Test
    @DisplayName("Doit répliquer ABANDONNER quand l'adversaire a abandonné")
    void prochainCoup_DoitRepliquerAbandon() {
        // Given
        List<CoupEnum> historique = Arrays.asList(CoupEnum.COOPERER, CoupEnum.ABANDONNER);

        // When
        CoupEnum resultat = strategie.prochainCoup(historique);

        // Then
        assertEquals(CoupEnum.ABANDONNER, resultat);
    }

    @Test
    @DisplayName("Doit lever une exception quand l'historique est vide")
    void prochainCoup_DoitLeverExceptionHistoriqueVide() {
        // Given
        List<CoupEnum> historique = new ArrayList<>();

        // When & Then
        assertThrows(IndexOutOfBoundsException.class,
                () -> strategie.prochainCoup(historique));
    }
}
