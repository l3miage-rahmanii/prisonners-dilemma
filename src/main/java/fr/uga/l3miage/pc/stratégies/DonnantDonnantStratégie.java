package fr.uga.l3miage.pc.strat�gies;

public class DonnantDonnantStrat�gie {
    private String dernierCoupAdversaire;

    public DonnantDonnantStrat�gie() {
        this.dernierCoupAdversaire = "c";
    }

    public String prochainCoup(){
        return dernierCoupAdversaire;
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire){
        this.dernierCoupAdversaire = coupAdversaire;
    }
}
