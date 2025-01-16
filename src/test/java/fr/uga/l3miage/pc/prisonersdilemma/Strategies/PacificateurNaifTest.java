package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import static org.junit.jupiter.api.Assertions.*;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.PacificateurNaif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class PacificateurNaifTest {
    private PacificateurNaif strategie;
    private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new PacificateurNaif();
        historique = new ArrayList<>();
        historique.add(CoupEnum.COOPERER);  // Initialisation pour éviter IndexOutOfBoundsException
    }

    @Test
    void testComportementApresCooperation() {
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
        // Devrait être environ 90% (copie) + 10% (apaisement) = 100% de COOPERER
        assertTrue(pourcentageCooperer > 0.90,
                "Devrait avoir une très forte proportion de COOPERER après COOPERER");
    }

    @Test
    void testComportementApresTrahison() {
        historique.clear();
        historique.add(CoupEnum.TRAHIR);

        int nbTrahir = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.TRAHIR) {
                nbTrahir++;
            }
        }

        double pourcentageTrahir = (double) nbTrahir / iterations;
        // Devrait être environ 90% de TRAHIR (copie) car 10% sera forcé en COOPERER
        assertTrue(pourcentageTrahir > 0.80 && pourcentageTrahir < 0.95,
                "Devrait avoir environ 90% de TRAHIR après TRAHIR (avec 10% de COOPERER forcé)");
    }

    @Test
    void testPresenceComportementApaisement() {
        // Même après une trahison, on devrait observer des coups COOPERER
        historique.clear();
        historique.add(CoupEnum.TRAHIR);

        int nbCooperer = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.COOPERER) {
                nbCooperer++;
            }
        }

        double pourcentageCooperer = (double) nbCooperer / iterations;
        // Devrait être environ 10% de COOPERER (comportement d'apaisement)
        assertTrue(pourcentageCooperer > 0.05 && pourcentageCooperer < 0.15,
                "Devrait avoir environ 10% de COOPERER même après TRAHIR");
    }

    @Test
    void testSequenceAlternee() {
        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        historique.add(CoupEnum.COOPERER);
        historique.add(CoupEnum.TRAHIR);

        int nbCoupsIdentiques = 0;
        CoupEnum dernierCoup = historique.get(historique.size() - 1);
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == dernierCoup) {
                nbCoupsIdentiques++;
            }
        }

        double pourcentageIdentique = (double) nbCoupsIdentiques / iterations;
        // Devrait être environ 90% identique au dernier coup
        assertTrue(pourcentageIdentique > 0.85 && pourcentageIdentique < 0.95,
                "Devrait copier le dernier coup environ 90% du temps");
    }

    @Test
    void testHistoriqueDifferent() {
        List<CoupEnum> historique1 = new ArrayList<>(List.of(CoupEnum.TRAHIR));
        List<CoupEnum> historique2 = new ArrayList<>(List.of(CoupEnum.COOPERER));

        int nbDifferences = 0;
        int iterations = 100;

        for (int i = 0; i < iterations; i++) {
            CoupEnum coup1 = strategie.prochainCoup(historique1);
            CoupEnum coup2 = strategie.prochainCoup(historique2);
            if (coup1 != coup2) {
                nbDifferences++;
            }
        }

        assertTrue(nbDifferences > 0,
                "Les réponses devraient différer selon l'historique");
    }
}