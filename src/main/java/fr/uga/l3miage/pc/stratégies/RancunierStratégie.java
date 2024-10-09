package fr.uga.l3miage.pc.strat�gies;

public class RancunierStrat�gie {
    private boolean adversaireEstTraitre;

    //On supose que de base l'adversaire n'a pas trahis
    public RancunierStrat�gie(){
        this.adversaireEstTraitre = false;
    }

    public String prochainCoup(){
        if (adversaireEstTraitre){
            return "t";
        }else {
            return "c";
        }
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire){
        if (coupAdversaire.equals("t")){
            adversaireEstTraitre = true;
        }else{
            adversaireEstTraitre = false;
        }
    }

}
