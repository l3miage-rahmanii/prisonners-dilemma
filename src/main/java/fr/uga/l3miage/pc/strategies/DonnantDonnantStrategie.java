package fr.uga.l3miage.pc.strategies;

public class DonnantDonnantStrategie implements Strategie {
    private int index;
    private String[] historique;

    public DonnantDonnantStrategie(String[] historique) {
        this.historique = historique;
        this.index = 0;
    }

    public String prochainCoup() {
        return index == 0 ? "c" : historique[index - 1];
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        historique[index++] = coupAdversaire;
    }

    protected int getIndex() {
        return index;
    }

    protected String[] getHistorique() {
        return historique;
    }
}
