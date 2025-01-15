package fr.uga.l3miage.pc.strategies;
/*
import java.security.SecureRandom;
import java.util.Random;

public class SondeurRepentant implements Strategie {
    private String[] historique;
    private int index;
    private Random random;
    private boolean enRepentance;

    public SondeurRepentant(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.random = new SecureRandom();
        this.enRepentance = false;
    }

    @Override
    public String prochainCoup() {
        if (enRepentance) {
            enRepentance = false;
            return "c"; // Coopérer si en repentance
        }
        if (random.nextDouble() < 0.1) { // 10% chance de trahir pour tester
            return "t";
        }
        return index > 0 && historique[index - 1].equals("t") ? "t" : "c";
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        if (coupAdversaire.equals("t")) {
            enRepentance = true; // Si l'adversaire trahit, revenir à la coopération
        }
        historique[index++] = coupAdversaire;
    }
}

 */