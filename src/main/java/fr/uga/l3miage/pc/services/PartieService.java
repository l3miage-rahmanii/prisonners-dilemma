        package fr.uga.l3miage.pc.services;

        import fr.uga.l3miage.pc.entities.PartieEntity;
        import fr.uga.l3miage.pc.enums.CoupEnum;
        import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
        import fr.uga.l3miage.pc.mappers.PartieMapper;
        import fr.uga.l3miage.pc.responses.PartieResponseDTO;
        import lombok.RequiredArgsConstructor;
        import org.springframework.stereotype.Service;

        import java.util.ArrayList;


        @Service
        @RequiredArgsConstructor
        public class PartieService {
            private final PartieMapper partieMapper;
            private static final String EN_ATTENTE = "en_attente";
            private static final String EN_COURS = "en_cours";
            private static final String TERMINEE = "terminee";
            public PartieEntity partie;

            public PartieResponseDTO jouerCoup(int joueurId, CoupEnum coup) {
                // Vérification des tours
                boolean estJoueur1 = partie.getIdJoueur1() == (joueurId);
                boolean estTourValide = (estJoueur1 && partie.getCoupsJoueur1().size() <= partie.getCoupsJoueur2().size()) ||
                        (!estJoueur1 && partie.getCoupsJoueur2().size() < partie.getCoupsJoueur1().size());

                if (!estTourValide) {
                    throw new BadRequestRestException("Ce n'est pas votre tour");
                }

                if (coup == CoupEnum.ABANDONNER) {
                    return null;
                } else {
                    if (estJoueur1) {
                        partie.getCoupsJoueur1().add(coup);
                    } else {
                        partie.getCoupsJoueur2().add(coup);
                    }
                    // Si les deux joueurs ont joué, calculer les points pour ce tour
                    if (partie.getCoupsJoueur1().size() == partie.getCoupsJoueur2().size()) {
                        calculerScoresDuTour();
                    }
                }

                    if (partie.getCoupsJoueur2().size() >= partie.getNbTours()) {
                        terminerPartie(partie);
                    }
                return partieMapper.toResponse(partie);
            }

            public void calculerScoresDuTour() {
                int dernierIndex = partie.getCoupsJoueur1().size() - 1;
                CoupEnum coupJ1 = partie.getCoupsJoueur1().get(dernierIndex);
                CoupEnum coupJ2 = partie.getCoupsJoueur2().get(dernierIndex);

                // Points constants
                final int COOPERATION_MUTUELLE = 3;
                final int TRAHISON_MUTUELLE = 1;
                final int TRAHISON_REUSSIE = 5;
                final int COOPERATION_TRAHIE = 0;

                // Calcul des points pour ce tour
                int pointsJ1;
                int pointsJ2;

                if (coupJ1.equals(CoupEnum.COOPERER) && coupJ2.equals(CoupEnum.COOPERER)) {
                    pointsJ1 = pointsJ2 = COOPERATION_MUTUELLE;
                } else if (coupJ1.equals(CoupEnum.TRAHIR) && coupJ2.equals(CoupEnum.TRAHIR)) {
                    pointsJ1 = pointsJ2 = TRAHISON_MUTUELLE;
                } else if (coupJ1.equals(CoupEnum.TRAHIR) && coupJ2.equals(CoupEnum.COOPERER)) {
                    pointsJ1 = TRAHISON_REUSSIE;
                    pointsJ2 = COOPERATION_TRAHIE;
                } else {
                    pointsJ1 = COOPERATION_TRAHIE;
                    pointsJ2 = TRAHISON_REUSSIE;
                }

                // Mise à jour des scores
                partie.setScoreJoueur1(partie.getScoreJoueur1() + pointsJ1);
                partie.setScoreJoueur2(partie.getScoreJoueur2() + pointsJ2);
            }

    /*
            private void choisirStrategieAutomatique(PartieEntity partie, boolean estJoueur1) {

            }

            public CoupEnum coupStrategie (StrategieEnum strategie, boolean estJoueur1) {

                List<CoupEnum> historiqueAdversaire = estJoueur1
                        ? partie.getCoupsJoueur2()
                        : partie.getCoupsJoueur1();


                try {
                    return strategie.prochainCoup(historiqueAdversaire);

                } catch (Exception e) {
                    throw new BadRequestRestException("Erreur lors de l'application de la stratégie");
                }


            } */

            public PartieEntity creerPartie() {
                if(partie != null) {
                        throw new BadRequestRestException("Une partie est deja en cours.");
                }
                else {
                    partie = PartieEntity.builder()
                            .status(EN_ATTENTE)
                            .nbTours(10)
                            .idJoueur1(1)
                            .idJoueur2(2)
                            .scoreJoueur1(0)
                            .scoreJoueur2(0)
                            .coupsJoueur1(new ArrayList<>())
                            .coupsJoueur2(new ArrayList<>())
                            .build();
                }

                return  partie;
            }


            public PartieEntity rejoindrePartie() {

                if (partie.getStatus().equals(EN_COURS)) {
                    throw new BadRequestRestException("Une partie est deja en cours");
                }

                partie.setStatus(EN_COURS);

                return partie;
            }


            public String getStatus() {
                if (partie == null) {
                    return "Aucune partie n'a ete creee";
                }

                StringBuilder status = new StringBuilder();
                status.append("Statut: ").append(partie.getStatus());

                if (EN_COURS.equals(partie.getStatus())) {
                    status.append(" - Tour: ")
                            .append(Math.min(partie.getCoupsJoueur1().size(), partie.getCoupsJoueur2().size()) + 1)
                            .append("/")
                            .append(partie.getNbTours());
                }

                return status.toString();
            }

            public int getScore(int joueurId) {
                if (partie == null) {
                    return 0;
                }

                if (joueurId != partie.getIdJoueur1() && joueurId != partie.getIdJoueur2()) {
                    throw new IllegalArgumentException("ID de joueur invalide");
                }

                return joueurId == partie.getIdJoueur1() ? partie.getScoreJoueur1() : partie.getScoreJoueur2();
            }

            private void terminerPartie(PartieEntity partie) {
                partie.setStatus(TERMINEE);

            }

        }