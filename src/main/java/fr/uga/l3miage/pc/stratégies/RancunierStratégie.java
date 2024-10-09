package fr.uga.l3miage.pc.strat�gies;

public class RancunierStrat�gie implements Strategie {
    private int index;
    private String[] historique;

    public RancunierStrat�gie(String[] historique) {
        this.historique = historique;
        this.index = 0;
    }

    public String prochainCoup() {
        return index > 0 && historique[index - 1].equals("t") ? "t" : "c";
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        historique[index++] = coupAdversaire;
    }
}
