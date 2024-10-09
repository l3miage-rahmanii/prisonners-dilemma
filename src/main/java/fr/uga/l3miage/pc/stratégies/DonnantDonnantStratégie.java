package fr.uga.l3miage.pc.strat�gies;

public class DonnantDonnantStrat�gie implements Strategie {
    private int index;
    private String[] historique;

    public DonnantDonnantStrat�gie(String[] historique) {
        this.historique = historique;
        this.index = 0;
    }

    public String prochainCoup(){
        return index == 0 ? "c" : historique[index - 1];
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire){
        historique[index++] = coupAdversaire;
    }
}
