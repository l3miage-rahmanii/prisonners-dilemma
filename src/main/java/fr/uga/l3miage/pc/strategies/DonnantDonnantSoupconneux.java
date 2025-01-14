package fr.uga.l3miage.pc.strategies;

public class DonnantDonnantSoupconneux implements Strategie {
    private String[] historique;
    private int index;
    private boolean premierCoup;

    public DonnantDonnantSoupconneux(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.premierCoup = true;
    }

    @Override
    public String prochainCoup() {
        if (premierCoup) {
            premierCoup = false;
            return "t"; // Commence par trahir
        }
        return index > 0 && historique[index - 1].equals("t") ? "t" : "c"; // Donnant donnant après le premier coup
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        historique[index++] = coupAdversaire;
    }
}