    package fr.uga.l3miage.pc.strategies;

    import fr.uga.l3miage.pc.enums.CoupEnum;
    import jdk.dynalink.linker.LinkerServices;

    import java.util.ArrayList;
    import java.util.List;

    public class Adaptatif extends Strategie {
    private List<CoupEnum> mesCoups;
    private int scoreTotal;
    private int countCooperer;
    private int countTrahir;

    public Adaptatif() {
        this.mesCoups = new ArrayList<>();
        this.scoreTotal = 0;
        this.countCooperer = 0;
        this.countTrahir = 0;
    }

    @Override
    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
        // Les 11 premiers coups : 6 COOPERER suivis de 5 TRAHIR
        if (historiqueAdversaire.size() < 11) {
            CoupEnum coup = historiqueAdversaire.size() < 6 ? CoupEnum.COOPERER : CoupEnum.TRAHIR;
            mesCoups.add(coup);
            if (coup == CoupEnum.COOPERER) {
                countCooperer++;
            } else {
                countTrahir++;
            }
            return coup;
        }

        // Calculer les moyennes pour chaque type de coup
        double moyenneCooperer = countCooperer > 0 ? (double) scoreTotal / countCooperer : 0;
        double moyenneTrahir = countTrahir > 0 ? (double) scoreTotal / countTrahir : 0;

        // Choisir le coup avec la meilleure moyenne
        CoupEnum meilleurCoup = (moyenneCooperer >= moyenneTrahir) ? CoupEnum.COOPERER : CoupEnum.TRAHIR;

        // Mettre à jour les compteurs
        if (meilleurCoup == CoupEnum.COOPERER) {
            countCooperer++;
        } else {
            countTrahir++;
        }

        mesCoups.add(meilleurCoup);
        return meilleurCoup;
    }

    // Méthode pour mettre à jour le score total après chaque tour
    public void updateScore(int nouveauScore) {
        this.scoreTotal = nouveauScore;
    }
}