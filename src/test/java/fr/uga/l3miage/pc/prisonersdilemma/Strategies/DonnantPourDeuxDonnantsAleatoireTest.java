package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import static org.junit.jupiter.api.Assertions.*;


import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.DonnantPourDeuxDonnantsAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class DonnantPourDeuxDonnantsAleatoireTest {
    private DonnantPourDeuxDonnantsAleatoire strategie;
    private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new DonnantPourDeuxDonnantsAleatoire();
        historique = new ArrayList<>();
        historique.add(CoupEnum.COOPERER);
    }

    @Test
    void testComportementPrincipal() {
        historique.clear();
        historique.add(CoupEnum.TRAHIR);

        int nbTrahir = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.TRAHIR) {
                nbTrahir++;
            }
        }

        // Avec 10% de chance aléatoire et 90% de copier TRAHIR
        // On s'attend à environ 90% + (10% * 0.5) = 95% de TRAHIR
        double pourcentageTrahir = (double) nbTrahir / iterations;
        assertTrue(pourcentageTrahir > 0.80,
                "Devrait avoir une proportion significative de TRAHIR (>80%)");
        assertTrue(pourcentageTrahir < 0.99,
                "Ne devrait pas avoir 100% de TRAHIR à cause du comportement aléatoire");
    }

    @Test
    void testComportementAleatoire() {
        // Sur un grand nombre d'itérations, on devrait voir les deux types de coups
        Set<CoupEnum> coupsObserves = new HashSet<>();
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            coupsObserves.add(strategie.prochainCoup(historique));
        }

        assertEquals(2, coupsObserves.size(),
                "Devrait observer à la fois COOPERER et TRAHIR sur un grand nombre d'itérations");
    }

    @Test
    void testSequenceDeCoups() {
        historique.clear();
        historique.add(CoupEnum.COOPERER);

        int nbCooperer = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.COOPERER) {
                nbCooperer++;
            }
        }

        double pourcentageCooperer = (double) nbCooperer / iterations;
        assertTrue(pourcentageCooperer > 0.80,
                "Devrait avoir une proportion significative de COOPERER (>80%)");
        assertTrue(pourcentageCooperer < 0.99,
                "Ne devrait pas avoir 100% de COOPERER à cause du comportement aléatoire");
    }

    @Test
    void testHistoriqueChangeant() {
        // Test avec différents historiques
        historique.clear();
        historique.add(CoupEnum.COOPERER);
        CoupEnum coupApresCooperer = strategie.prochainCoup(historique);

        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        CoupEnum coupApresTrahir = strategie.prochainCoup(historique);

        // Sur plusieurs itérations, il devrait y avoir des différences
        int nbDifferences = 0;
        int iterations = 100;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(new ArrayList<>(List.of(CoupEnum.COOPERER))) !=
                    strategie.prochainCoup(new ArrayList<>(List.of(CoupEnum.TRAHIR)))) {
                nbDifferences++;
            }
        }

        assertTrue(nbDifferences > 0,
                "Les coups devraient parfois différer selon l'historique");
    }
}