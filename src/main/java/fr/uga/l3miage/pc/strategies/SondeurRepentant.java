package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class SondeurRepentant extends Strategie {
    private Random random;
    private boolean dernierCoupTest;

    public SondeurRepentant() {
        this.random = new SecureRandom();
        this.dernierCoupTest = false;
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historique) {
        if (historique.isEmpty()) {
            return CoupEnum.COOPERER;  // Coopérer par défaut si l'historique est vide
        }

        CoupEnum dernierCoupAdversaire = historique.get(historique.size() - 1);

        if (dernierCoupTest && dernierCoupAdversaire == CoupEnum.TRAHIR) {
            dernierCoupTest = false;  // Réinitialiser après avoir coopéré par repentir
            return CoupEnum.COOPERER;
        }

        if (random.nextDouble() < 0.1) {
            dernierCoupTest = true;
            return CoupEnum.TRAHIR;
        }

        dernierCoupTest = false;
        return dernierCoupAdversaire;
    }
}
