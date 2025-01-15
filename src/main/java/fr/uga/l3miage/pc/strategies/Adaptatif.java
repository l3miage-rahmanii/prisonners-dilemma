    package fr.uga.l3miage.pc.strategies;
/*
    public class Adaptatif implements Strategie {
        private String[] historique;
        private int index;
        private int totalCoopScore;
        private int totalTrahirScore;
        private int coupsCoop;
        private int coupsTrahir;
        private String initialSequence;

        public Adaptatif(String[] historique) {
            this.historique = historique;
            this.index = 0;
            this.totalCoopScore = 0;
            this.totalTrahirScore = 0;
            this.coupsCoop = 0;
            this.coupsTrahir = 0;
            this.initialSequence = "cccccccttttt";
        }

        @Override
        public String prochainCoup() {
            if (index < initialSequence.length()) {
                return String.valueOf(initialSequence.charAt(index));
            }
            return totalCoopScore / Math.max(1, coupsCoop) >= totalTrahirScore / Math.max(1, coupsTrahir) ? "c" : "t";
        }

        public void miseAJourDernierCoupAdversaire(String coupAdversaire, int score) {
            if (historique[index - 1].equals("c")) {
                totalCoopScore += score;
                coupsCoop++;
            } else {
                totalTrahirScore += score;
                coupsTrahir++;
            }
            historique[index++] = coupAdversaire;
        }
    }
    s
 */