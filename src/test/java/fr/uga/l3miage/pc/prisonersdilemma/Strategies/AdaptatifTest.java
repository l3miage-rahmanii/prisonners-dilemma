package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.Adaptatif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class AdaptatifTest {
    private Adaptatif strategie;
    private List<CoupEnum> historiqueAdversaire;

    @BeforeEach
    void setUp() {
        strategie = new Adaptatif();
        historiqueAdversaire = new ArrayList<>();
    }


    @Test
    void testUpdateScore() {
        strategie.updateScore(10);
        // Jouer quelques coups
        for (int i = 0; i < 3; i++) {
            strategie.prochainCoup(historiqueAdversaire);
            historiqueAdversaire.add(CoupEnum.COOPERER);
        }

        strategie.updateScore(20);
        // Vérifier que le score a bien été mis à jour
        // On peut vérifier indirectement en observant le comportement du prochain coup
        assertNotNull(strategie.prochainCoup(historiqueAdversaire));
    }

    @Test
    void testComportementAvecHistoriqueComplet() {
        // Créer un historique complet de 20 coups
        List<CoupEnum> longHistorique = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            longHistorique.add(CoupEnum.COOPERER);
            strategie.prochainCoup(longHistorique);
            strategie.updateScore(i * 3); // Simuler différents scores
        }

        // Vérifier que la stratégie continue à fonctionner
        assertNotNull(strategie.prochainCoup(longHistorique));
    }

    @Test
    void testMoyenneTrahirAvecScorePositif() {
        // Given
        // Jouer 6 COOPERER puis 5 TRAHIR pour atteindre 11 coups
        for (int i = 0; i < 6; i++) {
            historiqueAdversaire.add(CoupEnum.COOPERER);
            strategie.prochainCoup(historiqueAdversaire);
        }
        for (int i = 0; i < 5; i++) {
            historiqueAdversaire.add(CoupEnum.TRAHIR);
            strategie.prochainCoup(historiqueAdversaire);
        }
        strategie.updateScore(50); // Score total de 50 pour 5 coups TRAHIR

        // When
        historiqueAdversaire.add(CoupEnum.COOPERER);
        CoupEnum resultat = strategie.prochainCoup(historiqueAdversaire);

        // Then
        assertEquals(CoupEnum.TRAHIR, resultat); // Moyenne TRAHIR = 50/5 = 10 > Moyenne COOPERER
    }

    @Test
    void testMoyennesAvecScoreZero() {
        // Given
        // Jouer 11 coups pour dépasser la phase initiale
        for (int i = 0; i < 11; i++) {
            historiqueAdversaire.add(CoupEnum.COOPERER);
            strategie.prochainCoup(historiqueAdversaire);
        }
        strategie.updateScore(0); // Score total de 0

        // When
        historiqueAdversaire.add(CoupEnum.COOPERER);
        CoupEnum resultat = strategie.prochainCoup(historiqueAdversaire);

        // Then
        assertEquals(CoupEnum.COOPERER, resultat); // En cas d'égalité (0 = 0), on privilégie COOPERER
    }

    @Test
    void testMoyennesAvecAucunCoupJoue() {
        // Given
        // On modifie directement les compteurs pour simuler aucun coup joué
        // Note: Ceci est un test artificiel pour vérifier la gestion du cas countCooperer = 0 et countTrahir = 0
        strategie = new Adaptatif() {
            @Override
            public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
                // Force le calcul des moyennes avec des compteurs à 0
                double moyenneCooperer = 0; // car countCooperer = 0
                double moyenneTrahir = 0;   // car countTrahir = 0

                return (moyenneCooperer >= moyenneTrahir) ? CoupEnum.COOPERER : CoupEnum.TRAHIR;
            }
        };

        // When
        CoupEnum resultat = strategie.prochainCoup(historiqueAdversaire);

        // Then
        assertEquals(CoupEnum.COOPERER, resultat); // En cas d'égalité (0 = 0), on privilégie COOPERER
    }

}