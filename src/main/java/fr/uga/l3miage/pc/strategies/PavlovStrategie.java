package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;
/*
public class PavlovStrategie extends Strategie {
   private CoupEnum coup;  // Devrait être un String pour le dernier coup, pas un score


    public CoupEnum prochainCoup(List<CoupEnum> historique) {
        if (dernierCoup.equals("c") || dernierCoup.equals("t")) {
            return index == 0 ? "c" : historique[index - 1];
        } else {
            // Sinon, changer de coup
            return dernierCoup.equals("c") ? "t" : "c";
        }
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire, int score) {
        historique[index++] = coupAdversaire;

        // Si le score est 5 ou 3, répéter le dernier coup
        if (score == 5 || score == 3) {
            dernierCoup = historique[index - 1]; // Répéter le coup de l'adversaire
        } else {
            // Sinon, changer de coup
            dernierCoup = dernierCoup.equals("c") ? "t" : "c";
        }
    }
}
*/