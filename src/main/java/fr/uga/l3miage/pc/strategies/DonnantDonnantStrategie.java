package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;


public class DonnantDonnantStrategie extends Strategie {

    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        return historiqueAdversaire.get(historiqueAdversaire.size() - 1);
    }


}
