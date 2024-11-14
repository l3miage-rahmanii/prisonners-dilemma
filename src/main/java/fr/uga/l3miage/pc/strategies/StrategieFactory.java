package fr.uga.l3miage.pc.strategies;

public class StrategieFactory {
    public static Strategie getStrategie(String choixStrategie, String[] historique) {
        switch (choixStrategie) {
            case "dd":
                return new DonnantDonnantStrategie(historique);
            case "dda":
                return new DonnantDonnantAleatoire(historique);
            case "dd2":
                return new DonnantPourDeuxDonnants(historique);
            case "dd2alea":
                return new DonnantPourDeuxDonnantsAleatoire(historique);
            case "t":
                return new ToujoursTrahirStrategie();
            case "c":
                return new ToujoursCooperer();
            case "r":
                return new RancunierStrategie(historique);
            case "p":
                return new PavlovStrategie(historique);
            case "pa":
                return new PavlovAleatoire(historique);
            case "alea":
                return new Aleatoire();
            case "sn":
                return new SondeurNaif(historique);
            case "sr":
                return new SondeurRepentant(historique);
            case "pn":
                return new PacificateurNaif(historique);
            case "vp":
                return new VraiPacificateur(historique);
            case "adaptatif":
                return new Adaptatif(historique);
            case "graduel":
                return new Graduel();
            case "dds":
                return new DonnantDonnantSoupconneux(historique);
            case "rd":
                return new RancunierDoux(historique);
            default:
                throw new IllegalArgumentException("Stratégie inconnue : " + choixStrategie);
        }
    }
}
