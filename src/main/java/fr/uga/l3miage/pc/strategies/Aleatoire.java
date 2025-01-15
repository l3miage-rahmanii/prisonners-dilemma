package fr.uga.l3miage.pc.strategies;



import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;

public class Aleatoire extends Strategie {
    private final SecureRandom secureRandom;
    private CoupEnum coup;

    public Aleatoire() {
        this.secureRandom = new SecureRandom();
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        return secureRandom.nextBoolean() ? coup.COOPERER: coup.TRAHIR;
    }
}

