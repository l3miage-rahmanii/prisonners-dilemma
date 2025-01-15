package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class VraiPacificateur extends Strategie {
    private Random random;

    public VraiPacificateur() {
        this.random = new SecureRandom();
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        int taille = historiqueAdversaire.size();

        if (taille >= 2) {
            CoupEnum avantDernierCoup = historiqueAdversaire.get(taille - 2);
            CoupEnum dernierCoup = historiqueAdversaire.get(taille - 1);

            // Si l'adversaire a trahi deux fois de suite
            if (avantDernierCoup == CoupEnum.TRAHIR && dernierCoup == CoupEnum.TRAHIR) {
                // Parfois coopérer pour essayer de faire la paix
                if (random.nextDouble() < 0.2) {  // 20% de chance de coopérer
                    return CoupEnum.COOPERER;
                }
                return CoupEnum.TRAHIR;
            }
        }

        // Coopérer si l'adversaire ne trahit pas deux fois de suite
        return CoupEnum.COOPERER;
    }
}