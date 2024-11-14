package fr.uga.l3miage.pc.stratégies;

public class DonnantDonnantStratégie implements Strategie {
    private int index;
    private String[] historique;

    public DonnantDonnantStratégie(String[] historique) {
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
