package fr.uga.l3miage.pc.strat�gies;

public class StrategieFactory {
    public static Strategie getStrategie(String choixStrategie) {
        switch (choixStrategie) {
            case "dd":
                return new DonnantDonnantStrat�gie();
            case "t":
                return new ToujoursTrahirStrat�gie();
            case "c":
                return new ToujoursCoop�rer();
            case "r":
                return new RancunierStrat�gie();
            case "p":
                return new PavlovStrat�gie();
            default:
                throw new IllegalArgumentException("Strat�gie inconnue : " + choixStrategie);
        }
    }
}
