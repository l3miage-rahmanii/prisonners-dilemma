package fr.uga.l3miage.pc.strat�gies;

public class ToujoursCoop�rer implements Strategie{

    private String coupAJouer;

    public ToujoursCoop�rer(){
        this.coupAJouer = "c";
    }

    public String prochainCoup() {
        return "c";
    }
}
