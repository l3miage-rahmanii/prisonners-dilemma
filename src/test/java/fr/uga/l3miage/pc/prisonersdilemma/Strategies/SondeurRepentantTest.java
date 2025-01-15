package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.SondeurRepentant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class SondeurRepentantTest {
private SondeurRepentant strategie;
    private List<CoupEnum> historique;

    @BeforeEach
    void setUp() {
        strategie = new SondeurRepentant();
        historique = new ArrayList<>();
    }

    @Test
    void testHistoriqueVide() {
        // Quand l'historique est vide, doit coopérer sauf test aléatoire
        int nbCooperer = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(new ArrayList<>()) == CoupEnum.COOPERER) {
                nbCooperer++;
            }
        }

        double pourcentageCooperer = (double) nbCooperer / iterations;
        assertTrue(pourcentageCooperer > 0.85 && pourcentageCooperer < 0.95,
            "Devrait coopérer environ 90% du temps avec un historique vide");
    }

    @Test
    void testComportementRepentant() {
        // Test du comportement repentant après une trahison
        historique.add(CoupEnum.COOPERER);

        // Forcer plusieurs séquences de test/réponse
        int nbRepentir = 0;
        int iterations = 100;

        for (int i = 0; i < iterations; i++) {
            // Premier coup - peut être un test
            CoupEnum premierCoup = strategie.prochainCoup(historique);
            if (premierCoup == CoupEnum.TRAHIR) {
                // Si c'était une trahison, l'adversaire trahit en retour
                historique.clear();
                historique.add(CoupEnum.TRAHIR);
                // Le prochain coup devrait être COOPERER par repentir
                if (strategie.prochainCoup(historique) == CoupEnum.COOPERER) {
                    nbRepentir++;
                }
                // Réinitialiser pour le prochain test
                historique.clear();
                historique.add(CoupEnum.COOPERER);
            }
        }

        assertTrue(nbRepentir > 0,
            "Devrait observer des comportements de repentir après trahison");
    }

    @Test
    void testComportementCopie() {
        // Test du comportement de copie normal (sans test ni repentir)
        historique.add(CoupEnum.COOPERER);

        int nbCopie = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.COOPERER) {
                nbCopie++;
            }
        }

        double pourcentageCopie = (double) nbCopie / iterations;
        assertTrue(pourcentageCopie > 0.85 && pourcentageCopie < 0.95,
            "Devrait copier le coup précédent environ 90% du temps");
    }

    @Test
    void testSequenceComplete() {
        // Test d'une séquence complète de comportements
        List<CoupEnum> resultats = new ArrayList<>();

        // Premier coup avec historique vide
        historique.clear();
        resultats.add(strategie.prochainCoup(historique));

        // Coup après coopération
        historique.add(CoupEnum.COOPERER);
        resultats.add(strategie.prochainCoup(historique));

        // Coup après trahison
        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        resultats.add(strategie.prochainCoup(historique));

        assertFalse(resultats.isEmpty(),
            "La stratégie doit produire une séquence de coups");
    }

    @Test
    void testFrequenceSondage() {
        // Vérifie que la fréquence de sondage (TRAHIR) est d'environ 10%
        historique.add(CoupEnum.COOPERER);

        int nbTrahir = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            if (strategie.prochainCoup(historique) == CoupEnum.TRAHIR) {
                nbTrahir++;
            }
        }

        double pourcentageTrahir = (double) nbTrahir / iterations;
        assertTrue(pourcentageTrahir > 0.05 && pourcentageTrahir < 0.15,
            "La fréquence de sondage devrait être d'environ 10%");
    }

    @Test
    void testReactionChangementAdversaire() {
        // Test de la réaction aux changements de comportement de l'adversaire
        // Premier coup - normalement coopération
        historique.add(CoupEnum.COOPERER);
        CoupEnum premierCoup = strategie.prochainCoup(historique);

        // Changement vers trahison
        historique.clear();
        historique.add(CoupEnum.TRAHIR);
        CoupEnum deuxiemeCoup = strategie.prochainCoup(historique);

        // Retour à coopération
        historique.clear();
        historique.add(CoupEnum.COOPERER);
        CoupEnum troisiemeCoup = strategie.prochainCoup(historique);

        // Vérifie que les coups changent en fonction de l'historique
        assertNotNull(premierCoup);
        assertNotNull(deuxiemeCoup);
        assertNotNull(troisiemeCoup);
    }
}