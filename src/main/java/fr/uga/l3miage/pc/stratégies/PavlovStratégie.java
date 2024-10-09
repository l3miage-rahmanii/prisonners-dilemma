package fr.uga.l3miage.pc.strat�gies;

public class PavlovStrat�gie {
    private String dernierCoup;
    private int dernierScore;

    public PavlovStrat�gie(){
        this.dernierCoup = "c";
        this.dernierScore = 0;
    }

    public PavlovStrat�gie(String dernierCoup, int dernierScore){
        this.dernierCoup = dernierCoup;
        this.dernierScore = dernierScore;
    }

    public String prochainCoup(){
        if(dernierScore == 3 || dernierScore == 5){
            return this.dernierCoup;
        } else {
            return dernierCoup.equals("c") ? "t" : "c";
        }
    }

    public void miseAJour(String coupJoue, int score){
        this.dernierScore += score;
        this.dernierCoup = coupJoue;
    }
}
