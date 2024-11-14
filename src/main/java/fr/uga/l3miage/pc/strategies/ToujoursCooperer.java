package fr.uga.l3miage.pc.strategies;

public class ToujoursCooperer implements Strategie{

    private String coupAJouer;

    public ToujoursCooperer(){
        this.coupAJouer = "c";
    }

    public String prochainCoup() {
        return "c";
    }
}
