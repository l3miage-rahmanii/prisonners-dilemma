package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.PavlovStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PavlovStrategieTest {
    private PavlovStrategie strategie;
    private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new PavlovStrategie();
        historique = new ArrayList<>();
        // Initialiser l'historique avec un coup pour éviter IndexOutOfBoundsException
        historique.add(CoupEnum.COOPERER);
    }

    @Test
    void testComportementAvecScoreSatisfaisant5() {
        // Avec un score de 5, devrait copier le dernier coup
        strategie.setScorePrecedent(5);
        historique.clear();
        historique.add(CoupEnum.COOPERER);

        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Devrait copier COOPERER quand le score précédent est 5");

        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait copier TRAHIR quand le score précédent est 5");
    }

    @Test
    void testComportementAvecScoreSatisfaisant3() {
        // Avec un score de 3, devrait copier le dernier coup
        strategie.setScorePrecedent(3);
        historique.clear();
        historique.add(CoupEnum.COOPERER);

        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Devrait copier COOPERER quand le score précédent est 3");

        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait copier TRAHIR quand le score précédent est 3");
    }

    @Test
    void testComportementAvecScoreInsatisfaisant() {
        // Avec un score différent de 3 et 5, devrait faire l'opposé du dernier coup
        strategie.setScorePrecedent(1);
        historique.clear();
        historique.add(CoupEnum.COOPERER);

        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait TRAHIR quand l'adversaire a COOPERE et le score est insatisfaisant");

        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Devrait COOPERER quand l'adversaire a TRAHI et le score est insatisfaisant");
    }

    @Test
    void testChangementDeScore() {
        // Vérifier que le changement de score modifie le comportement
        historique.clear();
        historique.add(CoupEnum.COOPERER);

        strategie.setScorePrecedent(5);
        CoupEnum coupAvecBonScore = strategie.prochainCoup(historique);

        strategie.setScorePrecedent(1);
        CoupEnum coupAvecMauvaisScore = strategie.prochainCoup(historique);

        assertNotEquals(coupAvecBonScore, coupAvecMauvaisScore,
            "Le comportement devrait changer en fonction du score");
    }

    @Test
    void testComportementScoreZero() {
        strategie.setScorePrecedent(0);
        historique.clear();
        historique.add(CoupEnum.COOPERER);

        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Devrait TRAHIR quand l'adversaire a COOPERE et le score est 0");
    }

    @Test
    void testSequenceDeCoups() {
        strategie.setScorePrecedent(5);
        historique.clear();

        // Test d'une séquence de coups avec un bon score
        historique.add(CoupEnum.COOPERER);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Premier coup devrait être COOPERER avec score 5");

        historique.add(CoupEnum.TRAHIR);
        assertEquals(CoupEnum.TRAHIR, strategie.prochainCoup(historique),
            "Deuxième coup devrait être TRAHIR avec score 5");

        // Changement de score au milieu de la séquence
        strategie.setScorePrecedent(1);
        assertEquals(CoupEnum.COOPERER, strategie.prochainCoup(historique),
            "Troisième coup devrait être COOPERER avec score 1");
    }
}