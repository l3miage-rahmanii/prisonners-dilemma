    package fr.uga.l3miage.pc.strategies;

    import fr.uga.l3miage.pc.enums.CoupEnum;

    import java.util.List;

    public class Graduel extends Strategie {
        private int countTrahisonAdversaire;
        private int countTrahirEnRepresailles;
        private int countCooperationsApresRepresailles;

        public Graduel() {
            this.countTrahisonAdversaire = 0;
            this.countTrahirEnRepresailles = 0;
            this.countCooperationsApresRepresailles = 0;
        }

        @Override
        public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
            CoupEnum dernierCoupAdversaire = historiqueAdversaire.get(historiqueAdversaire.size() - 1);

            if (dernierCoupAdversaire == CoupEnum.TRAHIR) {
                countTrahisonAdversaire++;
                countTrahirEnRepresailles = countTrahisonAdversaire;
            }

            if (countTrahirEnRepresailles > 0) {
                countTrahirEnRepresailles--;
                return CoupEnum.TRAHIR;
            }

            if (countCooperationsApresRepresailles < 2) {
                countCooperationsApresRepresailles++;
                return CoupEnum.COOPERER;
            }

            countCooperationsApresRepresailles = 0;
            return CoupEnum.COOPERER;
        }
    }