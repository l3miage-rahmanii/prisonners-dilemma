package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public class ToujoursTrahirStrategie extends Strategie {
    @Override
    public CoupEnum prochainCoup(List<CoupEnum> coup) {
        return CoupEnum.TRAHIR;
    }
}


