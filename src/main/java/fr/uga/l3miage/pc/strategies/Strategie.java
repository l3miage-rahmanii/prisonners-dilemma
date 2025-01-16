package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public abstract class Strategie {
    abstract CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire);
}