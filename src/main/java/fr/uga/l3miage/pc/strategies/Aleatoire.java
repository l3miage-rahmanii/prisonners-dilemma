package fr.uga.l3miage.pc.strategies;



import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class Aleatoire implements Strategie {
    private final SecureRandom secureRandom;

    public Aleatoire() {
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String prochainCoup() {
        return secureRandom.nextBoolean() ? "c" : "t";
    }
}

