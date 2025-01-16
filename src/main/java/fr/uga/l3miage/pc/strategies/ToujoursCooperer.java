package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public class ToujoursCooperer extends Strategie{

    public CoupEnum prochainCoup(List<CoupEnum> coups) {
        return CoupEnum.COOPERER;
    }
}


