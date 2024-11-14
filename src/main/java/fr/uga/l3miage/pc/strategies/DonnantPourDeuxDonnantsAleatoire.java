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
        if (random.nextDouble() < 0.1) { // 10% de chance de jouer au hasard
            return random.nextBoolean() ? "c" : "t";
        }
        return super.prochainCoup(); // Sinon, utiliser Donnant pour deux donnants
    }
}