package fr.uga.l3miage.pc.strategies;

public class ToujoursTrahirStrategie implements Strategie {
    private String coupAJpuer;
    public ToujoursTrahirStrategie(){
        this.coupAJpuer = "t";
    }
    public String prochainCoup() {
        return "t";
    }
}
