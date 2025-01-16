package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class PacificateurNaif extends Strategie {
    private Random random;

    public PacificateurNaif() {
        this.random = new SecureRandom();
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        if (random.nextDouble() < 0.1) { // 10% de chance de coopérer pour apaiser
            return CoupEnum.COOPERER;
        }
        return  historiqueAdversaire.get(historiqueAdversaire.size() - 1);
    }
}
