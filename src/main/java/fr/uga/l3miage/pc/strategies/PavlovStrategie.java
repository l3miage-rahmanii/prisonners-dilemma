package fr.uga.l3miage.pc.strategies;

public class PavlovStrategie implements Strategie {
    private int index;
    private String[] historique;
    private String dernierCoup;  // Devrait être un String pour le dernier coup, pas un score

    public PavlovStrategie(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.dernierCoup = "c";  // Le premier coup est la coopération
    }

    public String prochainCoup() {
        // Si le score est 3 ou 5, répéter le dernier coup
        if (dernierCoup.equals("c") || dernierCoup.equals("t")) {
            return index == 0 ? "c" : historique[index - 1];
        } else {
            // Sinon, changer de coup
            return dernierCoup.equals("c") ? "t" : "c";
        }
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire, int score) {
        historique[index++] = coupAdversaire;

        // Si le score est 5 ou 3, répéter le dernier coup
        if (score == 5 || score == 3) {
            dernierCoup = historique[index - 1]; // Répéter le coup de l'adversaire
        } else {
            // Sinon, changer de coup
            dernierCoup = dernierCoup.equals("c") ? "t" : "c";
        }
    }
}
