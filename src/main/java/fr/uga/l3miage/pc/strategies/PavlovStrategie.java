package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public class PavlovStrategie extends Strategie {
   private CoupEnum coup;
   private int scorePrecedent;// Devrait être un String pour le dernier coup, pas un score


    public CoupEnum prochainCoup(List<CoupEnum> historique) {
        if (scorePrecedent==5 || scorePrecedent==3 ) {
            return historique.get(historique.size() - 1);
        }
        if (historique.get(historique.size() - 1) == CoupEnum.COOPERER)
            return CoupEnum.TRAHIR;
        else return CoupEnum.COOPERER;
    }

    public void setScorePrecedent(int score) {
         scorePrecedent = score;
    }

}
