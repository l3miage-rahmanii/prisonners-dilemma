package fr.uga.l3miage.pc.strategies;

public class Graduel implements Strategie {
    private int countTrahisonAdversaire;
    private int countTrahirEnRepresailles;
    private boolean enRepresailles;

    public Graduel() {
        this.countTrahisonAdversaire = 0;
        this.countTrahirEnRepresailles = 0;
        this.enRepresailles = false;
    }

    @Override
    public String prochainCoup() {
        if (enRepresailles) {
            if (countTrahirEnRepresailles > 0) {
                countTrahirEnRepresailles--;
                return "t";
            }
            enRepresailles = false;
            return "c"; // Coop�re apr�s repr�sailles
        }
        return "c"; // Coop�ration par d�faut
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        if (coupAdversaire.equals("t")) {
            countTrahisonAdversaire++;
            enRepresailles = true;
            countTrahirEnRepresailles = countTrahisonAdversaire;
        }
    }
}