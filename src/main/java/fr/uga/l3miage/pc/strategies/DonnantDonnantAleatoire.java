package fr.uga.l3miage.pc.strategies;


import java.security.SecureRandom;
import java.util.Random;

public class DonnantDonnantAleatoire extends DonnantDonnantStrategie {
    private Random random;

    public DonnantDonnantAleatoire(String[] historique) {
        super(historique);
        this.random = new SecureRandom();
    }

    @Override
    public String prochainCoup() {
        // Randomly decide to either follow the regular tit-for-tat or choose a random move
        if (random.nextBoolean()) {
            return random.nextBoolean() ? "c" : "t"; // Randomly choose "c" or "t"
        }
        return super.prochainCoup(); // Default to Donnant-Donnant behavior
    }
}