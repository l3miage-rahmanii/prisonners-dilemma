package fr.uga.l3miage.pc.strategies;

import java.security.SecureRandom;
import java.util.Random;

public class PacificateurNaif implements Strategie {
    private String[] historique;
    private int index;
    private Random random;

    public PacificateurNaif(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.random = new SecureRandom();
    }

    @Override
    public String prochainCoup() {
        if (random.nextDouble() < 0.1) { // 10% de chance de coopérer pour apaiser
            return "c";
        }
        return index > 0 && historique[index - 1].equals("t") ? "t" : "c";
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        historique[index++] = coupAdversaire;
    }
}
