package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;


public class SondeurNaif extends Strategie {

    private Random random;
    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public SondeurNaif() {
        this.random = new SecureRandom();
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historique) {
        if (random.nextDouble() < 0.1) {
            return CoupEnum.TRAHIR;
        }
        return historique.get(historique.size() - 1);
    }


}


