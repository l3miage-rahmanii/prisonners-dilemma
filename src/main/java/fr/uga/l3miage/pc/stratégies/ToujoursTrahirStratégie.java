package fr.uga.l3miage.pc.strat�gies;

public class ToujoursTrahirStrat�gie implements Strategie {
    private String coupAJpuer;
    public ToujoursTrahirStrat�gie(){
        this.coupAJpuer = "t";
    }
    public String prochainCoup() {
        return "t";
    }
}
