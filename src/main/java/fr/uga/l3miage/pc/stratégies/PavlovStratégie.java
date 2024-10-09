package fr.uga.l3miage.pc.strat�gies;

public class PavlovStrat�gie implements Strategie {
    private int index;
    private String[] historique;
    private int dernierScore;

    public PavlovStrat�gie(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.dernierScore = 0;
    }

    public String prochainCoup() {
        if (dernierScore == 3 || dernierScore == 5) {
            return index == 0 ? "c" : historique[index - 1];
        } else {
            String dernierCoup = index == 0 ? "c" : historique[index - 1];
            return dernierCoup.equals("c") ? "t" : "c";
        }
    }

    public void miseAJourDernierCoupAdversaire(String coupJoue, int score) {
        dernierScore += score;
        historique[index++] = coupJoue;
    }
}
