package fr.uga.l3miage.pc.strategies;
/*
public class RancunierDoux extends Strategie {
    private String[] historique;
    private int index;
    private boolean estRancunier;
    private int compteurRepresailles;

    public RancunierDoux(String[] historique) {
        this.historique = historique;
        this.index = 0;
        this.estRancunier = false;
        this.compteurRepresailles = 0;
    }

    @Override
    public String prochainCoup() {
        if (estRancunier) {
            if (compteurRepresailles > 0) {
                compteurRepresailles--;
                return "t"; // Punit par trois trahisons
            }
            estRancunier = false;
            return "c"; // Coopère après représailles
        }
        return "c"; // Coopère par défaut
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        if (!estRancunier && coupAdversaire.equals("t")) {
            estRancunier = true;
            compteurRepresailles = 3; // Punit par trois trahisons
        }
        historique[index++] = coupAdversaire;
    }
}


 */