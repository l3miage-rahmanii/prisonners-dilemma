        package fr.uga.l3miage.pc.services;

        import fr.uga.l3miage.pc.entities.PartieEntity;
        import fr.uga.l3miage.pc.enums.CoupEnum;
        import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
        import fr.uga.l3miage.pc.mappers.PartieMapper;

        import fr.uga.l3miage.pc.requests.PartieRequestDTO;
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
            private static final String TERMINEE = "terminée";
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
                    //if ()
                   // choisirStrategieAutomatique(partie, estJoueur1);
                } else {
                    if (estJoueur1) {
                        //if(partie.getStrategieJoueur1() != null) {jouerCoupStrate}
                        partie.getCoupsJoueur1().add(coup);
                    } else {
                        partie.getCoupsJoueur2().add(coup);
                    }
                }

                    if (partie.getCoupsJoueur2().size() >= partie.getNbTours()) {
                        terminerPartie(partie);
                    }
                return partieMapper.toResponse(partie);
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

            public PartieResponseDTO creerPartie(PartieRequestDTO partieRequestDTO) {
                if (partie.getStatus().equals(EN_COURS) || partie.getStatus().equals(EN_ATTENTE)) {
                    throw new BadRequestRestException("Une partie est déjà en cours.");
                }

                if (partieRequestDTO.getNbTours() <= 0) {
                    throw new BadRequestRestException("Le nombre de tours doit être positif");
                }

               partie = PartieEntity.builder()
                        .status(EN_ATTENTE)
                        .nbTours(partieRequestDTO.getNbTours())
                        .idJoueur1(0)
                        .scoreJoueur1(0)
                        .scoreJoueur2(0)
                        .coupsJoueur1(new ArrayList<>())
                        .coupsJoueur2(new ArrayList<>())
                        .build();

                return  partieMapper.toResponse(partie);
            }


            public PartieResponseDTO rejoindrePartie() {

                if (partie.getStatus().equals(EN_COURS)) {
                    throw new BadRequestRestException("Une partie est déjà en cours");
                }

                partie.setStatus(EN_COURS);
                partie.setIdJoueur2(1);

                return  partieMapper.toResponse(partie);
            }


            public String getStatus() {

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

            public int calculerScores(int joueurId) {
                int dernierIndex = partie.getCoupsJoueur1().size() - 1;
                CoupEnum coupJ1 = partie.getCoupsJoueur1().get(dernierIndex);
                CoupEnum coupJ2 = partie.getCoupsJoueur2().get(dernierIndex);

                // Dilemme du prisonnier classique
                if (coupJ1.equals(CoupEnum.COOPERER) && coupJ2.equals(CoupEnum.COOPERER)) {
                    // Coopération mutuelle
                    partie.setScoreJoueur1(partie.getScoreJoueur1() + 3);
                    partie.setScoreJoueur2(partie.getScoreJoueur2() + 3);
                } else if (coupJ1.equals(CoupEnum.TRAHIR) && coupJ2.equals(CoupEnum.TRAHIR)) {
                    // Trahison mutuelle
                    partie.setScoreJoueur1(partie.getScoreJoueur1() + 1);
                    partie.setScoreJoueur2(partie.getScoreJoueur2() + 1);
                } else if (coupJ1.equals(CoupEnum.TRAHIR) && coupJ2.equals(CoupEnum.COOPERER)) {
                    // J1 trahit, J2 coopère
                    partie.setScoreJoueur1(partie.getScoreJoueur1() + 5);
                    partie.setScoreJoueur2(partie.getScoreJoueur2() + 0);
                } else {
                    // J1 coopère, J2 trahit
                    partie.setScoreJoueur1(partie.getScoreJoueur1() + 0);
                    partie.setScoreJoueur2(partie.getScoreJoueur2() + 5);
                }

                if(joueurId==partie.getIdJoueur1()) {
                    return partie.getScoreJoueur1();
                }
                else {return partie.getScoreJoueur2();}

            }

            private void terminerPartie(PartieEntity partie) {
                partie.setStatus(TERMINEE);
                // Determine the winner and update their scores
            }

        }