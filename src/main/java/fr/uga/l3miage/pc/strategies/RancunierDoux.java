package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public class RancunierDoux extends Strategie {
    private boolean estRancunier;
    private int compteurRepresailles;

    public RancunierDoux() {
        this.estRancunier = false;
        this.compteurRepresailles = 0;
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        if (estRancunier) {
            if (compteurRepresailles > 0) {
                compteurRepresailles--;
                return CoupEnum.TRAHIR;
            } else if (compteurRepresailles > -3) {
                compteurRepresailles--;
                return CoupEnum.COOPERER;
            } else {
                estRancunier = false;
            }
        }

        CoupEnum dernierCoupAdversaire = historiqueAdversaire.get(historiqueAdversaire.size() - 1);
        if (dernierCoupAdversaire == CoupEnum.TRAHIR) {
            estRancunier = true;
            compteurRepresailles = 2;
        }

        return CoupEnum.COOPERER;
    }
}
