package fr.uga.l3miage.pc.strat�gies;

public class DonnantPourDeuxDonnants extends DonnantDonnantStrat�gie {

    public DonnantPourDeuxDonnants(String[] historique) {
        super(historique);
    }

    @Override
    public String prochainCoup() {
        int index = getIndex();
        String[] historique = getHistorique();


        if (index >= 2 && historique[index - 1].equals("t") && historique[index - 2].equals("t")) {
            return "t";
        }
        return "c";
    }
}