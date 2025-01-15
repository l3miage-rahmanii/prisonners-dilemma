package fr.uga.l3miage.pc.prisonersdilemma.Strategies;
import fr.uga.l3miage.pc.strategies.Aleatoire;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class AleatoireTest {

/*
        @Test
        void testProchainCoupReturnsValidValue() {
            Aleatoire strategie = new Aleatoire();
            String coup = strategie.prochainCoup();

            // Vérifie que le coup retourné est soit "c" soit "t"
            assertTrue(coup.equals("c") || coup.equals("t"),
                    "Le coup devrait être 'c' ou 't', mais était: " + coup);
        }

        @Test
        void testDistributionAleatoire() {
            Aleatoire strategie = new Aleatoire();
            Map<String, Integer> distribution = new HashMap<>();
            distribution.put("c", 0);
            distribution.put("t", 0);

            // On fait un grand nombre de tirages pour vérifier la distribution
            int nombreTirages = 10000;
            for (int i = 0; i < nombreTirages; i++) {
                String coup = strategie.prochainCoup();
                distribution.put(coup, distribution.get(coup) + 1);
            }

            // Vérifie que la distribution est relativement équitable (45-55%)
            double ratioC = (double) distribution.get("c") / nombreTirages;
            double ratioT = (double) distribution.get("t") / nombreTirages;

            assertTrue(ratioC > 0.45 && ratioC < 0.55,
                    "La proportion de 'c' devrait être entre 45% et 55%, mais était: " + (ratioC * 100) + "%");
            assertTrue(ratioT > 0.45 && ratioT < 0.55,
                    "La proportion de 't' devrait être entre 45% et 55%, mais était: " + (ratioT * 100) + "%");
        }

        @Test
        void testMultipleInstancesDifferentResults() {
            Aleatoire strategie1 = new Aleatoire();
            Aleatoire strategie2 = new Aleatoire();

            // Vérifie que deux instances peuvent générer des résultats différents
            boolean differencesTrouvees = false;
            for (int i = 0; i < 100; i++) {
                if (!strategie1.prochainCoup().equals(strategie2.prochainCoup())) {
                    differencesTrouvees = true;
                    break;
                }
            }

            assertTrue(differencesTrouvees,
                    "Deux instances devraient pouvoir générer des résultats différents");
        }

 */

}
