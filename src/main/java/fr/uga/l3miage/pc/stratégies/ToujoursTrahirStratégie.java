package fr.uga.l3miage.pc.strat�gies;

public class ToujoursTrahirStrat�gie {
    private String coupAJpuer;
    public ToujoursTrahirStrat�gie(){
        this.coupAJpuer = "t";
    }
    public String prochainCoup() {
        return "t";
    }
}
