package fr.uga.l3miage.pc.strategies;


import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DonnantDonnantAleatoire extends DonnantDonnantStrategie {
    private Random random;

    public DonnantDonnantAleatoire() {
        this.random = new SecureRandom();
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        if (random.nextBoolean()) {
            return random.nextBoolean() ? CoupEnum.COOPERER : CoupEnum.TRAHIR; // Randomly choose "c" or "t"
        }
        return super.prochainCoup(historiqueAdversaire); // Default to Donnant-Donnant behavior
    }
}

