package fr.uga.l3miage.pc.strategies;
/*
import java.security.SecureRandom;
import java.util.Random;

public class VraiPacificateur implements Strategie {
    private String[] historique;
    private int index;
    private Random random;
    public VraiPacificateur(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.random = new SecureRandom();
    }
    @Override
    public String prochainCoup() {
        if (index >= 2 && historique[index - 1].equals("t") && historique[index - 2].equals("t")) {
            return random.nextDouble() < 0.3 ? "c" : "t"; // 30% de chance de coopérer pour apaiser
        }
        return "c"; // Coopérer tant que l'adversaire n'a pas trahi deux fois de suite
    }
    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        historique[index++] = coupAdversaire;
    }
}

 */