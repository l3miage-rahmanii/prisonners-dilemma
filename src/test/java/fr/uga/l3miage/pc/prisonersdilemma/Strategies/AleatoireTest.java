package fr.uga.l3miage.pc.prisonersdilemma.Strategies;
import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.Aleatoire;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class AleatoireTest {

    @Test
    void prochainCoup_shouldReturnValidMove() {
        // Arrange
        Aleatoire strategie = new Aleatoire();
        List<CoupEnum> historique = new ArrayList<>();

        // Act
        CoupEnum coup = strategie.prochainCoup(historique);

        // Assert
        assertTrue(coup == CoupEnum.COOPERER || coup == CoupEnum.TRAHIR,
                "Le coup doit être soit COOPERER soit TRAHIR");
    }

    @Test
    void prochainCoup_shouldProduceBothMoves() {
        // Arrange
        Aleatoire strategie = new Aleatoire();
        List<CoupEnum> historique = new ArrayList<>();
        Set<CoupEnum> coups = new HashSet<>();
        int nombreEssais = 100;

        // Act
        for (int i = 0; i < nombreEssais; i++) {
            coups.add(strategie.prochainCoup(historique));
        }

        // Assert
        assertEquals(2, coups.size(),
                "La stratégie doit produire les deux types de coups sur un grand nombre d'essais");
        assertTrue(coups.contains(CoupEnum.COOPERER),
                "La stratégie doit produire au moins un coup COOPERER");
        assertTrue(coups.contains(CoupEnum.TRAHIR),
                "La stratégie doit produire au moins un coup TRAHIR");
    }

    @Test
    void prochainCoup_shouldWorkWithDifferentHistories() {
        // Arrange
        Aleatoire strategie = new Aleatoire();
        List<CoupEnum> historiqueVide = new ArrayList<>();
        List<CoupEnum> historiqueRempli = List.of(
                CoupEnum.COOPERER,
                CoupEnum.TRAHIR,
                CoupEnum.COOPERER
        );

        // Act & Assert
        // Test avec historique vide
        CoupEnum coupHistoriqueVide = strategie.prochainCoup(historiqueVide);
        assertTrue(coupHistoriqueVide == CoupEnum.COOPERER || coupHistoriqueVide == CoupEnum.TRAHIR,
                "Doit fonctionner avec un historique vide");

        // Test avec historique rempli
        CoupEnum coupHistoriqueRempli = strategie.prochainCoup(historiqueRempli);
        assertTrue(coupHistoriqueRempli == CoupEnum.COOPERER || coupHistoriqueRempli == CoupEnum.TRAHIR,
                "Doit fonctionner avec un historique non vide");
    }

    @Test
    void prochainCoup_distributionShouldBeRoughlyEqual() {
        // Arrange
        Aleatoire strategie = new Aleatoire();
        List<CoupEnum> historique = new ArrayList<>();
        int nombreEssais = 1000;
        int cooperer = 0;
        double tolerance = 0.1; // 10% de tolérance

        // Act
        for (int i = 0; i < nombreEssais; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.COOPERER) {
                cooperer++;
            }
        }

        // Assert
        double ratio = (double) cooperer / nombreEssais;
        assertTrue(Math.abs(ratio - 0.5) < tolerance,
                "La distribution des coups doit être approximativement égale (50% ± 10%)");
    }
}
