package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public class DonnantPourDeuxDonnants extends DonnantDonnantStrategie {
    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        if (historiqueAdversaire.get(historiqueAdversaire.size() - 1)== historiqueAdversaire.get(historiqueAdversaire.size() - 2)) {
            return historiqueAdversaire.get(historiqueAdversaire.size() - 1);
        }
        return historiqueAdversaire.get(historiqueAdversaire.size() - 2);
    }
}