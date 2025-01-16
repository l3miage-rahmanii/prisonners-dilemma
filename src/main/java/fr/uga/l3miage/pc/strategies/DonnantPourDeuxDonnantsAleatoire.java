package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DonnantPourDeuxDonnantsAleatoire extends DonnantPourDeuxDonnants {
    private Random random;

    public DonnantPourDeuxDonnantsAleatoire() {
        this.random = new SecureRandom();
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        if (random.nextDouble() < 0.1) {
            return random.nextBoolean() ? CoupEnum.COOPERER : CoupEnum.TRAHIR;
        }
        return historiqueAdversaire.get(historiqueAdversaire.size() - 1) ;
    }
}