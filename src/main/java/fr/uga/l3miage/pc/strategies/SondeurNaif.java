package fr.uga.l3miage.pc.strategies;

import java.security.SecureRandom;
import java.util.Random;

public class SondeurNaif implements Strategie {
    private String[] historique;
    private int index;
    private Random random;

    public SondeurNaif(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.random = new SecureRandom();
    }

    @Override
    public String prochainCoup() {
        if (random.nextDouble() < 0.1) { // 10% de probabilité de trahir pour tester
            return "t";
        }
        return index > 0 && historique[index - 1].equals("t") ? "t" : "c";
    }


    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        historique[index++] = coupAdversaire;
    }
}
