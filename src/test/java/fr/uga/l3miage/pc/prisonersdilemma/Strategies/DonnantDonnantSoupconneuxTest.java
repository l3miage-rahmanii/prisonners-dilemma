package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import fr.uga.l3miage.pc.strategies.DonnantDonnantSoupconneux;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DonnantDonnantSoupconneuxTest {

    private DonnantDonnantSoupconneux strategie;

    @BeforeEach
    void setUp() {
        strategie = new DonnantDonnantSoupconneux();
    }

    @Test
    void prochainCoup_shouldBetrayOnFirstMove() {
        // Arrange
        List<CoupEnum> historique = new ArrayList<>();

        // Act
        CoupEnum premierCoup = strategie.prochainCoup(historique);

        // Assert
        assertEquals(CoupEnum.TRAHIR, premierCoup,
                "Le premier coup devrait être TRAHIR");
    }

    @Test
    void prochainCoup_shouldCopyLastOpponentMove() {
        // Arrange
        List<CoupEnum> historique = new ArrayList<>();

        // First move (should be TRAHIR)
        strategie.prochainCoup(historique);

        // Test copying COOPERER
        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
                "Devrait copier le dernier coup COOPERER de l'adversaire");

        // Test copying TRAHIR
        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
                "Devrait copier le dernier coup TRAHIR de l'adversaire");
    }

    @Test
    void prochainCoup_shouldHandleLongHistory() {
        // Arrange
        List<CoupEnum> historique = new ArrayList<>();

        // First move
        strategie.prochainCoup(historique);

        // Add multiple moves
        historique.addAll(List.of(
                CoupEnum.COOPERER,
                CoupEnum.TRAHIR,
                CoupEnum.COOPERER,
                CoupEnum.COOPERER,
                CoupEnum.TRAHIR
        ));

        // Act
        CoupEnum coup = strategie.prochainCoup(historique);

        // Assert
        assertEquals(CoupEnum.TRAHIR, coup,
                "Devrait copier le dernier coup même avec un long historique");
    }

    @Test
    void prochainCoup_shouldMaintainBehaviorAcrossMultipleGames() {
        // Arrange
        List<CoupEnum> premierJeu = new ArrayList<>();
        List<CoupEnum> deuxiemeJeu = new ArrayList<>();

        // Act & Assert - Premier jeu
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(premierJeu),
                "Premier coup du premier jeu devrait être TRAHIR");

        premierJeu.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(premierJeu),
                "Deuxième coup du premier jeu devrait copier COOPERER");

        // Act & Assert - Deuxième jeu avec nouvelle instance
        strategie = new DonnantDonnantSoupconneux();
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(deuxiemeJeu),
                "Premier coup du deuxième jeu devrait être TRAHIR");
    }

    @Test
    void prochainCoup_shouldThrowExceptionForEmptyHistoryAfterFirstMove() {
        // Arrange
        List<CoupEnum> historique = new ArrayList<>();

        // First move
        strategie.prochainCoup(historique);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class,
                () -> strategie.prochainCoup(historique),
                "Devrait lever une exception si l'historique est vide après le premier coup");
    }

    @Test
    void prochainCoup_shouldHandleAlternatingMoves() {
        // Arrange
        List<CoupEnum> historique = new ArrayList<>();

        // First move
        strategie.prochainCoup(historique);

        // Test alternating pattern
        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
                "Premier coup après initialisation devrait être COOPERER");

        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
                "Deuxième coup devrait être TRAHIR");

        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
                "Troisième coup devrait être COOPERER");
    }
}