package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class PavlovAleatoire extends PavlovStrategie {
    private Random random;

    public PavlovAleatoire() {
        this.random = new SecureRandom();
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        if (random.nextDouble() < 0.1) { // 10% de chance de jouer au hasard
            return random.nextBoolean() ? CoupEnum.COOPERER : CoupEnum.TRAHIR;
        }
        return super.prochainCoup(historiqueAdversaire);
    }
}

