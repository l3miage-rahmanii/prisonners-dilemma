package fr.uga.l3miage.pc.strategies;

import java.security.SecureRandom;
import java.util.Random;

public class DonnantPourDeuxDonnantsAleatoire extends DonnantPourDeuxDonnants {
    private Random random;

    public DonnantPourDeuxDonnantsAleatoire(String[] historique) {
        super(historique);
        this.random = new SecureRandom();
    }

    @Override
    public String prochainCoup() {
        if (random.nextDouble() < 0.1) {
            return random.nextBoolean() ? "c" : "t";
        }
        return super.prochainCoup();
    }
}