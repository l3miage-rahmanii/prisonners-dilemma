package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.DonnantDonnantAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonnantDonnantAleatoireTest {

    private DonnantDonnantAleatoire strategie;
    private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new DonnantDonnantAleatoire();
        historique = new ArrayList<>();
        historique.add(CoupEnum.COOPERER);
    }

    @Test
    void testComportementAleatoire() {
        // Exécuter plusieurs fois pour vérifier la distribution aléatoire
        int nbCooperer = 0;
        int nbTrahir = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            CoupEnum coup = strategie.prochainCoup(historique);
            if (coup == CoupEnum.COOPERER) {
                nbCooperer++;
            } else {
                nbTrahir++;
            }
        }

        // Vérifier qu'on a bien les deux types de coups
        assertTrue(nbCooperer > 0, "Devrait avoir des coups COOPERER");
        assertTrue(nbTrahir > 0, "Devrait avoir des coups TRAHIR");
    }

    @Test
    void testComportementDonnantDonnant() {
        // Tester le comportement hérité quand l'historique n'est pas vide
        historique.add(CoupEnum.TRAHIR);

        // Exécuter plusieurs fois car le comportement est partiellement aléatoire
        int nbTrahir = 0;
        int iterations = 100;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.TRAHIR) {
                nbTrahir++;
            }
        }

        // Vérifier qu'il y a une proportion significative de TRAHIR en réponse à TRAHIR
        assertTrue(nbTrahir > iterations * 0.3,
            "Devrait avoir une proportion significative de TRAHIR en réponse à TRAHIR");
    }

    @Test
    void testComportementConsecutif() {
        // Tester plusieurs coups consécutifs
        List<CoupEnum> coupsPrecedents = new ArrayList<>();
        coupsPrecedents.add(CoupEnum.COOPERER);
        coupsPrecedents.add(CoupEnum.COOPERER);
        coupsPrecedents.add(CoupEnum.TRAHIR);

        // Vérifier que les coups suivants sont cohérents
        int nbCooperer = 0;
        int iterations = 100;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(coupsPrecedents) == CoupEnum.COOPERER) {
                nbCooperer++;
            }
        }

        // Vérifier qu'il y a une proportion significative de TRAHIR
        assertTrue(nbCooperer < iterations * 0.7,
            "Devrait avoir une proportion significative de TRAHIR après une trahison");
    }
}