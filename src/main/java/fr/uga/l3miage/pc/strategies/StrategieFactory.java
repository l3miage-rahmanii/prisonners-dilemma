package fr.uga.l3miage.pc.strategies;


import fr.uga.l3miage.pc.Enum.StrategieEnum;

public class StrategieFactory {

    public static Strategie getStrategie(StrategieEnum choixStrategie, String[] historique) {
        switch (choixStrategie) {
            case DONNANT_DONNANT:
                return new DonnantDonnantStrategie(historique);
            case DONNANT_DONNANT_ALEATOIRE:
                return new DonnantDonnantAleatoire(historique);
            case DONNANT_POUR_DEUX_DONNANTS:
                return new DonnantPourDeuxDonnants(historique);
            case DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE:
                return new DonnantPourDeuxDonnantsAleatoire(historique);
            case TOUJOURS_TRAHIR:
                return new ToujoursTrahirStrategie();
            case TOUJOURS_COOPERER:
                return new ToujoursCooperer();
            case RANCUNIER:
                return new RancunierStrategie(historique);
            case PAVLOV:
                return new PavlovStrategie(historique);
            case PAVLOV_ALEATOIRE:
                return new PavlovAleatoire(historique);
            case ALEATOIRE:
                return new Aleatoire();
            case SONDEUR_NAIF:
                return new SondeurNaif(historique);
            case SONDEUR_REPENTANT:
                return new SondeurRepentant(historique);
            case PACIFICATEUR_NAIF:
                return new PacificateurNaif(historique);
            case VRAI_PACIFICATEUR:
                return new VraiPacificateur(historique);
            case ADAPTATIF:
                return new Adaptatif(historique);
            case GRADUEL:
                return new Graduel();
            case DONNANT_DONNANT_SOUPCONNEUX:
                return new DonnantDonnantSoupconneux(historique);
            case RANCUNIER_DOUX:
                return new RancunierDoux(historique);
            default:
                throw new IllegalArgumentException("Stratégie inconnue : " + choixStrategie);
        }
    }
}
