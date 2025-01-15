package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public class DonnantDonnantSoupconneux extends Strategie {
    private boolean premierCoup;

    public DonnantDonnantSoupconneux() {
        this.premierCoup = true;
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        if (premierCoup) {
            premierCoup = false;
            return CoupEnum.TRAHIR;
        }
        return historiqueAdversaire.get(historiqueAdversaire.size() - 1); // Donnant donnant après le premier coup
    }

}